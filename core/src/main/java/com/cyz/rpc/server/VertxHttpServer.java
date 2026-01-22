package com.cyz.rpc.server;

import io.vertx.core.Vertx;

/**
 * 基于Vert.x的HTTP服务器实现
 */
public class VertxHttpServer implements HttpServer {
    
    /**
     * 启动服务器
     * 
     * @param port 监听端口
     */
    @Override
    public void start(int port) {
        // 创建Vert.x实例
        Vertx vertx = Vertx.vertx();
        
        // 创建HTTP服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
        
        // 设置请求处理器
        server.requestHandler(new HttpServerHandler());
        
        // 启动服务器
        server.listen(port, result -> {
            if (result.succeeded()) {
                // 服务器启动成功
                System.out.println("RPC服务器已启动，监听端口: " + port);
            } else {
                // 服务器启动失败
                System.err.println("RPC服务器启动失败: " + result.cause().getMessage());
                result.cause().printStackTrace();
            }
        });
    }
    
}