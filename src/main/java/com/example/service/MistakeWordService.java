package com.example.service;

import com.example.entity.MistakeWord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Word;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
public interface MistakeWordService extends IService<MistakeWord> {

    int add(Integer userId, Integer wordId, Integer planId);

    int addSeveral(Integer userId, List<Integer> wordIds, Integer planId);

    List<Word> queryAll(Integer userId);

    int removeOne(Integer userId, Integer wordId);

    int removeSeveral(Integer userId, List<Integer> wordIds);
}
