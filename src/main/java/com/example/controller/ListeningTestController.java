package com.example.controller;


import com.example.service.ListeningTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-06-13
 */
@RestController
public class ListeningTestController {

    /**
     * 描述:查询文章是否被收藏
     *
     */

    @Autowired
    private ListeningTestService listeningTestService;

    @RequestMapping(value = "/listen/getListeningTest", method = RequestMethod.GET)
    public Object queryStar() {
        return listeningTestService.getAll();
    }

}

