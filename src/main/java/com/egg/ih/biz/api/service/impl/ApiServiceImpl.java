package com.egg.ih.biz.api.service.impl;

import com.egg.ih.biz.api.service.ApiService;
import com.egg.ih.biz.api.vo.IhClassVO;
import com.egg.ih.db.mapper.IhClassMapper;
import com.egg.ih.db.model.IhClass;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 * @author Administrator
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private IhClassMapper ihClassMapper;

    private Function<IhClassVO, IhClass> classVO2Class = vo -> {
        IhClass ihClass = new IhClass();
        BeanUtils.copyProperties(vo, ihClass);
        ihClass.setCreateTime(new Date());
        ihClass.setUpdateTime(new Date());
        return ihClass;
    };

    @Override
    public void createClass(IhClassVO ihClassVO) {
        IhClass ihClass = classVO2Class.apply(ihClassVO);
        ihClassMapper.insert(ihClass);
    }
}
