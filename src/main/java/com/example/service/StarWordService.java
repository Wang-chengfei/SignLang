package com.example.service;

import com.example.entity.StarWord;
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
public interface StarWordService extends IService<StarWord> {

    int add(Integer userId, Integer wordId);

    List<Word> queryAll(Integer userId);

    int removeOne(Integer userId, Integer wordId);
}
