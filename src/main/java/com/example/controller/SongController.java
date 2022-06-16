package com.example.controller;


import com.example.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-06-13
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    private SongService songService;

    /**
     * 描述:获取所有歌单
     *
     */
    @RequestMapping(value = "getSongList", method = RequestMethod.GET)
    public Object getSongList() {
        return songService.getSongList();
    }

    /**
     * 描述:获取某一歌单下的所有歌曲
     *
     */
    @RequestMapping(value = "getSong", method = RequestMethod.GET)
    public Object getSong(@RequestParam("songListId") Integer songListId) {
        return songService.getSong(songListId);
    }

    /**
     * 描述:收藏某一歌曲
     *
     */
    @RequestMapping(value = "star", method = RequestMethod.POST)
    public Object star(@RequestParam("userId") Integer userId,
                       @RequestParam("songId") Integer songId) {
        return songService.star(userId, songId);
    }

    /**
     * 描述:取消收藏某一歌曲
     *
     */
    @RequestMapping(value = "cancelStar", method = RequestMethod.POST)
    public Object cancelStar(@RequestParam("userId") Integer userId,
                            @RequestParam("songId") Integer songId) {
        return songService.cancelStar(userId, songId);
    }

    /**
     * 描述:获取收藏的所有歌曲
     *
     */
    @RequestMapping(value = "getStar", method = RequestMethod.GET)
    public Object getStar(@RequestParam("userId") Integer userId) {
        return songService.getStar(userId);
    }

    /**
     * 描述:检查某一歌曲是否被收藏
     *
     */
    @RequestMapping(value = "isStar", method = RequestMethod.GET)
    public Object isStar(@RequestParam("userId") Integer userId,
                         @RequestParam("songId") Integer songId) {
        return songService.isStar(userId, songId);
    }


}

