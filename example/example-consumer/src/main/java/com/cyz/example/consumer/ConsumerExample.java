package com.cyz.example.consumer;

import com.cyz.example.common.model.User;
import com.cyz.example.common.service.UserService;
import com.cyz.rpc.proxy.ServiceProxyFactory;

/**
 * 服务消费者示例
 */
public class ConsumerExample {
    
    public static void main(String[] args) {
        // 1. 使用代理工厂创建UserService代理对象，指定服务端地址为8081端口
        UserService userService = ServiceProxyFactory.getProxy(UserService.class, "http://localhost:8081");
        
        // 2. 调用远程方法：getUserById
        System.out.println("调用getUserById方法");
        User user = userService.getUserById(1L);
        System.out.println("getUserById结果: " + user);
        
        // 3. 调用远程方法：createUser
        System.out.println("\n调用createUser方法");
        User newUser = new User(null, "新用户", 25);
        User createdUser = userService.createUser(newUser);
        System.out.println("createUser结果: " + createdUser);
    }
    
}