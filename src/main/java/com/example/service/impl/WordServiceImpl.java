package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Plan;
import com.example.entity.PlanWord;
import com.example.entity.PlanWordReturn;
import com.example.entity.Word;
import com.example.mapper.PlanMapper;
import com.example.mapper.PlanWordMapper;
import com.example.mapper.WordMapper;
import com.example.service.PlanWordService;
import com.example.service.WordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private PlanWordMapper planWordMapper;
    @Autowired
    private PlanWordService planWordService;

    @Override
    public Word getRandomOne() {
        return wordMapper.getRandomOne();
    }

    @Override
    public Word queryOne(Integer id) {
        return wordMapper.selectById(id);
    }

    /**
     * 描述:获取今日单词
     *
     */
    @Override
    public List<PlanWordReturn> getTodayWord(Integer userId) {
        LocalDate today = LocalDate.now();
        //找到用户进行中的计划
        Plan plan = planMapper.mySelectOne(userId, true);
        Integer planId = plan.getId();
        Integer amount = plan.getAmount();
        Integer pOrder = plan.getPOrder();
        LocalDate lastTime = plan.getLastTime();
        if (!today.equals(lastTime)) {
            plan.setLastTime(today);
            plan.setTodayAmount(amount);
            planMapper.updateById(plan);
        }
        else {
            amount = plan.getTodayAmount();
        }
        //找到计划中的单词
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("study_time", LocalDate.now()).or().isNull("study_time");
        if (pOrder == 1) {
            planWordQueryWrapper.orderByDesc("study_time").orderByAsc("word_id");
            planWordQueryWrapper.last("limit " + amount);
        }
        else if (pOrder == 2) {
            planWordQueryWrapper.orderByDesc("study_time").orderByDesc("word_id");
            planWordQueryWrapper.last("limit " + amount);
        }
        else {
            planWordQueryWrapper.last("order by study_time desc, rand(1) limit " + amount);
        }
        List<PlanWord> planWordList = planWordMapper.selectList(planWordQueryWrapper);
        if (planWordList.size() == 0) return null;
        //根据plan_word找到word
        List<Integer> idList = new ArrayList<>();
        for (PlanWord planWord : planWordList) {
            idList.add(planWord.getWordId());
        }
        List<Word> wordList = wordMapper.selectBatchIds(idList);
        //planWordReturn中增加word变量
        List<PlanWordReturn> planWordReturnList = new ArrayList<>();
        for (PlanWord planWord : planWordList) {
            Integer wordId = planWord.getWordId();
            Word word = wordList.stream().filter(item -> item.getId().equals(wordId)).collect(Collectors.toList()).get(0);
            PlanWordReturn planWordReturn = new PlanWordReturn(planWord);
            planWordReturn.setWord(word);
            planWordReturnList.add(planWordReturn);
        }
        return planWordReturnList;
    }

    /**
     * 描述:完成若干个单词的学习
     *
     */
    @Override
    public boolean completeWord(Integer userId, List<Integer> wordIds) {
        int count = 0;
        //找到用户进行中的计划
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        Plan plan = planMapper.selectOne(planQueryWrapper);
        Integer planId = plan.getId();
        //找到计划中的单词
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.in("word_id", wordIds);
        List<PlanWord> planWordList = planWordMapper.selectList(planWordQueryWrapper);
        //更新为已完成状态
        final LocalDate now = LocalDate.now();
        for (PlanWord planWord : planWordList) {
            if (planWord.getCompleted() == false) {
                count++;
                planWord.setCompleted(true);
            }
            planWord.setStudyTime(now);
        }
        //更新plan表格中已完成的单词数
        plan.setLearnedNumber(plan.getLearnedNumber() + count);
        planMapper.updateById(plan);
        return planWordService.updateBatchById(planWordList);
    }

    /**
     * 描述:获取今日已学习单词数
     *
     */
    @Override
    public int getTodayLearned(Integer userId) {
        //找到用户进行中的计划
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        Plan plan = planMapper.selectOne(planQueryWrapper);
        Integer planId = plan.getId();
        //查询今日已学习单词数
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("study_time", LocalDate.now());
        return planWordMapper.selectCount(planWordQueryWrapper);
    }

    /**
     * 描述:增加今天学习的单词数量
     *
     */
    @Override
    public int addTodayWord(Integer userId, Integer addAmount) {
        LocalDate today = LocalDate.now();
        //找到用户进行中的计划
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        Plan plan = planMapper.selectOne(planQueryWrapper);
        //更新今日单词数
        LocalDate lastTime = plan.getLastTime();
        if (!today.equals(lastTime)) {
            plan.setLastTime(today);
            plan.setTodayAmount(plan.getAmount() + addAmount);
        }
        else {
            plan.setTodayAmount(plan.getTodayAmount() + addAmount);
        }
        return planMapper.updateById(plan);
    }


    /**
     * 描述：单词查询
     *
     */
    @Override
    public List<Word> find(String findWord) {
        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        wordQueryWrapper.like("answer", findWord);
        return wordMapper.selectList(wordQueryWrapper);
    }

}
