package com.cyz.rpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.cyz.rpc.RpcApplication;
import com.cyz.rpc.config.RpcConfig;
import com.cyz.rpc.model.RpcRequest;
import com.cyz.rpc.model.RpcResponse;
import com.cyz.rpc.serializer.Serializer;
import com.cyz.rpc.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务代理实现
 * 使用JDK动态代理，处理客户端远程调用逻辑
 */
public class ServiceProxy implements InvocationHandler {
    
    /**
     * 服务端地址，默认从配置中获取
     */
    private final String serverAddress;
    
    /**
     * 序列化器，从配置中获取
     */
    private final Serializer serializer;
    
    /**
     * 默认构造函数，使用配置中的服务器地址和序列化器
     */
    public ServiceProxy() {
        // 获取配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 构建服务端地址
        this.serverAddress = String.format("http://%s:%d", rpcConfig.getServerHost(), rpcConfig.getServerPort());
        // 从配置中获取序列化器类型
        String serializerType = rpcConfig.getSerializer();
        // 根据类型获取序列化器实例
        this.serializer = SerializerFactory.getInstance(serializerType);
    }
    
    /**
     * 构造函数，指定服务端地址
     * 
     * @param serverAddress 服务端地址
     */
    public ServiceProxy(String serverAddress) {
        this.serverAddress = serverAddress;
        // 从配置中获取序列化器类型
        String serializerType = RpcApplication.getRpcConfig().getSerializer();
        // 根据类型获取序列化器实例
        this.serializer = SerializerFactory.getInstance(serializerType);
    }
    
    /**
     * 调用代理方法
     * 
     * @param proxy 代理对象
     * @param method 调用的方法
     * @param args 方法参数
     * @return 调用结果
     * @throws Throwable 调用异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            // 创建RPC请求对象
            RpcRequest rpcRequest = RpcRequest.builder()
                    .serviceName(method.getDeclaringClass().getName())
                    .methodName(method.getName())
                    .parameterTypes(method.getParameterTypes())
                    .args(args)
                    .build();
            
            // 序列化请求对象
            byte[] requestBytes = serializer.serialize(rpcRequest);
            
            // 发送HTTP请求
            try (HttpResponse response = HttpRequest.post(serverAddress)
                    .body(requestBytes)
                    .execute()) {
                
                // 处理响应
                if (response.isOk()) {
                    byte[] responseBytes = response.bodyBytes();
                    // 反序列化响应对象
                    RpcResponse rpcResponse = serializer.deserialize(responseBytes, RpcResponse.class);
                    
                    // 处理异常
                    if (rpcResponse.getException() != null) {
                        throw rpcResponse.getException();
                    }
                    
                    return rpcResponse.getData();
                } else {
                    throw new RuntimeException("HTTP请求失败，状态码: " + response.getStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("远程调用失败: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
}