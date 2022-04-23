package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Dictionary;
import com.example.entity.Plan;
import com.example.mapper.DictionaryMapper;
import com.example.mapper.PlanMapper;
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
        dictionaryQueryWrapper.notIn("id", ids);
        return dictionaryMapper.selectList(dictionaryQueryWrapper);
    }
}
