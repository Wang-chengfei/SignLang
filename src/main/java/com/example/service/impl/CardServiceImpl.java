package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Card;
import com.example.mapper.CardMapper;
import com.example.service.CardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-11
 */
@Service
public class CardServiceImpl extends ServiceImpl<CardMapper, Card> implements CardService {

    @Autowired
    private CardMapper cardMapper;

    /**
     * 描述:今日打卡
     *
     */
    @Override
    public int clock(Integer userId, Integer amount) {
        final LocalDate now = LocalDate.now();
        int result = 0;
        //检查今日是否已打卡
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("user_id", userId);
        cardQueryWrapper.eq("clock_time", now);
        Card card1 = cardMapper.selectOne(cardQueryWrapper);
        //如果今日未打卡，则打卡
        if (card1 == null) {
            Card card = new Card();
            card.setUserId(userId);
            card.setClockTime(now);
            card.setAmount(amount);
            result = cardMapper.insert(card);
        }
        return result;
    }

    /**
     * 描述:获取打卡记录
     *
     */
    @Override
    public List<Card> queryAll(Integer userId) {
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("user_id", userId);
        return cardMapper.selectList(cardQueryWrapper);
    }

    /**
     * 描述:获取今日打卡信息
     *
     */
    @Override
    public Card queryToday(Integer userId) {
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("user_id", userId);
        cardQueryWrapper.eq("clock_time", LocalDate.now());
        return cardMapper.selectOne(cardQueryWrapper);
    }
}
