package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.entity.Dictionary;
import com.example.mapper.DictionaryMapper;
import com.example.mapper.PlanMapper;
import com.example.mapper.PlanWordMapper;
import com.example.mapper.WordMapper;
import com.example.service.DictionaryService;
import com.example.service.PlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.service.PlanWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Service
public class PlanServiceImpl extends ServiceImpl<PlanMapper, Plan> implements PlanService {

    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private PlanService planService;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private PlanWordMapper planWordMapper;
    @Autowired
    private PlanWordService planWordService;

    /**
     * 描述:查询所有计划
     *
     */
    @Override
    public List<PlanReturn> queryAll(Integer userId) {
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        if (plans.size() == 0) return null;
        List<PlanReturn> planReturns = new ArrayList<>();
        for (Plan plan : plans) {
            PlanReturn planReturn = new PlanReturn(plan);
            planReturns.add(planReturn);
        }
        //获取计划对应的词典
        for (PlanReturn planReturn : planReturns) {
            Dictionary dictionary = dictionaryMapper.selectById(planReturn.getDictionaryId());
            planReturn.setDictionary(dictionary);
        }
        return planReturns;
    }

    /**
     * 描述:查询正在进行中的计划
     *
     */
    @Override
    public PlanReturn queryNow(Integer userId) {
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        Plan plan = planMapper.selectOne(planQueryWrapper);
        if (plan == null) return null;
        PlanReturn planReturn = new PlanReturn(plan);
        //获取计划对应的词典
        Integer dictionaryId = plan.getDictionaryId();
        QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        dictionaryQueryWrapper.eq("id", dictionaryId);
        Dictionary dictionary = dictionaryMapper.selectOne(dictionaryQueryWrapper);
        planReturn.setDictionary(dictionary);
        return planReturn;
    }

    /**
     * 描述:添加计划
     *
     */
    @Override
    public int add(Plan plan) {
        //设置计划的一些默认值
        plan.setStartTime(LocalDate.now());
        plan.setCompleted(false);
        plan.setLearnedNumber(0);
        plan.setState(false);
        plan.setLastTime(LocalDate.now());
        plan.setTodayAmount(plan.getAmount());
        //获取并设置计划的总单词数
        QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        dictionaryQueryWrapper.eq("id", plan.getDictionaryId());
        Dictionary dictionary = dictionaryMapper.selectOne(dictionaryQueryWrapper);
        int totalNumber = dictionary.getTotalNumber();
        plan.setTotalNumber(totalNumber);
        //向plan表中插入数据
        int result = planMapper.insert(plan);
        int planId = plan.getId();
        //向plan_word表中添加数据
        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        wordQueryWrapper.eq("dictionary_id", plan.getDictionaryId());
        List<Word> wordList = wordMapper.selectList(wordQueryWrapper);
        List<PlanWord> planWordList = new ArrayList<>();
        for (Word word : wordList) {
            PlanWord planWord = new PlanWord();
            planWord.setPlanId(planId);
            planWord.setWordId(word.getId());
            planWordList.add(planWord);
        }
        planWordService.saveBatch(planWordList);
        return result;
    }

    /**
     * 描述:删除某一计划
     *
     */
    @Override
    public int removeOne(Integer id) {
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", id);
        planWordMapper.delete(planWordQueryWrapper);
        return planMapper.deleteById(id);
    }

    /**
     * 描述:修改某一计划
     *
     */
    @Override
    public int changeOne(Integer id, Integer amount, Integer pOrder) {
        Plan plan = planMapper.selectById(id);
        if (amount != null) plan.setAmount(amount);
        if (pOrder != null) plan.setPOrder(pOrder);
        return planMapper.updateById(plan);
    }

    /**
     * 描述:切换计划
     *
     */
    @Override
    public int switchPlan(Integer id) {
        Plan plan = planMapper.selectById(id);
        plan.setState(true);
        Integer userId = plan.getUserId();
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        List<Plan> previousPlan = planMapper.selectList(planQueryWrapper);
        for (Plan plan1 : previousPlan) {
            plan1.setState(false);
        }
        if (previousPlan.size() > 0) {
            planService.updateBatchById(previousPlan);
        }
        return planMapper.updateById(plan);
    }

    /**
     * 描述:查询计划中的所有单词
     *
     */
    @Override
    public List<PlanWordReturn> queryAllWord(Integer id) {
        //查找出所有plan_word
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", id);
        List<PlanWord> planWordList = planWordMapper.selectList(planWordQueryWrapper);
        //根据plan_word查找出word
        List<Integer> wordIdList = new ArrayList<>();
        for (PlanWord planWord : planWordList) {
            wordIdList.add(planWord.getWordId());
        }
        List<Word> wordList = wordMapper.selectBatchIds(wordIdList);
        //整合为planWordReturn
        List<PlanWordReturn> planWordReturnList = new ArrayList<>();
        int size = wordList.size();
        for (int i = 0; i < size; i++) {
            PlanWordReturn planWordReturn = new PlanWordReturn(planWordList.get(i));
            planWordReturn.setWord(wordList.get(i));
            planWordReturnList.add(planWordReturn);
        }
        return planWordReturnList;
    }
}
