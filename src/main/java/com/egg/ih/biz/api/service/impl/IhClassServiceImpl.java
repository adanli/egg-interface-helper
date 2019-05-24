package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.biz.api.service.IhClassService;
import com.egg.ih.db.mapper.IhClassMapper;
import com.egg.ih.db.model.IhClass;
import org.springframework.stereotype.Service;

/**
 * @author adanl
 */
@Service
public class IhClassServiceImpl extends ServiceImpl<IhClassMapper, IhClass> implements IhClassService {
}
