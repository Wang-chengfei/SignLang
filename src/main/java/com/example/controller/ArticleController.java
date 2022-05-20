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
    public List<Article> queryAll(@RequestParam(value = "type", required = false) String type) {
        return articleService.queryAll(type);
    }

    /**
     * 描述:根据id获取文章
     *
     */
    @RequestMapping(value = "/queryOne", method = RequestMethod.GET)
    public Article queryOne(@RequestParam("articleId") String articleId) {
        return articleService.queryOne(articleId);
    }

}

