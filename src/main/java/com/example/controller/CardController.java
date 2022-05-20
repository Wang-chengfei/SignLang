package com.example.controller;


import com.example.entity.Card;
import com.example.service.CardService;
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
 * @since 2022-04-11
 */
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * 描述:今日打卡
     *
     */
    @RequestMapping(value = "/clock", method = RequestMethod.POST)
    public int clock(@RequestParam("userId") Integer userId) {
        return cardService.clock(userId);
    }

    /**
     * 描述:获取打卡记录
     *
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<Card> queryAll(@RequestParam("userId") Integer userId) {
        return cardService.queryAll(userId);
    }

    /**
     * 描述:获取今日打卡信息
     *
     */
    @RequestMapping(value = "/queryToday", method = RequestMethod.GET)
    public Card queryToday(@RequestParam("userId") Integer userId) {
        return cardService.queryToday(userId);
    }

    /**
     * 描述:获取连续打卡天数
     *
     */
    @RequestMapping(value = "/queryCardDays", method = RequestMethod.GET)
    public Integer queryCardDays(@RequestParam("userId") Integer userId) {
        return cardService.queryCardDays(userId);
    }

    /**
     * 描述:获取累计学习单词数
     *
     */
    @RequestMapping(value = "/queryLearnedWord", method = RequestMethod.GET)
    public Integer queryLearnedWord(@RequestParam("userId") Integer userId) {
        return cardService.queryLearnedWord(userId);
    }

    /**
     * 描述:获取累计学习天数
     *
     */
    @RequestMapping(value = "/queryLearnedDay", method = RequestMethod.GET)
    public Integer queryLearnedDay(@RequestParam("userId") Integer userId) {
        return cardService.queryLearnedDay(userId);
    }

    /**
     * 描述:取消今日打卡
     *
     */
    @RequestMapping(value = "/cancelClock", method = RequestMethod.POST)
    public int cancelClock(@RequestParam("userId") Integer userId) {
        return cardService.cancelClock(userId);
    }

    /**
     * 描述:按周统计已学习单词数
     *
     */
    @RequestMapping(value = "/queryLearnedByWeek", method = RequestMethod.GET)
    public Object queryLearnedByWeek(@RequestParam("userId") Integer userId) {
        return cardService.queryLearnedByWeek(userId);
    }

    /**
     * 描述:按月统计已学习单词数
     *
     */
    @RequestMapping(value = "/queryLearnedByMonth", method = RequestMethod.GET)
    public Object queryLearnedByMonth(@RequestParam("userId") Integer userId) {
        return cardService.queryLearnedByMonth(userId);
    }

}

