package com.example.controller;


import com.example.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/sentence")
public class SentenceController {

    @Autowired
    private SentenceService sentenceService;

    /**
     * 描述:查询所有常用句子
     *
     */
    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public Object queryAll(@RequestParam("userId") Integer userId) {
        return sentenceService.queryAll(userId);
    }


    /**
     * 描述:添加分组
     *
     */
    @RequestMapping(value = "addGroup", method = RequestMethod.POST)
    public Object addGroup(@RequestParam("userId") Integer userId,
                           @RequestParam("name") String name) {
        return sentenceService.addGroup(userId, name);
    }

    /**
     * 描述:删除分组
     *
     */
    @RequestMapping(value = "removeGroup", method = RequestMethod.POST)
    public Object removeGroup(@RequestParam("userId") Integer userId,
                              @RequestParam("name") String name) {
        return sentenceService.removeGroup(userId, name);
    }

    /**
     * 描述:修改分组名称
     *
     */
    @RequestMapping(value = "updateGroup", method = RequestMethod.POST)
    public Object updateGroup(@RequestParam("userId") Integer userId,
                              @RequestParam("name") String name,
                              @RequestParam("newName") String newName) {
        return sentenceService.updateGroup(userId, name, newName);
    }

    /**
     * 描述:添加句子
     *
     */
    @RequestMapping(value = "addSentence", method = RequestMethod.POST)
    public Object addSentence(@RequestParam("userId") Integer userId,
                              @RequestParam("name") String name,
                              @RequestParam("content") String content) {
        return sentenceService.addSentence(userId, name, content);
    }

    /**
     * 描述:删除句子
     *
     */
    @RequestMapping(value = "removeSentence", method = RequestMethod.POST)
    public Object removeSentence(@RequestParam("sentenceId") Integer sentenceId) {
        return sentenceService.removeSentence(sentenceId);
    }

    /**
     * 描述:修改句子内容
     *
     */
    @RequestMapping(value = "updateSentence", method = RequestMethod.POST)
    public Object updateSentence(@RequestParam("sentenceId") Integer sentenceId,
                                 @RequestParam("content") String content) {
        return sentenceService.updateSentence(sentenceId, content);
    }

}

