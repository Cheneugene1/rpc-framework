package com.cyz.rpc.serializer;

import java.io.IOException;

/**
 * 序列化器接口
 */
public interface Serializer {
    
    /**
     * 序列化对象
     * 
     * @param object 要序列化的对象
     * @param <T> 对象类型
     * @return 序列化后的字节数组
     * @throws IOException 序列化异常
     */
    <T> byte[] serialize(T object) throws IOException;
    
    /**
     * 反序列化对象
     * 
     * @param bytes 序列化后的字节数组
     * @param type 目标对象类型
     * @param <T> 对象类型
     * @return 反序列化后的对象
     * @throws IOException 反序列化异常
     */
    <T> T deserialize(byte[] bytes, Class<T> type) throws IOException;
    
}