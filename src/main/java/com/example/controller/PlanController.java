package com.example.controller;


import com.example.entity.Plan;
import com.example.entity.PlanWord;
import com.example.entity.Word;
import com.example.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@RestController
@RequestMapping("/plan")
public class PlanController {

    @Autowired
    private PlanService planService;

    /**
     * 描述:查询所有计划
     *
     */
    @RequestMapping(value = "queryAll", method = RequestMethod.GET)
    public List<Plan> queryAll(@RequestParam("userId") Integer userId) {
        return planService.queryAll(userId);
    }

    /**
     * 描述:查询正在进行中的计划
     *
     */
    @RequestMapping(value = "queryNow", method = RequestMethod.GET)
    public Plan queryNow(@RequestParam("userId") Integer userId) {
        return planService.queryNow(userId);
    }

    /**
     * 描述:添加计划
     *
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public int add(Plan plan) {
        return planService.add(plan);
    }

    /**
     * 描述:删除某一计划
     *
     */
    @RequestMapping(value = "removeOne", method = RequestMethod.POST)
    public int removeOne(@RequestParam("id") Integer id) {
        return planService.removeOne(id);
    }

    /**
     * 描述:修改某一计划
     *
     */
    @RequestMapping(value = "changeOne", method = RequestMethod.POST)
    public int changeOne(@RequestParam("id") Integer id,
                         @RequestParam(value = "amount", required = false) Integer amount,
                         @RequestParam(value = "pOrder", required = false) Integer pOrder) {
        return planService.changeOne(id, amount, pOrder);
    }

    /**
     * 描述:切换计划
     *
     */
    @RequestMapping(value = "switchPlan", method = RequestMethod.POST)
    public int switchPlan(@RequestParam("id") Integer id) {
        return planService.switchPlan(id);
    }

    /**
     * 描述:查询计划中的所有单词
     *
     */
    @RequestMapping(value = "queryAllWord", method = RequestMethod.GET)
    public Map<PlanWord, Word> queryAllWord(@RequestParam("id") Integer id) {
        return planService.queryAllWord(id);
    }

}

