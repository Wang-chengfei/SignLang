package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.Article;
import com.example.entity.StarArticle;
import com.example.mapper.ArticleMapper;
import com.example.mapper.StarArticleMapper;
import com.example.service.ArticleService;
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
 * @since 2022-05-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private StarArticleMapper starArticleMapper;

    @Override
    public List<Article> queryAll(String type, Integer userId, Integer isStar) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (type != null) {
            articleQueryWrapper.eq("type", type);
        }
        if (userId != null && isStar != null) {
            QueryWrapper<StarArticle> starArticleQueryWrapper = new QueryWrapper<>();
            starArticleQueryWrapper.eq("user_id", userId);
            List<StarArticle> starArticles = starArticleMapper.selectList(starArticleQueryWrapper);
            List<Integer> articleIds = new ArrayList<>();
            for (StarArticle starArticle : starArticles) {
                articleIds.add(starArticle.getArticleId());
            }
            if (isStar == 1) articleQueryWrapper.in("id", articleIds);
            else articleQueryWrapper.notIn("id", articleIds);
        }
        List<Article> articles = articleMapper.selectList(articleQueryWrapper);
        return articles;
    }

    @Override
    public Article queryOne(String articleId) {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("id", articleId);
        return articleMapper.selectOne(articleQueryWrapper);
    }

    @Override
    public Object addStarArticle(Integer userId, Integer articleId) {
        if (!queryStar(userId, articleId)) {
            StarArticle starArticle = new StarArticle();
            starArticle.setUserId(userId);
            starArticle.setArticleId(articleId);
            return starArticleMapper.insert(starArticle);
        }
        return 0;
    }

    @Override
    public Boolean queryStar(Integer userId, Integer articleId) {
        QueryWrapper<StarArticle> starArticleQueryWrapper = new QueryWrapper<>();
        starArticleQueryWrapper.eq("user_id", userId);
        starArticleQueryWrapper.eq("article_id", articleId);
        StarArticle starArticle = starArticleMapper.selectOne(starArticleQueryWrapper);
        return !(starArticle == null);
    }

    @Override
    public Object cancelStarArticle(Integer userId, Integer articleId) {
        QueryWrapper<StarArticle> starArticleQueryWrapper = new QueryWrapper<>();
        starArticleQueryWrapper.eq("user_id", userId);
        starArticleQueryWrapper.eq("article_id", articleId);
        return starArticleMapper.delete(starArticleQueryWrapper);
    }
}
