package com.example.service;

import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
public interface UserService extends IService<User> {
    User query(Integer id);

    User login(String openid, String sessionKey);
}
