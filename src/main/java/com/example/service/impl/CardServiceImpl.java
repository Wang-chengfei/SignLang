package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Card;
import com.example.entity.Plan;
import com.example.mapper.CardMapper;
import com.example.mapper.PlanMapper;
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
    @Autowired
    private PlanMapper planMapper;

    /**
     * 描述:今日打卡
     *
     */
    @Override
    public int clock(Integer userId) {
        final LocalDate now = LocalDate.now();
        int result = 0;
        //获取打卡单词数量
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        Plan plan = planMapper.selectOne(planQueryWrapper);
        Integer amount = plan.getTodayAmount();
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
            card.setCompleted(true);
            result = cardMapper.insert(card);
        }
        //如果今日已打卡，更新打卡单词数
        else {
            card1.setAmount(amount);
            card1.setCompleted(true);
            result = cardMapper.updateById(card1);
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

    /**
     * 描述:获取连续打卡天数
     *
     */
    @Override
    public Integer queryCardDays(Integer userId) {
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("user_id", userId);
        cardQueryWrapper.eq("completed", true);
        List<Card> cards = cardMapper.selectList(cardQueryWrapper);
        int idx = cards.size() - 1;
        LocalDate lastTime = cards.get(idx).getClockTime();
        if (lastTime.equals(LocalDate.now()) || lastTime.equals(LocalDate.now().minusDays(1))) {
            int count = 1;
            while(idx > 0) {
                idx--;
                lastTime = lastTime.minusDays(1);
                if (lastTime.equals(cards.get(idx))) count++;
                else break;
            }
            return count;
        }
        return 0;
    }
}
