package com.egg.ih.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.egg.ih.db.mapper.IhHiOperMapper;
import com.egg.ih.db.model.IhHiOper;
import com.egg.ih.log.constant.LogConstant;
import com.egg.ih.log.dto.HiOperDTO;
import com.egg.ih.log.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;


/**
 * @author Administrator
 */
@Service
public class LogServiceImpl extends ServiceImpl<IhHiOperMapper, IhHiOper> implements LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

    private Function<HiOperDTO, IhHiOper> trans = dto -> {
        IhHiOper oper = new IhHiOper();
        oper.setCreateTime(new Date());
        oper.setUpdateTime(new Date());
        oper.setInterfaceId(dto.getOperateArgs());
        oper.setOperIp(dto.getOperateIp());
        return oper;
    };

    @Override
    public void insertLog(HiOperDTO log) {
        this.check(log);
    }

    private void check(HiOperDTO log){
        //检验日志对象属性非空，设置对应的异常抛出

        if(log.getLogState().equals(LogConstant.LOG_STATE_SUCCESS)){
            logger.info(log.toString());
        }else{
            logger.error(log.toString());
        }

        IhHiOper oper = trans.apply(log);
        this.save(oper);


    }
}
