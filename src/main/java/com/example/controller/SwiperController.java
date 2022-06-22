package com.example.controller;

import com.example.service.SwiperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-06-21
 */
@RestController
@RequestMapping("/swiper")
public class SwiperController {

    @Autowired
    private SwiperService swiperService;

    /**
     * 描述:获取首页轮播图
     *
     */
    @RequestMapping(value = "getIndex", method = RequestMethod.GET)
    public Object getIndex() {
        return swiperService.getIndex();
    }

    /**
     * 描述:获取音乐页轮播图
     *
     */
    @RequestMapping(value = "getMusic", method = RequestMethod.GET)
    public Object getMusic() {
        return swiperService.getMusic();
    }

}

