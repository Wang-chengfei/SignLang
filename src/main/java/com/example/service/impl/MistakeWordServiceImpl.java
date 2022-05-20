package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.mapper.MistakeWordMapper;
import com.example.mapper.PlanMapper;
import com.example.mapper.PlanWordMapper;
import com.example.mapper.WordMapper;
import com.example.service.MistakeWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.service.PlanWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Service
public class MistakeWordServiceImpl extends ServiceImpl<MistakeWordMapper, MistakeWord> implements MistakeWordService {

    @Autowired
    private MistakeWordMapper mistakeWordMapper;
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private PlanWordMapper planWordMapper;
    @Autowired
    private PlanWordService planWordService;
    @Autowired
    private MistakeWordService mistakeWordService;
    @Autowired
    private PlanMapper planMapper;

    @Override
    public int add(Integer userId, Integer wordId, Integer planId) {
        int result = 0;
        //检查mistake_word表中是否存在该单词
        QueryWrapper<MistakeWord> mistakeWordQueryWrapper = new QueryWrapper<>();
        mistakeWordQueryWrapper.eq("word_id", wordId);
        mistakeWordQueryWrapper.eq("user_id", userId);
        MistakeWord mistakeWord1 = mistakeWordMapper.selectOne(mistakeWordQueryWrapper);
        if (mistakeWord1 == null) {
            //改动mistake_word表格
            MistakeWord mistakeWord = new MistakeWord();
            mistakeWord.setWordId(wordId);
            mistakeWord.setUserId(userId);
            result = mistakeWordMapper.insert(mistakeWord);
        }
        //改动plan_word表格
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("word_id", wordId);
        PlanWord planWord = planWordMapper.selectOne(planWordQueryWrapper);
        planWord.setIsMistake(true);
        planWordMapper.updateById(planWord);
        return result;
    }

    @Override
    public int addSeveral(Integer userId, List<Integer> wordIds, Integer planId) {
        int result;
        //删除mistake_word表中已经存在的wordId
        QueryWrapper<MistakeWord> mistakeWordQueryWrapper = new QueryWrapper<>();
        mistakeWordQueryWrapper.eq("user_id", userId);
        mistakeWordQueryWrapper.in("word_id", wordIds);
        List<MistakeWord> mistakeWords = mistakeWordMapper.selectList(mistakeWordQueryWrapper);
        for (MistakeWord mistakeWord : mistakeWords) {
            wordIds.remove(mistakeWord.getWordId());
        }
        result = wordIds.size();
        if (result > 0) {
            //改动mistake_word表格
            List<MistakeWord> mistakeWordList = new ArrayList<>();
            for (Integer wordId : wordIds) {
                MistakeWord mistakeWord = new MistakeWord();
                mistakeWord.setWordId(wordId);
                mistakeWord.setUserId(userId);
                mistakeWordList.add(mistakeWord);
            }
            mistakeWordService.saveBatch(mistakeWordList);
            //改动plan_word表格
            QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
            planWordQueryWrapper.eq("plan_id", planId);
            planWordQueryWrapper.in("word_id", wordIds);
            List<PlanWord> planWordList = planWordMapper.selectList(planWordQueryWrapper);
            for (PlanWord planWord : planWordList) {
                planWord.setIsMistake(true);
            }
            planWordService.updateBatchById(planWordList);
        }
        return result;
    }

    @Override
    public List<Word> queryAll(Integer userId) {
        //查询错误单词id
        QueryWrapper<MistakeWord> mistakeWordQueryWrapper = new QueryWrapper<>();
        mistakeWordQueryWrapper.eq("user_id", userId);
        List<MistakeWord> mistakeWordList = mistakeWordMapper.selectList(mistakeWordQueryWrapper);
        List<Integer> idList = new ArrayList<>();
        for (MistakeWord mistakeWord : mistakeWordList) {
            idList.add(mistakeWord.getWordId());
        }
        return wordMapper.selectBatchIds(idList);
    }

    @Override
    public int removeOne(Integer userId, Integer wordId) {
        //找到用户所有的planId
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        List<Integer> planIds = new ArrayList<>();
        for (Plan plan : plans) {
            planIds.add(plan.getId());
        }
        //改动mistake_word表格
        QueryWrapper<MistakeWord> mistakeWordQueryWrapper = new QueryWrapper<>();
        mistakeWordQueryWrapper.eq("user_id", userId);
        mistakeWordQueryWrapper.eq("word_id", wordId);
        //改动plan_word表格
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.in("plan_id", planIds);
        planWordQueryWrapper.eq("word_id", wordId);
        PlanWord planWord = planWordMapper.selectOne(planWordQueryWrapper);
        planWord.setIsMistake(false);
        planWordMapper.updateById(planWord);
        return mistakeWordMapper.delete(mistakeWordQueryWrapper);
    }

    @Override
    public int removeSeveral(Integer userId, List<Integer> wordIds) {
        //找到用户所有的planId
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        List<Integer> planIds = new ArrayList<>();
        for (Plan plan : plans) {
            planIds.add(plan.getId());
        }
        int result;
        //改动mistake_word表格
        QueryWrapper<MistakeWord> mistakeWordQueryWrapper = new QueryWrapper<>();
        mistakeWordQueryWrapper.eq("user_id", userId);
        mistakeWordQueryWrapper.in("word_id", wordIds);
        result = mistakeWordMapper.delete(mistakeWordQueryWrapper);
        //改动plan_word表格
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.in("plan_id", planIds);
        planWordQueryWrapper.in("word_id", wordIds);
        List<PlanWord> planWordList = planWordMapper.selectList(planWordQueryWrapper);
        for (PlanWord planWord : planWordList) {
            planWord.setIsMistake(false);
        }
        planWordService.updateBatchById(planWordList);
        return result;
    }
}
