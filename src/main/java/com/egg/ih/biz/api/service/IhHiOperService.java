package com.egg.ih.biz.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egg.ih.db.model.IhHiOper;

import java.util.List;

/**
 * @author adanl
 */
public interface IhHiOperService extends IService<IhHiOper> {
    List<String> findHistoryByDate();
}
