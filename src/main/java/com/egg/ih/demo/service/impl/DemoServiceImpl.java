package com.egg.ih.demo.service.impl;

import com.egg.ih.db.mapper.DemoMapper;
import com.egg.ih.db.model.Demo;
import com.egg.ih.demo.service.DemoService;
import com.egg.ih.demo.vo.DemoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoMapper demoMapper;

    @Override
    public DemoVO findById(Integer id) {
        Demo demo = demoMapper.selectById(id);
        DemoVO vo = new DemoVO();
        BeanUtils.copyProperties(demo, vo);

        return vo;
    }
}
