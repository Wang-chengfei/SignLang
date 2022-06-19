package com.example.service;

import com.example.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-06-19
 */
public interface RecordService extends IService<Record> {

    Object add(Integer userId, String sentence);

    Object removeOne(Integer recordId);

    Object get(Integer userId, Integer num);
}
