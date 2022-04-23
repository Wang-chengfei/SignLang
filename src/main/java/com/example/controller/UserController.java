package com.example.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //在开发者平台中获取APPID和SECRET
    private static final String APPID= "xxxxx";
    private static final String SECRET = "xxxxxx";

    /**
     * 描述:查询用户信息
     *
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public User query(@RequestParam("id") Integer id) {
        return userService.query(id);
    }

    /**
     * 描述:用户登录
     *
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public User login(@RequestParam("code") String code) {
        //微信服务器的接口路径
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+APPID+ "&secret="+SECRET+"&js_code="+ code +"&grant_type=authorization_code";
        //进行网络请求,访问微信服务器接口
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        //根据返回值进行后续操作
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            JSONObject jsonObject = (JSONObject) JSON.parse(sessionData);
            //得到openid与session_ke
            String openid = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("session_key");
            return userService.login(openid, sessionKey);
        }
        return null;
    }
}

