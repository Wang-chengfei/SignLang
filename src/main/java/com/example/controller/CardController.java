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
    public int clock(@RequestParam("userId") Integer userId,
                     @RequestParam("amount") Integer amount) {
        return cardService.clock(userId, amount);
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
}

