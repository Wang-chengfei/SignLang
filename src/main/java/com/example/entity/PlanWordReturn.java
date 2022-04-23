package com.example.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanWordReturn {

    public PlanWordReturn(PlanWord planWord) {
        this.id = planWord.getId();
        this.planId = planWord.getPlanId();
        this.wordId = planWord.getWordId();
        this.completed = planWord.getCompleted();
        this.studyTime = planWord.getStudyTime();
        this.isStar = planWord.getIsStar();
        this.isMistake = planWord.getIsMistake();
    }


    private Integer id;

    private Integer planId;

    private Integer wordId;

    private Boolean completed;

    private LocalDate studyTime;

    private Boolean isStar;

    private Boolean isMistake;

    private Word word;
}
