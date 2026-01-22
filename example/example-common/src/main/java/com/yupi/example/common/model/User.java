package com.cyz.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户模型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户年龄
     */
    private Integer age;
    
}