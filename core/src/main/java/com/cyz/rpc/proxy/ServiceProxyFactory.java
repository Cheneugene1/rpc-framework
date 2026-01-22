package com.cyz.rpc.proxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂
 * 用于创建服务接口的代理对象
 */
public class ServiceProxyFactory {
    
    /**
     * 获取服务代理对象
     * 
     * @param serviceClass 服务接口类
     * @param <T> 服务接口类型
     * @return 服务代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return getProxy(serviceClass, "http://localhost:8080");
    }
    
    /**
     * 获取服务代理对象，指定服务端地址
     * 
     * @param serviceClass 服务接口类
     * @param serverAddress 服务端地址
     * @param <T> 服务接口类型
     * @return 服务代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass, String serverAddress) {
        if (serviceClass == null) {
            throw new IllegalArgumentException("服务接口类不能为空");
        }
        if (!serviceClass.isInterface()) {
            throw new IllegalArgumentException("serviceClass必须是接口类型");
        }
        
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass},
                new ServiceProxy(serverAddress)
        );
    }
    
}