package com.example.service;

import com.example.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;

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

    List<PlanReturn> queryAll(Integer userId);

    PlanReturn queryNow(Integer userId);

    int add(Plan plan);

    int removeOne(Integer id);

    int changeOne(Integer id, Integer amount, Integer pOrder);

    int switchPlan(Integer id);

    List<PlanWordReturn> queryAllWord(Integer id);
}
