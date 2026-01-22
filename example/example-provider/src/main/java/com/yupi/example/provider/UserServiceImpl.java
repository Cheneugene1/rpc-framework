package com.cyz.example.provider;

import com.cyz.example.common.model.User;
import com.cyz.example.common.service.UserService;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    
    /**
     * 根据ID获取用户
     * 
     * @param id 用户ID
     * @return 用户对象
     */
    @Override
    public User getUserById(Long id) {
        // 模拟从数据库获取用户
        System.out.println("调用getUserById方法，ID: " + id);
        
        // 创建并返回用户对象
        User user = new User();
        user.setId(id);
        user.setUsername("用户" + id);
        user.setAge(20 + (int) (id % 10));
        
        return user;
    }
    
    /**
     * 新增用户
     * 
     * @param user 用户对象
     * @return 新增后的用户对象
     */
    @Override
    public User createUser(User user) {
        // 模拟新增用户
        System.out.println("调用createUser方法，用户: " + user);
        
        // 设置默认ID（实际应用中应由数据库生成）
        if (user.getId() == null) {
            user.setId(System.currentTimeMillis());
        }
        
        return user;
    }
    
}