package com.cyz.rpc.test;

import com.cyz.rpc.RpcApplication;
import com.cyz.rpc.config.RpcConfig;

/**
 * 配置测试主类
 */
public class ConfigTestMain {

    public static void main(String[] args) {
        // 测试配置加载
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        
        System.out.println("配置加载成功:");
        System.out.println("服务名称: " + rpcConfig.getName());
        System.out.println("服务版本: " + rpcConfig.getVersion());
        System.out.println("服务器主机: " + rpcConfig.getServerHost());
        System.out.println("服务器端口: " + rpcConfig.getServerPort());
        System.out.println("序列化器: " + rpcConfig.getSerializer());
        System.out.println("负载均衡器: " + rpcConfig.getLoadBalancer());
        System.out.println("重试策略: " + rpcConfig.getRetryStrategy());
        System.out.println("容错策略: " + rpcConfig.getTolerantStrategy());
        System.out.println("是否模拟调用: " + rpcConfig.isMock());
        
        // 测试注册中心配置
        System.out.println("注册中心类型: " + rpcConfig.getRegistryConfig().getRegistry());
        System.out.println("注册中心地址: " + rpcConfig.getRegistryConfig().getAddress());
        System.out.println("注册中心超时时间: " + rpcConfig.getRegistryConfig().getTimeout());
        
        // 验证配置值
        if (rpcConfig.getName() != null && 
            rpcConfig.getSerializer() != null && 
            rpcConfig.getLoadBalancer() != null && 
            rpcConfig.getRegistryConfig().getRegistry() != null) {
            System.out.println("\n✅ 所有配置验证通过!");
        } else {
            System.out.println("\n❌ 配置验证失败!");
        }
    }
}