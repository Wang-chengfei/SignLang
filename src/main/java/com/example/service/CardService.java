package com.example.service;

import com.example.entity.Card;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-04-11
 */
public interface CardService extends IService<Card> {

    int clock(Integer userId);

    List<Card> queryAll(Integer userId);

    Card queryToday(Integer userId);

    Integer queryCardDays(Integer userId);

    Integer queryLearnedWord(Integer userId);

    Integer queryLearnedDay(Integer userId);

    int cancelClock(Integer userId);

    Object queryLearnedByWeek(Integer userId);

    Object queryLearnedByMonth(Integer userId);
}
