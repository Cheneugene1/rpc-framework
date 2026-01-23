package com.cyz.rpc.registry;

import com.cyz.rpc.spi.SpiLoader;

public class RegistryFactory {

    static {
        // 加载所有注册中心
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new LocalRegistry();

    /**
     * 获取注册中心实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        if (key == null || key.isEmpty()) {
            return DEFAULT_REGISTRY;
        }
        return SpiLoader.getInstance(Registry.class, key);
    }
}