package com.cyz.rpc.server;

/**
 * HTTP服务器接口
 * 定义服务器的启动方法
 */
public interface HttpServer {
    
    /**
     * 启动服务器
     * 
     * @param port 监听端口
     */
    void start(int port);
    
}