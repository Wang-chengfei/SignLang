package com.example.service;

import com.example.entity.Song;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-06-13
 */
public interface SongService extends IService<Song> {

    Object getSongList();

    Object getSong(Integer songListId);

    Object star(Integer userId, Integer songId);

    Object isStar(Integer userId, Integer songId);

    Object cancelStar(Integer userId, Integer songId);

    Object getStar(Integer userId);
}
