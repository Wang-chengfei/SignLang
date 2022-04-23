package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Plan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Repository
public interface PlanMapper extends BaseMapper<Plan> {

    Plan mySelectOne(Integer userId, Boolean state);

    List<Plan> mySelectList(Integer userId);
}
