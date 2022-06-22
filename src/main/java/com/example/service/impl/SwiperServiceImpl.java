package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Swiper;
import com.example.mapper.SwiperMapper;
import com.example.service.SwiperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-06-21
 */
@Service
public class SwiperServiceImpl extends ServiceImpl<SwiperMapper, Swiper> implements SwiperService {

    @Autowired
    private SwiperMapper swiperMapper;

    @Override
    public Object getIndex() {
        QueryWrapper<Swiper> swiperQueryWrapper = new QueryWrapper<>();
        swiperQueryWrapper.eq("type", "index");
        return swiperMapper.selectList(swiperQueryWrapper);
    }

    @Override
    public Object getMusic() {
        QueryWrapper<Swiper> swiperQueryWrapper = new QueryWrapper<>();
        swiperQueryWrapper.eq("type", "music");
        return swiperMapper.selectList(swiperQueryWrapper);
    }
}
