package com.example.service;

import com.example.entity.Plan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.PlanWord;
import com.example.entity.Word;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
public interface PlanService extends IService<Plan> {

    List<Plan> queryAll(Integer userId);

    Plan queryNow(Integer userId);

    int add(Plan plan);

    int removeOne(Integer id);

    int changeOne(Integer id, Integer amount, Integer pOrder);

    int switchPlan(Integer id);

    Map<PlanWord, Word> queryAllWord(Integer id);
}
