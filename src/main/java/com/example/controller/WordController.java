package com.example.controller;


import com.example.entity.Word;
import com.example.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    //获取词库中所有单词信息
    @GetMapping("/queryAllWord")
    public List<Word> queryAllWord() {
        return wordService.queryAllWord();
    }

}

