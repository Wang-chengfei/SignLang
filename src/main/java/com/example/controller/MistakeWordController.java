package com.example.controller;


import com.example.service.MistakeWordService;
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

}

