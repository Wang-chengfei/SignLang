package com.example.service;

import com.example.entity.PlanWord;
import com.example.entity.PlanWordReturn;
import com.example.entity.Word;
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
public interface WordService extends IService<Word> {

    Word getRandomOne();

    Object queryOne(Integer id, Integer userId);

    List<PlanWordReturn> getTodayWord(Integer userId);

    boolean completeWord(Integer userId, List<Integer> wordIds);

    int getTodayLearned(Integer userId);

    int addTodayWord(Integer userId, Integer addAmount);

    List<Word> find(String findWord);
}
