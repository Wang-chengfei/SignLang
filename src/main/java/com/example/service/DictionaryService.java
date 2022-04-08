package com.example.service;

import com.example.entity.Dictionary;
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
public interface DictionaryService extends IService<Dictionary> {

    List<Dictionary> queryAll();
}
