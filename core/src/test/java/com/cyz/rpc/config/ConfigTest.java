package com.cyz.rpc.config;

import com.cyz.rpc.RpcApplication;
import org.junit.Test;

/**
 * 配置测试类
 */
public class ConfigTest {

    @Test
    public void testConfigLoading() {
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
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        System.out.println("注册中心类型: " + registryConfig.getRegistry());
        System.out.println("注册中心地址: " + registryConfig.getAddress());
        System.out.println("注册中心超时时间: " + registryConfig.getTimeout());
        
        // 验证配置值
        assert rpcConfig.getName() != null;
        assert rpcConfig.getSerializer() != null;
        assert rpcConfig.getLoadBalancer() != null;
        assert registryConfig.getRegistry() != null;
        
        System.out.println("所有配置验证通过!");
    }
}