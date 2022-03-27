package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //获取所有用户信息
    @RequestMapping("/queryAllUser")
    public List<User> queryAllUser() {
        List<User> userList = userService.queryAllUser();
        return userList;
    }

    //测试
    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }

}