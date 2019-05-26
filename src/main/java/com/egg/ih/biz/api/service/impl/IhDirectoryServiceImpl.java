package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.biz.api.service.IhDirectoryService;
import com.egg.ih.db.mapper.IhDirectoryMapper;
import com.egg.ih.db.model.IhDirectory;
import org.springframework.stereotype.Service;

/**
 * @author adanl
 */
@Service
public class IhDirectoryServiceImpl extends ServiceImpl<IhDirectoryMapper, IhDirectory> implements IhDirectoryService {
}
