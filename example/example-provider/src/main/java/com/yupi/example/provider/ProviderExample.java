package com.cyz.example.provider;

import com.cyz.example.common.service.UserService;
import com.cyz.rpc.registry.LocalRegistry;
import com.cyz.rpc.server.HttpServer;
import com.cyz.rpc.server.VertxHttpServer;

/**
 * 服务提供者示例
 */
public class ProviderExample {
    
    public static void main(String[] args) {
        // 1. 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.registerStatic(serviceName, UserServiceImpl.class);
        
        // 2. 启动HTTP服务器，使用8081端口
        HttpServer server = new VertxHttpServer();
        server.start(8081);
    }
    
}