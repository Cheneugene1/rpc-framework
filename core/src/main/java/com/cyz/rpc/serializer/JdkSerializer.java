package com.cyz.rpc.serializer;

import java.io.*;

/**
 * JDK序列化器实现
 */
public class JdkSerializer implements Serializer {
    
    /**
     * 序列化对象
     * 
     * @param object 要序列化的对象
     * @param <T> 对象类型
     * @return 序列化后的字节数组
     * @throws IOException 序列化异常
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        return outputStream.toByteArray();
    }
    
    /**
     * 反序列化对象
     * 
     * @param bytes 序列化后的字节数组
     * @param type 目标对象类型
     * @param <T> 对象类型
     * @return 反序列化后的对象
     * @throws IOException 反序列化异常
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("类加载异常: " + e.getMessage(), e);
        } finally {
            objectInputStream.close();
        }
    }
    
}