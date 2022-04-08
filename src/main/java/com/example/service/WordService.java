package com.example.service;

import com.example.entity.Word;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    Word queryOne(Integer id);

    List<Word> getTodayWord(Integer userId);
}
