package com.egg.ih.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.db.mapper.IhHiOperMapper;
import com.egg.ih.db.model.IhHiOper;
import com.egg.ih.log.service.LogService;
import com.egg.ih.log.vo.HiOperVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author adanl
 */
@Service
public class LogServiceImpl extends ServiceImpl<IhHiOperMapper, IhHiOper> implements LogService {
    @Override
    public void saveLog(HiOperVO hiOperVO) {
        IhHiOper ihHiOper = new IhHiOper();
        BeanUtils.copyProperties(hiOperVO, ihHiOper);
        ihHiOper.setCreateTime(new Date());
        ihHiOper.setUpdateTime(new Date());
        this.save(ihHiOper);
    }
}
