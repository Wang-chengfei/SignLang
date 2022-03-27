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
 * @since 2022-03-27
 */
public interface WordService extends IService<Word> {

    List<Word> queryAllWord();
}
