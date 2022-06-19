package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Record;
import com.example.mapper.RecordMapper;
import com.example.service.RecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-06-19
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public Object add(Integer userId, String sentence) {
        Record record = new Record();
        record.setUserId(userId);
        record.setSentence(sentence);
        record.setTime(LocalDateTime.now());
        return recordMapper.insert(record);
    }

    @Override
    public Object removeOne(Integer recordId) {
        return recordMapper.deleteById(recordId);
    }

    @Override
    public Object get(Integer userId, Integer num) {
        QueryWrapper<Record> recordQueryWrapper = new QueryWrapper<>();
        recordQueryWrapper.eq("user_id", userId);
        recordQueryWrapper.orderByDesc("time");
        if (num != null) {
            recordQueryWrapper.last("limit 0," + num);
        }
        return recordMapper.selectList(recordQueryWrapper);
    }
}
