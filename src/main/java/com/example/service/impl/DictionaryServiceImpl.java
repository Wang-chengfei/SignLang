package com.example.service.impl;

import com.example.entity.Dictionary;
import com.example.mapper.DictionaryMapper;
import com.example.service.DictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Service
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public List<Dictionary> queryAll() {
        return dictionaryMapper.selectList(null);
    }
}
