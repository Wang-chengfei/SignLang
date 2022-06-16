package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.ListeningTest;
import com.example.mapper.ListeningTestMapper;
import com.example.service.ListeningTestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-06-13
 */
@Service
public class ListeningTestServiceImpl extends ServiceImpl<ListeningTestMapper, ListeningTest> implements ListeningTestService {

    @Autowired
    private ListeningTestMapper listeningTestMapper;

    @Override
    public Object getAll() {
        List<ListeningTest> listeningTests = listeningTestMapper.selectList(null);
        Map<String, List<ListeningTest>> map = new LinkedHashMap<>();
        for (ListeningTest listeningTest : listeningTests) {
            String type = listeningTest.getType();
            if (!map.containsKey(type)) {
                QueryWrapper<ListeningTest> listeningTestQueryWrapper = new QueryWrapper<>();
                listeningTestQueryWrapper.eq("type", type);
                List<ListeningTest> type_list = listeningTestMapper.selectList(listeningTestQueryWrapper);
                map.put(type, type_list);
            }
        }
        return map;
    }
}
