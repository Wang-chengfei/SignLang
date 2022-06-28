package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.mapper.*;
import com.example.service.PlanService;
import com.example.service.PlanWordService;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wcf
 * @since 2022-04-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PlanService planService;
    @Autowired
    private SentenceMapper sentenceMapper;
    @Autowired
    private SentenceGroupMapper sentenceGroupMapper;

    @Override
    public User query(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public User login(String openid, String sessionKey) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);
        User user = userMapper.selectOne(userQueryWrapper);
        //如果用户未注册，则注册，同时生成默认计划
        if (user == null) {
            //用户注册
            user = new User();
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            userMapper.insert(user);
            //添加计划
            Plan plan = new Plan();
            plan.setUserId(user.getId());
            plan.setDictionaryId(3);
            plan.setAmount(30);
            plan.setPOrder(1);
            planService.add(plan);
            //生成常用句子
            //初始化内容
            Map<String, String[]> initialSentences = new LinkedHashMap<>();
            initialSentences.put("常用", new String[]{"你好", "谢谢", "好的", "请稍等", "别客气", "没关系", "劳驾了", "请多指教", "打扰一下", "再见", "询问", "我说不了话", "我听不见", "怎么了", "还有其它事吗"});
            initialSentences.put("买东西", new String[]{"这个多少钱", "一共多少钱", "请问xxx有卖吗", "微信或支付宝", "你扫我还是我扫你", "怎么结账", "我要结账", "能便宜点吗", "有其它牌子吗", "不买了，谢谢", });
            initialSentences.put("问路", new String[]{"请问xxx怎么走", "洗手间在哪边", "xxx在哪里", "xxx在附近吗", "附近有公交站吗", "附近有地铁吗", "高铁站入口在哪边"});
            initialSentences.put("我的", new String[]{"我是xxx", "我在等人"});
            initialSentences.put("点餐吃饭", new String[]{"请问怎么点餐", "麻烦结账", "有优惠吗","哪些比较好吃", "你们招牌菜是什么", "请问有xxx吗", "麻烦来x碗米饭"});
            //插入数据库
            for (Map.Entry<String, String[]> entry : initialSentences.entrySet()) {
                //插入sentenceGroup
                String  sentenceGroupName = entry.getKey();
                SentenceGroup sentenceGroup = new SentenceGroup();
                sentenceGroup.setUserId(user.getId());
                sentenceGroup.setName(sentenceGroupName);
                sentenceGroupMapper.insert(sentenceGroup);
                //插入sentence
                String[] sentences = entry.getValue();
                for (String sentenceContent : sentences) {
                    Sentence sentence = new Sentence();
                    sentence.setGroupId(sentenceGroup.getId());
                    sentence.setUserId(user.getId());
                    sentence.setContent(sentenceContent);
                    sentenceMapper.insert(sentence);
                }
            }

        }
        //用户已注册，更新sessionKey
        else {
            user.setSessionKey(sessionKey);
            userMapper.updateById(user);
        }
        return user;
    }
}
