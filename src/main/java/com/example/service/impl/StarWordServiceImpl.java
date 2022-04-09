package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.PlanWord;
import com.example.entity.StarWord;
import com.example.entity.Word;
import com.example.mapper.PlanWordMapper;
import com.example.mapper.StarWordMapper;
import com.example.mapper.WordMapper;
import com.example.service.StarWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class StarWordServiceImpl extends ServiceImpl<StarWordMapper, StarWord> implements StarWordService {

    @Autowired
    private StarWordMapper starWordMapper;
    @Autowired
    private WordMapper wordMapper;
    @Autowired
    private PlanWordMapper planWordMapper;

    @Override
    public int add(Integer userId, Integer wordId, Integer planId) {
        //改动star_word表格
        StarWord starWord = new StarWord();
        starWord.setUserId(userId);
        starWord.setWordId(wordId);
        //改动plan_word表格
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("word_id", wordId);
        PlanWord planWord = planWordMapper.selectOne(planWordQueryWrapper);
        planWord.setIsStar(true);
        planWordMapper.updateById(planWord);
        return starWordMapper.insert(starWord);
    }

    @Override
    public List<Word> queryAll(Integer userId) {
        //查询收藏单词id
        QueryWrapper<StarWord> starWordQueryWrapper = new QueryWrapper<>();
        starWordQueryWrapper.eq("user_id", userId);
        List<StarWord> starWordList = starWordMapper.selectList(starWordQueryWrapper);
        List<Integer> idList = new ArrayList<>();
        for (StarWord starWord : starWordList) {
            idList.add(starWord.getWordId());
        }
        return wordMapper.selectBatchIds(idList);
    }

    @Override
    public int removeOne(Integer userId, Integer wordId, Integer planId) {
        //改动star_word表格
        QueryWrapper<StarWord> starWordQueryWrapper = new QueryWrapper<>();
        starWordQueryWrapper.eq("user_id", userId);
        starWordQueryWrapper.eq("word_id", wordId);
        //改动plan_word表格
        QueryWrapper<PlanWord> planWordQueryWrapper = new QueryWrapper<>();
        planWordQueryWrapper.eq("plan_id", planId);
        planWordQueryWrapper.eq("word_id", wordId);
        PlanWord planWord = planWordMapper.selectOne(planWordQueryWrapper);
        planWord.setIsStar(false);
        planWordMapper.updateById(planWord);
        return starWordMapper.delete(starWordQueryWrapper);
    }
}
