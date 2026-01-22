package com.cyz.rpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地注册中心
 * 用于管理服务接口与实现类的映射关系
 */
public class LocalRegistry {
    
    /**
     * 注册信息存储，使用线程安全的ConcurrentHashMap
     */
    private static final Map<String, Class<?>> registry = new ConcurrentHashMap<>();
    
    /**
     * 注册服务
     * 
     * @param serviceName 服务名称
     * @param implClass 实现类
     */
    public static void register(String serviceName, Class<?> implClass) {
        if (serviceName == null || implClass == null) {
            throw new IllegalArgumentException("服务名称和实现类不能为空");
        }
        registry.put(serviceName, implClass);
        System.out.println("服务注册成功: " + serviceName + " -> " + implClass.getName());
    }
    
    /**
     * 获取服务实现类
     * 
     * @param serviceName 服务名称
     * @return 实现类
     */
    public static Class<?> get(String serviceName) {
        if (serviceName == null) {
            throw new IllegalArgumentException("服务名称不能为空");
        }
        return registry.get(serviceName);
    }
    
    /**
     * 删除服务
     * 
     * @param serviceName 服务名称
     */
    public static void remove(String serviceName) {
        if (serviceName == null) {
            throw new IllegalArgumentException("服务名称不能为空");
        }
        registry.remove(serviceName);
        System.out.println("服务注销成功: " + serviceName);
    }
    
}