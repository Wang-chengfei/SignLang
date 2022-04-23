package com.example.controller;


import com.example.entity.Dictionary;
import com.example.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    //查询所有词典
    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public List<Dictionary> queryAll() {
        return dictionaryService.queryAll();
    }

    //查询用户还没有添加计划的词典
    @RequestMapping(value = "queryRest", method = RequestMethod.GET)
    public List<Dictionary> queryRest(@RequestParam("userId") Integer userId) {
        return dictionaryService.queryRest(userId);
    }

}

