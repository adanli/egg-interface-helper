package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.biz.api.service.IhInterfaceService;
import com.egg.ih.db.mapper.IhInterfaceMapper;
import com.egg.ih.db.model.IhInterface;
import org.springframework.stereotype.Service;

/**
 * @author adanl
 */
@Service
public class IhInterfaceServiceImpl extends ServiceImpl<IhInterfaceMapper, IhInterface> implements IhInterfaceService {
}
