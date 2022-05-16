package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Dictionary;
import com.example.entity.Plan;
import com.example.entity.Word;
import com.example.mapper.DictionaryMapper;
import com.example.mapper.PlanMapper;
import com.example.mapper.WordMapper;
import com.example.service.DictionaryService;
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
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private WordMapper wordMapper;

    @Override
    public List<Dictionary> queryAll() {
        return dictionaryMapper.selectList(null);
    }

    @Override
    public List<Dictionary> queryRest(Integer userId) {
        //查询已有计划中所有的词典id
        QueryWrapper<Plan> planQueryWrapper = new QueryWrapper<>();
        planQueryWrapper.eq("user_id", userId);
        List<Plan> plans = planMapper.selectList(planQueryWrapper);
        List<Integer> ids = new ArrayList<>();
        for (Plan plan : plans) {
            ids.add(plan.getDictionaryId());
        }
        //查询没有添加计划的词典
        QueryWrapper<Dictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        if (ids.size() > 0) dictionaryQueryWrapper.notIn("id", ids);
        return dictionaryMapper.selectList(dictionaryQueryWrapper);
    }

    @Override
    public List<Word> getAllWord(Integer dictionaryId) {
        QueryWrapper<Word> wordQueryWrapper = new QueryWrapper<>();
        List<Integer> dictionaryIds = new ArrayList<>();
        for (int i = 1; i <= dictionaryId; i++) {
            dictionaryIds.add(i);
        }
        wordQueryWrapper.in("dictionary_id", dictionaryIds);
        return wordMapper.selectList(wordQueryWrapper);
    }
}
