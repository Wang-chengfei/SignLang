package com.example.controller;


import com.example.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wcf
 * @since 2022-06-19
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 描述:添加记录
     *
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Object add(@RequestParam("userId") Integer userId,
                   @RequestParam("sentence") String sentence) {
        return recordService.add(userId, sentence);
    }

    /**
     * 描述:删除某条记录
     *
     */
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public Object removeOne(@RequestParam("recordId") Integer recordId) {
        return recordService.removeOne(recordId);
    }

    /**
     * 描述:获取记录
     *
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Object get(@RequestParam("userId") Integer userId,
                      @RequestParam(value = "num", required = false) Integer num) {
        return recordService.get(userId, num);
    }

}

