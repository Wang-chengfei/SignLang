package com.example.service;

import com.example.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-05-19
 */
public interface ArticleService extends IService<Article> {

    List<Article> queryAll(String type, Integer userId, Integer isStar);

    Article queryOne(String articleId);

    Object addStarArticle(Integer userId, Integer articleId);

    Boolean queryStar(Integer userId, Integer articleId);

    Object cancelStarArticle(Integer userId, Integer articleId);

    Object getAllType();
}
