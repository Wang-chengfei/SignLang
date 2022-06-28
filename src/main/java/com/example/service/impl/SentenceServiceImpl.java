package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Sentence;
import com.example.entity.SentenceGroup;
import com.example.mapper.SentenceGroupMapper;
import com.example.mapper.SentenceMapper;
import com.example.service.SentenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-05-31
 */
@Service
public class SentenceServiceImpl extends ServiceImpl<SentenceMapper, Sentence> implements SentenceService {

    @Autowired
    private SentenceMapper sentenceMapper;
    @Autowired
    private SentenceGroupMapper sentenceGroupMapper;

    /**
     * 描述:查询所有常用句子
     *
     */
    @Override
    public Object queryAll(Integer userId) {
        //获取用户所有的句子类型
        QueryWrapper<SentenceGroup> sentenceGroupQueryWrapper = new QueryWrapper<>();
        sentenceGroupQueryWrapper.eq("user_id", userId);
        List<SentenceGroup> sentenceGroups = sentenceGroupMapper.selectList(sentenceGroupQueryWrapper);
        //从数据库读数据并封装成map返回
        Map<String, List<Sentence>> sentences = new LinkedHashMap<>();
        //sentenceGroup
        for (SentenceGroup sentenceGroup : sentenceGroups) {
            Integer groupId = sentenceGroup.getId();
            //获取sentence
            QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
            sentenceQueryWrapper.eq("group_id", groupId);
            List<Sentence> groupSentences = sentenceMapper.selectList(sentenceQueryWrapper);
            sentences.put(sentenceGroup.getName(), groupSentences);
        }
        return sentences;
    }


    /**
     * 描述:添加分组
     *
     */
    @Override
    public Object addGroup(Integer userId, String name) {
        //获取用户所有分组
        QueryWrapper<SentenceGroup> sentenceGroupQueryWrapper = new QueryWrapper<>();
        sentenceGroupQueryWrapper.eq("user_id", userId);
        List<SentenceGroup> sentenceGroups = sentenceGroupMapper.selectList(sentenceGroupQueryWrapper);
        //检查是否有重复
        for (SentenceGroup sentenceGroup : sentenceGroups) {
            if (name.equals(sentenceGroup.getName())) {
                return "分组名称重复";
            }
        }
        //插入新分组
        SentenceGroup sentenceGroup = new SentenceGroup();
        sentenceGroup.setName(name);
        sentenceGroup.setUserId(userId);
        return sentenceGroupMapper.insert(sentenceGroup);
    }

    /**
     * 描述:删除分组
     *
     */
    @Override
    public Object removeGroup(Integer userId, String name) {
        //获取用户指定分组
        QueryWrapper<SentenceGroup> sentenceGroupQueryWrapper = new QueryWrapper<>();
        sentenceGroupQueryWrapper.eq("user_id", userId);
        sentenceGroupQueryWrapper.eq("name", name);
        SentenceGroup sentenceGroup = sentenceGroupMapper.selectOne(sentenceGroupQueryWrapper);
        if (sentenceGroup == null) return "分组不存在";
        //删除分组下的所有句子
        QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
        sentenceQueryWrapper.eq("group_id", sentenceGroup.getId());
        sentenceMapper.delete(sentenceQueryWrapper);
        //删除分组
        return sentenceGroupMapper.delete(sentenceGroupQueryWrapper);
    }

    /**
     * 描述:修改分组名称
     *
     */
    @Override
    public Object updateGroup(Integer userId, String name, String newName) {
        //获取用户指定分组
        QueryWrapper<SentenceGroup> sentenceGroupQueryWrapper = new QueryWrapper<>();
        sentenceGroupQueryWrapper.eq("user_id", userId);
        sentenceGroupQueryWrapper.eq("name", name);
        SentenceGroup sentenceGroup = sentenceGroupMapper.selectOne(sentenceGroupQueryWrapper);
        if (sentenceGroup == null) return "分组不存在";
        //修改分组名称
        sentenceGroup.setName(newName);
        return sentenceGroupMapper.updateById(sentenceGroup);
    }

    /**
     * 描述:添加句子
     *
     */
    @Override
    public Object addSentence(Integer userId, String name, String content) {
        //获取用户指定分组
        QueryWrapper<SentenceGroup> sentenceGroupQueryWrapper = new QueryWrapper<>();
        sentenceGroupQueryWrapper.eq("user_id", userId);
        sentenceGroupQueryWrapper.eq("name", name);
        SentenceGroup sentenceGroup = sentenceGroupMapper.selectOne(sentenceGroupQueryWrapper);
        if (sentenceGroup == null) return "分组不存在";
        Integer groupId = sentenceGroup.getId();
        //添加句子
        Sentence sentence = new Sentence();
        sentence.setUserId(userId);
        sentence.setGroupId(groupId);
        sentence.setContent(content);
        return sentenceMapper.insert(sentence);
    }

    /**
     * 描述:删除句子
     *
     */
    @Override
    public Object removeSentence(Integer sentenceId) {
        return sentenceMapper.deleteById(sentenceId);
    }

    /**
     * 描述:修改句子内容
     *
     */
    @Override
    public Object updateSentence(Integer sentenceId, String content) {
        QueryWrapper<Sentence> sentenceQueryWrapper = new QueryWrapper<>();
        sentenceQueryWrapper.eq("id", sentenceId);
        Sentence sentence = sentenceMapper.selectOne(sentenceQueryWrapper);
        if (sentence == null) return "句子不存在";
        sentence.setContent(content);
        return sentenceMapper.updateById(sentence);
    }
}
