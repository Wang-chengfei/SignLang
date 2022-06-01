package com.example.controller;


import com.example.entity.Article;
import com.example.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 描述:查询所有文章
     *
     */
    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
    public List<Article> queryAll(@RequestParam(value = "type", required = false) String type,
                                  @RequestParam(value = "userId", required = false) Integer userId,
                                  @RequestParam(value = "isStar", required = false) Integer isStar) {
        return articleService.queryAll(type, userId, isStar);
    }

    /**
     * 描述:根据id获取文章
     *
     */
    @RequestMapping(value = "/queryOne", method = RequestMethod.GET)
    public Article queryOne(@RequestParam("articleId") String articleId) {
        return articleService.queryOne(articleId);
    }

    /**
     * 描述:添加收藏文章
     *
     */
    @RequestMapping(value = "/addStarArticle", method = RequestMethod.POST)
    public Object addStarArticle(@RequestParam("userId") Integer userId,
                                 @RequestParam("articleId") Integer articleId) {
        return articleService.addStarArticle(userId, articleId);
    }

    /**
     * 描述:取消收藏文章
     *
     */
    @RequestMapping(value = "/cancelStarArticle", method = RequestMethod.POST)
    public Object cancelStarArticle(@RequestParam("userId") Integer userId,
                                    @RequestParam("articleId") Integer articleId) {
        return articleService.cancelStarArticle(userId, articleId);
    }


    /**
     * 描述:查询文章是否被收藏
     *
     */
    @RequestMapping(value = "/queryStar", method = RequestMethod.GET)
    public Boolean queryStar(@RequestParam("userId") Integer userId,
                             @RequestParam("articleId") Integer articleId) {
        return articleService.queryStar(userId, articleId);
    }

    /**
     * 描述:获取所有文章类型
     *
     */
    @RequestMapping(value = "/getAllType", method = RequestMethod.GET)
    public Object getAllType() {
        return articleService.getAllType();
    }
}

