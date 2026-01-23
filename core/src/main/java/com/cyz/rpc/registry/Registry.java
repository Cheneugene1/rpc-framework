package com.cyz.rpc.registry;

import com.cyz.rpc.config.RegistryConfig;

public interface Registry {

    /**
     * 初始化
     *
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务
     *
     * @param serviceName
     * @param serviceImplClass
     */
    void register(String serviceName, Class<?> serviceImplClass);

    /**
     * 获取服务
     *
     * @param serviceName
     * @return
     */
    Class<?> getService(String serviceName);

    /**
     * 销毁
     */
    void destroy();
}