package com.example.service.impl;

import com.example.entity.Word;
import com.example.mapper.WordMapper;
import com.example.service.WordService;
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
 * @since 2022-03-27
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Autowired
    private WordMapper wordMapper;

    @Override
    public List<Word> queryAllWord() {
        return wordMapper.selectList(null);
    }
}
