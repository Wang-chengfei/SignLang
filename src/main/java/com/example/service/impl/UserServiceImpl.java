package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User query(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User login(String openid, String sessionKey) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);
        User user = userMapper.selectOne(userQueryWrapper);
        //如果用户未注册，则注册
        if (user == null) {
            user = new User();
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            userMapper.insert(user);
        }
        //用户已注册，更新sessionKey
        else {
            user.setSessionKey(sessionKey);
            userMapper.updateById(user);
        }
        return user;
    }
}
