package com.example.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanReturn {

    public PlanReturn (Plan plan) {
        this.id = plan.getId();
        this.userId = plan.getUserId();
        this.dictionaryId = plan.getDictionaryId();
        this.amount = plan.getAmount();
        this.pOrder = plan.getPOrder();
        this.startTime = plan.getStartTime();
        this.completed = plan.getCompleted();
        this.totalNumber = plan.getTotalNumber();
        this.learnedNumber = plan.getLearnedNumber();
        this.state = plan.getState();
        this.lastTime = plan.getLastTime();
        this.todayAmount = plan.getTodayAmount();
    }

    private Integer id;

    private Integer userId;

    private Integer dictionaryId;

    private Integer amount;

    private Integer pOrder;

    private LocalDate startTime;

    private Boolean completed;

    private Integer totalNumber;

    private Integer learnedNumber;

    private Boolean state;

    private LocalDate lastTime;

    private Integer todayAmount;

    private Dictionary dictionary;

    private Integer mistakeNum;

}
