package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.biz.api.service.IhParamsService;
import com.egg.ih.db.mapper.IhParamsMapper;
import com.egg.ih.db.model.IhParams;
import org.springframework.stereotype.Service;

/**
 * @author adanl
 */
@Service
public class IhParamsServiceImpl extends ServiceImpl<IhParamsMapper, IhParams> implements IhParamsService {
}
