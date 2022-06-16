package com.example.service;

import com.example.entity.ListeningTest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wcf
 * @since 2022-06-13
 */
public interface ListeningTestService extends IService<ListeningTest> {

    Object getAll();
}
