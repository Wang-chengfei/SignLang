package com.example.controller;


import com.example.entity.Word;
import com.example.service.StarWordService;
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
@RequestMapping("/starWord")
public class StarWordController {

    @Autowired
    private StarWordService starWordService;

    /**
     * 描述:添加收藏单词
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public int add(@RequestParam("userId") Integer userId,
                   @RequestParam("wordId") Integer wordId) {
        return starWordService.add(userId, wordId);
    }

    /**
     * 描述:查询所有收藏的单词
     *
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<Word> queryAll(@RequestParam("userId") Integer userId) {
        return starWordService.queryAll(userId);
    }

    /**
     * 描述:删除收藏单词
     *
     */
    @RequestMapping(value = "/removeOne", method = RequestMethod.POST)
    public int removeOne(@RequestParam("userId") Integer userId,
                         @RequestParam("wordId") Integer wordId) {
        return starWordService.removeOne(userId, wordId);
    }
}

