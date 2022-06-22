package com.example.controller;


import com.example.entity.Word;
import com.example.service.MistakeWordService;
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
@RequestMapping("/mistakeWord")
public class MistakeWordController {

    @Autowired
    private MistakeWordService mistakeWordService;

    /**
     * 描述:添加错题单词
     *
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public int add(@RequestParam("userId") Integer userId,
                   @RequestParam("wordId") Integer wordId,
                   @RequestParam("planId") Integer planId) {
        return mistakeWordService.add(userId, wordId, planId);
    }

    /**
     * 描述:添加多个错题单词
     *
     */
    @RequestMapping(value = "addSeveral", method = RequestMethod.POST)
    public int addSeveral(@RequestParam("userId") Integer userId,
                          @RequestParam("wordIds") List<Integer> wordIds,
                          @RequestParam("planId") Integer planId) {
        return mistakeWordService.addSeveral(userId, wordIds, planId);
    }

    /**
     * 描述:查询所有错题单词
     *
     */
    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public Object queryAll(@RequestParam("userId") Integer userId) {
        return mistakeWordService.queryAll(userId);
    }

    /**
     * 描述:删除某一错题单词
     *
     */
    @RequestMapping(value = "removeOne", method = RequestMethod.POST)
    public int removeOne(@RequestParam("userId") Integer userId,
                         @RequestParam("wordId") Integer wordId) {
        return mistakeWordService.removeOne(userId, wordId);
    }

    /**
     * 描述:删除多个错题单词
     *
     */
    @RequestMapping(value = "removeSeveral", method = RequestMethod.POST)
    public int removeSeveral(@RequestParam("userId") Integer userId,
                             @RequestParam("wordIds") List<Integer> wordIds) {
        return mistakeWordService.removeSeveral(userId, wordIds);
    }
}

