package com.egg.ih.biz.api.service.impl;

import com.alibaba.druid.sql.visitor.functions.Char;
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

import java.nio.charset.Charset;
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
        QueryWrapper<IhClass> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        List<IhClass> li = ihClassMapper.selectList(wrapper);
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

    @Override
    public List<IhInterfaceVO> findInterfacesByClassId(String classId) {
        QueryWrapper<IhInterface> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhInterface::getClassId, classId);
        wrapper.lambda().eq(IhInterface::getValid, BaseConstant.有效性.有效.getCode());
        List<IhInterface> li = ihInterfaceMapper.selectList(wrapper);
        List<IhInterfaceVO> list = new ArrayList<>(li.size());
        li.stream().forEach(inter -> {
            IhInterfaceVO vo = new IhInterfaceVO();
            BeanUtils.copyProperties(inter, vo);
            list.add(vo);
        });
        return list;
    }

    @Override
    public IhInterfaceVO findInterfaceById(String interfaceId) {
        // 查询接口类
        IhInterface ihInterface = this.findOrgInterfaceById(interfaceId);
        if(ihInterface == null) {
            return null;
        }
        IhInterfaceVO vo = new IhInterfaceVO();
        BeanUtils.copyProperties(ihInterface, vo);

        this.setParamsByInterfaceId(vo);

        return vo;
    }

    /**
     * 根据接口主键逻辑删除接口
     * @param interfaceId
     */
    @Override
    public void deleteInterfaceById(String interfaceId) {
        IhInterface ihInterface = this.findOrgInterfaceById(interfaceId);

        if(ihInterface != null) {
            ihInterface.setValid(BaseConstant.有效性.无效.getCode());
            ihInterfaceMapper.updateById(ihInterface);
        }

    }

    @Override
    public void deleteClassById(String classId) {
        IhClass ihClass = this.findOrgClassById(classId);

        if(ihClass != null) {
            ihClass.setValid(BaseConstant.有效性.无效.getCode());
            ihClassMapper.updateById(ihClass);
        }
    }

    @Override
    public int updateClass(String classId, String className) {

        IhClass ihClass = this.findOrgClassById(classId);
        if(ihClass != null) {
            ihClass.setName(className);
            return ihClassMapper.updateById(ihClass);
        }

        return 0;
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

    /**
     * 根据接口主键查询参数列表，并拼凑
     * @param interfaceVO
     * @return
     */
    public void setParamsByInterfaceId(IhInterfaceVO interfaceVO) {
        QueryWrapper<IhParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhParams::getInterfaceId, interfaceVO.getInterfaceId());
        List<IhParams> list = ihParamsMapper.selectList(wrapper);
        if(list!=null && list.size()>0) {
            List<IhParamsVO> params = new ArrayList<>();
            List<IhHeaderVO> headers = new ArrayList<>();
            IhBodyVO body = new IhBodyVO();
            IhResponseVO response = new IhResponseVO();

            list.stream().forEach(param -> {
                if(BaseConstant.参数存储位置.PARAMS.name().equals(param.getPosition())) {
                    IhParamsVO vo = new IhParamsVO();
                    BeanUtils.copyProperties(param, vo);
                    params.add(vo);
                }else if(BaseConstant.参数存储位置.HEADER.name().equals(param.getPosition())) {
                    IhHeaderVO vo = new IhHeaderVO();
                    BeanUtils.copyProperties(param, vo);
                    headers.add(vo);
                }else if(BaseConstant.参数存储位置.BODY.name().equals(param.getPosition())) {
                    if(BaseConstant.例子位置.query.getCode().equals(param.getFlag())) {
                        if(body.getList()==null) {
                            List<IhParamsVO> li = new ArrayList<>();
                            body.setList(li);
                        }
                        IhParamsVO vo = new IhParamsVO();
                        BeanUtils.copyProperties(param, vo);
                        body.getList().add(vo);
                    }else if(BaseConstant.例子位置.example.getCode().equals(param.getFlag())){
                        body.setExample(new String(param.getExample(), Charset.defaultCharset()));
                    }

                }else if(BaseConstant.参数存储位置.RESPONSE.name().equals(param.getPosition())) {
                    if(BaseConstant.例子位置.query.getCode().equals(param.getFlag())) {
                        if(response.getList()==null) {
                            List<IhParamsVO> li = new ArrayList<>();
                            response.setList(li);
                        }
                        IhParamsVO vo = new IhParamsVO();
                        BeanUtils.copyProperties(param, vo);
                        response.getList().add(vo);
                    }else if(BaseConstant.例子位置.example.getCode().equals(param.getFlag())){
                        response.setExample(new String(param.getExample(), Charset.defaultCharset()));
                    }

                }
            });

            interfaceVO.setParams(params);
            interfaceVO.setHeaders(headers);
            interfaceVO.setBody(body);
            interfaceVO.setResponse(response);

        }

    }

    private IhClass findOrgClassById(String id) {
        QueryWrapper<IhClass> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        wrapper.lambda().eq(IhClass::getClassId, id);
        return ihClassMapper.selectOne(wrapper);
    }

    private IhInterface findOrgInterfaceById(String id) {
        QueryWrapper<IhInterface> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhInterface::getClassId, id);
        wrapper.lambda().eq(IhInterface::getValid, BaseConstant.有效性.有效.getCode());
        return ihInterfaceMapper.selectOne(wrapper);
    }

    /**
     * 先物理删除原接口和参数，新增新接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param params 位于params的参数
     * @param headers 位于headers的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    @Override
    public int updateInterface(IhInterfaceVO interfaceVO, List<IhParamsVO> params, List<IhHeaderVO> headers, IhBodyVO bodyVO, IhResponseVO responseVO) {
        // 删除参数
        QueryWrapper<IhParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhParams::getInterfaceId, interfaceVO.getInterfaceId());
        ihParamsMapper.selectList(wrapper).stream().forEach(p -> ihParamsMapper.deleteById(p.getParamId()));

        return this.saveInterface(interfaceVO, params, headers, bodyVO, responseVO);
    }

}
