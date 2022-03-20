package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * query all
     * @return
     */
    List<User> queryAllUser();
}