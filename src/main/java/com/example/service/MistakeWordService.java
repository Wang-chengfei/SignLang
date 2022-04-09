package com.example.service;

import com.example.entity.MistakeWord;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
