package com.example.controller;


import com.example.entity.PlanWordReturn;
import com.example.entity.Word;
import com.example.service.WordService;
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
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    /**
     * 描述:随机获取一题
     *
     */
    @RequestMapping(value = "/getRandomOne", method = RequestMethod.GET)
    public Word getRandomOne() {
        return wordService.getRandomOne();
    }

    /**
     * 描述:根据id查询单词
     *
     */
    @RequestMapping(value = "/queryOne", method = RequestMethod.GET)
    public Object queryOne(@RequestParam("id") Integer id,
                           @RequestParam(value = "userId", required = false) Integer userId) {
        return wordService.queryOne(id, userId);
    }

    /**
     * 描述:获取今日单词
     *
     */
    @RequestMapping(value = "/getTodayWord", method = RequestMethod.GET)
    public List<PlanWordReturn> getTodayWord(@RequestParam("userId") Integer userId) {
        return wordService.getTodayWord(userId);
    }

    /**
    * 描述:完成若干个单词的学习
    *
    */
    @RequestMapping(value = "/completeWord", method = RequestMethod.POST)
    public boolean completeWord(@RequestParam("userId") Integer userId,
                            @RequestParam("wordIds") List<Integer> wordIds) {
        return wordService.completeWord(userId, wordIds);
    }

    /**
     * 描述:获取今日已学习单词数
     *
     */
    @RequestMapping(value = "/getTodayLearned", method = RequestMethod.GET)
    public int getTodayLearned(@RequestParam("userId") Integer userId) {
        return wordService.getTodayLearned(userId);
    }


    /**
     * 描述:增加今天学习的单词数量
     *
     */
    @RequestMapping(value = "/addTodayWord", method = RequestMethod.POST)
    public int addTodayWord(@RequestParam("userId") Integer userId,
                            @RequestParam("addAmount") Integer addAmount) {

        return wordService.addTodayWord(userId, addAmount);
    }

    /**
     * 描述:单词查询
     *
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<Word> find(@RequestParam("findWord") String findWord) {
        return wordService.find(findWord);
    }
}

