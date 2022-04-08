package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Plan;
import com.example.entity.PlanWord;
import com.example.entity.Word;
import com.example.mapper.PlanMapper;
import com.example.mapper.PlanWordMapper;
import com.example.mapper.WordMapper;
import com.example.service.WordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private PlanWordMapper planWordMapper;

    @Override
    public Word getRandomOne() {
        return wordMapper.getRandomOne();
    }

    @Override
    public Word queryOne(Integer id) {
        return wordMapper.selectById(id);
    }



    @Override
    public List<Word> getTodayWord(Integer userId) {
        //找到用户进行中的计划
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        planQueryWrapper.eq("state", true);
        Plan plan = planMapper.selectOne(planQueryWrapper);
        Integer planId = plan.getId();
        Integer amount = plan.getAmount();
        Integer pOrder = plan.getPOrder();
        //找到计划中的单词
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("study_time", LocalDate.now()).or().isNull("study_time");
        if (pOrder == 1) {
            planWordQueryWrapper.orderByAsc("word_id");
        }
        else if (pOrder == 2) {
            planWordQueryWrapper.orderByDesc("word_id");
        }
        else {
            planWordQueryWrapper.last("order by rand()");
        }
        planWordQueryWrapper.last("limit " + amount);
        List<PlanWord> planWordList = planWordMapper.selectList(planWordQueryWrapper);
        //根据plan_word找到word并返回
        List<Integer> idList = new ArrayList<>();
        for (PlanWord planWord : planWordList) {
            idList.add(planWord.getWordId());
        }
        List<Word> wordList = wordMapper.selectBatchIds(idList);
        return wordList;
    }
}
