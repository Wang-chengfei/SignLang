package com.example.service;

import com.example.entity.Swiper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-06-21
 */
public interface SwiperService extends IService<Swiper> {

    Object getIndex();

    Object getMusic();
}
