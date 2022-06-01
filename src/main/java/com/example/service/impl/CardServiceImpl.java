package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Card;
import com.example.entity.Plan;
import com.example.entity.PlanWord;
import com.example.mapper.CardMapper;
import com.example.mapper.PlanMapper;
import com.example.mapper.PlanWordMapper;
import com.example.service.CardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

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
    @Autowired
    private PlanWordMapper planWordMapper;

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
        if (cards.size() == 0) return 0;
        int idx = cards.size() - 1;
        LocalDate lastTime = cards.get(idx).getClockTime();
        if (lastTime.equals(LocalDate.now()) || lastTime.equals(LocalDate.now().minusDays(1))) {
            int count = 1;
            while(idx > 0) {
                idx--;
                lastTime = lastTime.minusDays(1);
                if (lastTime.equals(cards.get(idx).getClockTime())) count++;
                else break;
            }
            return count;
        }
        return 0;
    }

    /**
     * 描述:获取累计学习单词数
     *
     */
    @Override
    public Integer queryLearnedWord(Integer userId) {
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        int count = 0;
        for (Plan plan : plans) {
            count += plan.getLearnedNumber();
        }
        return count;
    }

    /**
     * 描述:获取累计学习天数
     *
     */
    @Override
    public Integer queryLearnedDay(Integer userId) {
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("user_id", userId);
        cardQueryWrapper.eq("completed", true);
        List<Card> cards = cardMapper.selectList(cardQueryWrapper);
        return cards.size();
    }

    /**
     * 描述:取消今日打卡
     *
     */
    @Override
    public int cancelClock(Integer userId) {
        QueryWrapper<Card> cardQueryWrapper = new QueryWrapper<>();
        cardQueryWrapper.eq("user_id", userId);
        cardQueryWrapper.eq("clock_time", LocalDate.now());
        Card card = cardMapper.selectOne(cardQueryWrapper);
        if (card == null) return 0;
        card.setCompleted(false);
        return cardMapper.updateById(card);
    }

    /**
     * 描述:按周统计已学习单词数
     *
     */
    @Override
    public Object queryLearnedByWeek(Integer userId) {
        //获取用户所有计划id
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        List<Integer> planIds = new ArrayList<>();
        for (Plan plan : plans) {
            planIds.add(plan.getId());
        }
        //根据计划id在plan_word表中查询过去七天学习单词数量
        Map<LocalDate, Integer> map = new TreeMap<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
            planWordQueryWrapper.in("plan_id", planIds);
            planWordQueryWrapper.eq("completed", true);
            planWordQueryWrapper.eq("study_time", date);
            Integer number = planWordMapper.selectCount(planWordQueryWrapper);
            map.put(date, number);
            date = date.minusDays(1);
        }
        return map;
    }

    /**
     * 描述:按月统计已学习单词数
     *
     */
    @Override
    public Object queryLearnedByMonth(Integer userId) {
        //获取用户所有计划id
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        List<Integer> planIds = new ArrayList<>();
        for (Plan plan : plans) {
            planIds.add(plan.getId());
        }
        //根据计划id在plan_word表中查询过去六个月的学习单词数量
        Map<Integer, Integer> map = new HashMap<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 6; i++) {
            int len = date.getDayOfMonth();
            List<LocalDate> dates = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                dates.add(date);
                date = date.minusDays(1);
            }
            QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
            planWordQueryWrapper.in("plan_id", planIds);
            planWordQueryWrapper.eq("completed", true);
            planWordQueryWrapper.in("study_time", dates);
            Integer number = planWordMapper.selectCount(planWordQueryWrapper);
            map.put(date.plusDays(1).getMonthValue(), number);
        }
        return map;
    }
}
