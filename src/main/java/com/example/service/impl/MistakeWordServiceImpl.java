package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.MistakeWord;
import com.example.entity.PlanWord;
import com.example.mapper.MistakeWordMapper;
import com.example.mapper.PlanWordMapper;
import com.example.mapper.WordMapper;
import com.example.service.MistakeWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public int add(Integer userId, Integer wordId, Integer planId) {
        //改动mistake_word表格
        MistakeWord mistakeWord = new MistakeWord();
        mistakeWord.setWordId(wordId);
        mistakeWord.setUserId(userId);
        //改动plan_word表格
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("word_id", wordId);
        PlanWord planWord = planWordMapper.selectOne(planWordQueryWrapper);
        planWord.setIsMistake(true);
        planWordMapper.updateById(planWord);
        return mistakeWordMapper.insert(mistakeWord);
    }
}
