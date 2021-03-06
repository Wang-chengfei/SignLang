package com.example.mapper;

import com.example.entity.Word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Repository
public interface WordMapper extends BaseMapper<Word> {

    Word getRandomOne();
}
