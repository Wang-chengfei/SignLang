package com.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Song;
import com.example.entity.StarSong;
import com.example.mapper.SongListMapper;
import com.example.mapper.SongMapper;
import com.example.mapper.StarSongMapper;
import com.example.service.SongService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-06-13
 */
@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SongListMapper songListMapper;
    @Autowired
    private StarSongMapper starSongMapper;

    @Override
    public Object getSongList() {
        return songListMapper.selectList(null);
    }

    @Override
    public Object getSong(Integer songListId, Integer userId) {
        List<Song> songs;
        //若无userId，直接返回
        if (userId == null) {
            QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
            songQueryWrapper.eq("song_list_id", songListId);
            songs = songMapper.selectList(songQueryWrapper);
            return songs;
        }
        //若有，则加上isStar字段
        if (songListId == 1) {
            songs = (List<Song>) getStar(userId);
        }
        else {
            QueryWrapper<Song> songQueryWrapper = new QueryWrapper<>();
            songQueryWrapper.eq("song_list_id", songListId);
            songs = songMapper.selectList(songQueryWrapper);
        }
        if (songs.size() == 0) return new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        List<Integer> songIds = new ArrayList<>();
        for (Song song : songs) {
            songIds.add(song.getId());
        }
        QueryWrapper<StarSong> starSongQueryWrapper = new QueryWrapper<>();
        starSongQueryWrapper.eq("user_id", userId);
        starSongQueryWrapper.in("song_id", songIds);
        List<StarSong> starSongs = starSongMapper.selectList(starSongQueryWrapper);
        for (Song song : songs) {
            Boolean isStar = false;
            for (StarSong starSong : starSongs) {
                if (starSong.getSongId() == song.getId()) {
                    isStar = true;
                    break;
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", song.getId());
            jsonObject.put("title", song.getTitle());
            jsonObject.put("author", song.getAuthor());
            jsonObject.put("imgUrl", song.getImgUrl());
            jsonObject.put("url", song.getUrl());
            jsonObject.put("songListId", song.getSongListId());
            jsonObject.put("introduction", song.getIntroduction());
            jsonObject.put("isStar", isStar);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public Object star(Integer userId, Integer songId) {
        if (isStar(userId, songId)) return "文章已被收藏";
        StarSong starSong = new StarSong();
        starSong.setUserId(userId);
        starSong.setSongId(songId);
        return starSongMapper.insert(starSong);
    }

    @Override
    public Boolean isStar(Integer userId, Integer songId) {
        QueryWrapper<StarSong> starSongQueryWrapper = new QueryWrapper<>();
        starSongQueryWrapper.eq("user_id", userId);
        starSongQueryWrapper.eq("song_id", songId);
        StarSong starSong = starSongMapper.selectOne(starSongQueryWrapper);
        if (starSong == null) return false;
        return true;
    }

    @Override
    public Object cancelStar(Integer userId, Integer songId) {
        QueryWrapper<StarSong> starSongQueryWrapper = new QueryWrapper<>();
        starSongQueryWrapper.eq("user_id", userId);
        starSongQueryWrapper.eq("song_id", songId);
        StarSong starSong = starSongMapper.selectOne(starSongQueryWrapper);
        if (starSong == null) return "文章未被收藏";
        return starSongMapper.delete(starSongQueryWrapper);
    }

    @Override
    public Object getStar(Integer userId) {
        //获取用户收藏歌曲的id
        QueryWrapper<StarSong> starSongQueryWrapper = new QueryWrapper<>();
        starSongQueryWrapper.eq("user_id", userId);
        List<StarSong> starSongs = starSongMapper.selectList(starSongQueryWrapper);
        if (starSongs.size() == 0) return new ArrayList();
                List<Integer> songIds = new ArrayList<>();
        for (StarSong starSong : starSongs) {
            songIds.add(starSong.getSongId());
        }
        //根据歌曲id取得歌曲
        List<Song> songs = songMapper.selectBatchIds(songIds);
        return songs;
    }
}
