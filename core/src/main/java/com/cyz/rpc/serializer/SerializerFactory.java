package com.cyz.rpc.serializer;

import com.cyz.rpc.spi.SpiLoader;

public class SerializerFactory {

    static {
        // 加载所有序列化器
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取序列化器实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        if (key == null || key.isEmpty()) {
            return DEFAULT_SERIALIZER;
        }
        return SpiLoader.getInstance(Serializer.class, key);
    }
}