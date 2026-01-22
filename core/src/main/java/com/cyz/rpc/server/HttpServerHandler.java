package com.cyz.rpc.server;

import com.cyz.rpc.model.RpcRequest;
import com.cyz.rpc.model.RpcResponse;
import com.cyz.rpc.registry.LocalRegistry;
import com.cyz.rpc.serializer.JdkSerializer;
import com.cyz.rpc.serializer.Serializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.lang.reflect.Method;

/**
 * HTTP请求处理器
 * 处理客户端发来的RPC请求
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {
    
    /**
     * 序列化器
     */
    private final Serializer serializer;
    
    /**
     * 默认构造函数，使用JDK序列化器
     */
    public HttpServerHandler() {
        this.serializer = new JdkSerializer();
    }
    
    /**
     * 处理HTTP请求
     * 
     * @param request HTTP请求对象
     */
    @Override
    public void handle(HttpServerRequest request) {
        // 记录请求日志
        System.out.println("收到请求: " + request.method() + " " + request.uri());
        
        // 异步处理请求体
        request.bodyHandler(body -> {
            try {
                // 反序列化RPC请求
                byte[] requestBytes = body.getBytes();
                RpcRequest rpcRequest = serializer.deserialize(requestBytes, RpcRequest.class);
                
                // 创建RPC响应对象
                RpcResponse rpcResponse = new RpcResponse();
                
                // 处理RPC请求
                if (rpcRequest != null) {
                    try {
                        // 从本地注册中心获取服务实现类
                        Class<?> serviceClass = LocalRegistry.get(rpcRequest.getServiceName());
                        if (serviceClass == null) {
                            throw new RuntimeException("服务未找到: " + rpcRequest.getServiceName());
                        }
                        
                        // 获取目标方法
                        Method method = serviceClass.getMethod(
                                rpcRequest.getMethodName(), 
                                rpcRequest.getParameterTypes()
                        );
                        
                        // 创建服务实例并调用方法
                        Object serviceInstance = serviceClass.newInstance();
                        Object result = method.invoke(serviceInstance, rpcRequest.getArgs());
                        
                        // 设置响应结果
                        rpcResponse.setData(result);
                        rpcResponse.setDataType(method.getReturnType());
                        rpcResponse.setMessage("success");
                    } catch (Exception e) {
                        // 处理调用异常
                        System.err.println("方法调用失败: " + e.getMessage());
                        e.printStackTrace();
                        rpcResponse.setException(new RuntimeException(e.getMessage(), e));
                        rpcResponse.setMessage("error: " + e.getMessage());
                    }
                } else {
                    // 处理空请求
                    rpcResponse.setMessage("无效的RPC请求");
                }
                
                // 发送响应
                sendResponse(request, rpcResponse);
            } catch (Exception e) {
                // 处理序列化异常
                System.err.println("请求处理异常: " + e.getMessage());
                e.printStackTrace();
                
                // 发送错误响应
                RpcResponse errorResponse = new RpcResponse();
                errorResponse.setMessage("服务器内部错误: " + e.getMessage());
                sendResponse(request, errorResponse);
            }
        });
    }
    
    /**
     * 发送HTTP响应
     * 
     * @param request HTTP请求对象
     * @param rpcResponse RPC响应对象
     */
    private void sendResponse(HttpServerRequest request, RpcResponse rpcResponse) {
        HttpServerResponse response = request.response()
                .putHeader("content-type", "application/octet-stream");
        
        try {
            // 序列化响应对象
            byte[] responseBytes = serializer.serialize(rpcResponse);
            // 发送响应
            response.end(Buffer.buffer(responseBytes));
        } catch (Exception e) {
            System.err.println("响应序列化失败: " + e.getMessage());
            e.printStackTrace();
            response.setStatusCode(500).end("服务器内部错误");
        }
    }
    
}