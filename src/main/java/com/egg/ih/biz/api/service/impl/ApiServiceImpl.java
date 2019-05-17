package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.ih.biz.api.service.ApiService;
import com.egg.ih.biz.api.vo.*;
import com.egg.ih.constant.BaseConstant;
import com.egg.ih.db.mapper.IhClassMapper;
import com.egg.ih.db.mapper.IhInterfaceMapper;
import com.egg.ih.db.mapper.IhParamsMapper;
import com.egg.ih.db.model.IhClass;
import com.egg.ih.db.model.IhInterface;
import com.egg.ih.db.model.IhParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Administrator
 */
@Service
public class ApiServiceImpl implements ApiService {
    @Autowired
    private IhClassMapper ihClassMapper;

    @Autowired
    private IhInterfaceMapper ihInterfaceMapper;

    @Autowired
    private IhParamsMapper ihParamsMapper;

    private Function<IhClassVO, IhClass> classVO2Class = vo -> {
        IhClass ihClass = new IhClass();
        BeanUtils.copyProperties(vo, ihClass);
        ihClass.setCreateTime(new Date());
        ihClass.setUpdateTime(new Date());
        ihClass.setValid(BaseConstant.有效性.有效.getCode());
        return ihClass;
    };

    private Function<IhInterfaceVO, IhInterface> interfaceVO2Interface = vo -> {
        IhInterface ihInterface = new IhInterface();
        BeanUtils.copyProperties(vo, ihInterface);
        ihInterface.setCreateTime(new Date());
        ihInterface.setUpdateTime(new Date());
        ihInterface.setValid(BaseConstant.有效性.有效.getCode());

        return ihInterface;
    };

    private Consumer<IhParams> inQuery = p -> p.setFlag(BaseConstant.例子位置.query.getCode());
    private Consumer<IhParams> inExample = p -> p.setFlag(BaseConstant.例子位置.example.getCode());

    @Override
    public int saveClass(IhClassVO ihClassVO) {
        IhClass ihClass = classVO2Class.apply(ihClassVO);
        return ihClassMapper.insert(ihClass);
    }

    @Override
    public int saveInterface(IhInterfaceVO interfaceVO, List<IhParamsVO> params, List<IhHeaderVO> headers, IhBodyVO bodyVO, IhResponseVO responseVO) {

        IhInterface ihInterface = interfaceVO2Interface.apply(interfaceVO);
        // 保存接口
        int returnId = ihInterfaceMapper.insert(ihInterface);
        // 保存接口参数
        if(params != null) {
            params.stream().map(vo -> {
                IhParams p = new IhParams();
                BeanUtils.copyProperties(vo, p);
                p.setCreateTime(new Date());
                p.setUpdateTime(new Date());
                inQuery.accept(p);
                p.setPosition(BaseConstant.参数存储位置.PARAMS.name());
                p.setInterfaceId(ihInterface.getInterfaceId());
                return p;
            }).forEach(p -> ihParamsMapper.insert(p));
        }
        if(headers != null) {
            headers.stream().map(vo -> {
                IhParams p = new IhParams();
                BeanUtils.copyProperties(vo, p);
                p.setCreateTime(new Date());
                p.setUpdateTime(new Date());
                p.setPosition(BaseConstant.参数存储位置.HEADER.name());
                inQuery.accept(p);
                p.setInterfaceId(ihInterface.getInterfaceId());
                return p;
            }).forEach(p -> ihParamsMapper.insert(p));
        }
        if(bodyVO != null) {
            bodyVO.getList().stream().map(vo -> {
                IhParams p = new IhParams();
                BeanUtils.copyProperties(vo, p);
                p.setCreateTime(new Date());
                p.setUpdateTime(new Date());
                p.setPosition(BaseConstant.参数存储位置.BODY.name());
                p.setInterfaceId(ihInterface.getInterfaceId());
                inQuery.accept(p);
                return p;
            }).forEach(p -> {
                ihParamsMapper.insert(p);
            });
            saveExample(bodyVO.getExample().getBytes(), BaseConstant.参数存储位置.BODY.name(), ihInterface.getInterfaceId());
        }
        if(responseVO != null) {
            responseVO.getList().stream().map(vo -> {
                IhParams p = new IhParams();
                BeanUtils.copyProperties(vo, p);
                inQuery.accept(p);
                p.setCreateTime(new Date());
                p.setUpdateTime(new Date());
                p.setPosition(BaseConstant.参数存储位置.RESPONSE.name());
                p.setInterfaceId(ihInterface.getInterfaceId());
                return p;
            }).forEach(p -> {
                ihParamsMapper.insert(p);
            });
            saveExample(responseVO.getExample().getBytes(), BaseConstant.参数存储位置.RESPONSE.name(), ihInterface.getInterfaceId());
        }

        return returnId;
    }

    @Override
    public List<IhClassVO> findClasses() {

        List<IhClass> li = ihClassMapper.selectList(new QueryWrapper<>());
        if(li!=null && li.size()>0) {
            List<IhClassVO> list = new ArrayList<>(li.size());
            li.stream().forEach(clazz -> {
                IhClassVO vo = new IhClassVO();
                BeanUtils.copyProperties(clazz, vo);
                list.add(vo);
            });
            // 将类型根据字典顺序排列
            Collections.sort(list, Comparator.comparing(IhClassVO::getCode));
            return list;
        }

        return null;
    }

    private void saveExample(byte[] example, String position, String interfaceId) {
        IhParams exampleParams = new IhParams();
        inExample.accept(exampleParams);
        exampleParams.setPosition(position);
        exampleParams.setCreateTime(new Date());
        exampleParams.setUpdateTime(new Date());
        exampleParams.setInterfaceId(interfaceId);
        exampleParams.setExample(example);
        ihParamsMapper.insert(exampleParams);
    }



}
