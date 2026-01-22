package com.cyz.example.common.service;

import com.cyz.example.common.model.User;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 根据ID获取用户
     * 
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);
    
    /**
     * 新增用户
     * 
     * @param user 用户对象
     * @return 新增后的用户对象
     */
    User createUser(User user);
    
}