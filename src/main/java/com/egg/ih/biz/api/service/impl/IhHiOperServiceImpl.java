package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.biz.api.service.IhHiOperService;
import com.egg.ih.db.mapper.IhHiOperMapper;
import com.egg.ih.db.model.IhHiOper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adanl
 */
@Service
public class IhHiOperServiceImpl extends ServiceImpl<IhHiOperMapper, IhHiOper> implements IhHiOperService {

    @Override
    public List<String> findHistoryByDate() {
        return this.getBaseMapper().findHistoryByDate();
    }
}
