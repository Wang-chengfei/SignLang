package com.example.service;

import com.example.entity.Sentence;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-05-31
 */
public interface SentenceService extends IService<Sentence> {

    Object queryAll(Integer userId);

    Object addGroup(Integer userId, String name);

    Object removeGroup(Integer userId, String name);

    Object updateGroup(Integer userId, String name, String newName);

    Object addSentence(Integer userId, String name, String content);

    Object removeSentence(Integer sentenceId);

    Object updateSentence(Integer sentenceId, String content);
}
