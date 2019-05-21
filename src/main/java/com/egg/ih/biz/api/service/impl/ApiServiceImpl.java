package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.ih.biz.api.service.ApiService;
import com.egg.ih.biz.api.vo.ClassVO;
import com.egg.ih.biz.api.vo.InterfaceVO;
import com.egg.ih.biz.api.vo.params.*;
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
import java.util.function.Supplier;

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

    private Function<ClassVO, IhClass> classVO2Class = vo -> {
        IhClass ihClass = new IhClass();
        BeanUtils.copyProperties(vo, ihClass);
        ihClass.setCreateTime(new Date());
        ihClass.setUpdateTime(new Date());
        ihClass.setValid(BaseConstant.有效性.有效.getCode());
        return ihClass;
    };

    private Function<InterfaceVO, IhInterface> interfaceVO2Interface = vo -> {
        IhInterface ihInterface = new IhInterface();
        BeanUtils.copyProperties(vo, ihInterface);
        ihInterface.setCreateTime(new Date());
        ihInterface.setUpdateTime(new Date());
        ihInterface.setValid(BaseConstant.有效性.有效.getCode());

        return ihInterface;
    };

    private Function<IhParams, ParamVO> param2VO = param -> {
        ParamVO paramVO = new ParamVO();
        BeanUtils.copyProperties(param, paramVO);
        return paramVO;
    };

    private Consumer<IhParams> inQuery = p -> p.setFlag(BaseConstant.例子位置.query.getCode());
    private Consumer<IhParams> inExample = p -> p.setFlag(BaseConstant.例子位置.example.getCode());

    private Supplier<QueryVO> querySupplier = () -> {
        QueryVO queryVO = new QueryVO();
        if(queryVO.getParams() == null) {
            queryVO.setParams(new ArrayList<>());
        }
        return queryVO;
    };

    private Supplier<HeaderVO> headerSupplier = () -> {
        HeaderVO headerVO = new HeaderVO();
        if(headerVO.getParams() == null) {
            headerVO.setParams(new ArrayList<>());
        }
        return headerVO;
    };

    private Supplier<BodyVO> bodySupplier = () -> {
        BodyVO bodyVO = new BodyVO();
        if(bodyVO.getParams() == null) {
            bodyVO.setParams(new ArrayList<>());
        }
        return bodyVO;
    };

    private Supplier<ResponseVO> responseSupplier = () -> {
        ResponseVO responseVO = new ResponseVO();
        if(responseVO.getParams() == null) {
            responseVO.setParams(new ArrayList<>());
        }
        return responseVO;
    };


    @Override
    public int saveClass(ClassVO ihClassVO) {
        IhClass ihClass = classVO2Class.apply(ihClassVO);
        return ihClassMapper.insert(ihClass);
    }

    @Override
    public int saveInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, BodyVO bodyVO, ResponseVO responseVO) {

        IhInterface ihInterface = interfaceVO2Interface.apply(interfaceVO);
        // 保存接口
        int returnId = ihInterfaceMapper.insert(ihInterface);
        // 保存接口参数
        if(queryVO!=null && queryVO.getParams()!=null) {
            this.saveParams(queryVO, ihInterface.getInterfaceId(), BaseConstant.参数存储位置.QUERY.name());
        }
        if(headerVO!=null && headerVO.getParams()!=null) {
            this.saveParams(headerVO, ihInterface.getInterfaceId(), BaseConstant.参数存储位置.HEADER.name());
        }
        if(bodyVO!=null && bodyVO.getParams()!=null) {
            this.saveParams(bodyVO, ihInterface.getInterfaceId(), BaseConstant.参数存储位置.BODY.name());
            saveExample(bodyVO.getExample().getBytes(), BaseConstant.参数存储位置.BODY.name(), ihInterface.getInterfaceId());
        }
        if(responseVO!=null && responseVO.getParams()!=null) {
            this.saveParams(responseVO, ihInterface.getInterfaceId(), BaseConstant.参数存储位置.RESPONSE.name());
            saveExample(responseVO.getExample().getBytes(), BaseConstant.参数存储位置.RESPONSE.name(), ihInterface.getInterfaceId());
        }

        return returnId;
    }

    private void saveParams(AbstractParamVO param, String interfaceId, String position) {
        param.getParams().stream().map(vo -> {
            IhParams p = new IhParams();
            BeanUtils.copyProperties(vo, p);
            inQuery.accept(p);
            p.setCreateTime(new Date());
            p.setUpdateTime(new Date());
            p.setPosition(position);
            p.setInterfaceId(interfaceId);
            return p;
        }).forEach(p -> ihParamsMapper.insert(p));
    }

    @Override
    public List<ClassVO> findClasses() {
        QueryWrapper<IhClass> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        List<IhClass> li = ihClassMapper.selectList(wrapper);
        if(li!=null && li.size()>0) {
            List<ClassVO> list = new ArrayList<>(li.size());
            li.stream().forEach(clazz -> {
                ClassVO vo = new ClassVO();
                BeanUtils.copyProperties(clazz, vo);
                list.add(vo);
            });
            // 将类型根据字典顺序排列
            Collections.sort(list, Comparator.comparing(ClassVO::getCode));
            return list;
        }

        return null;
    }

    @Override
    public List<InterfaceVO> findInterfacesByClassId(String classId) {
        QueryWrapper<IhInterface> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhInterface::getClassId, classId);
        wrapper.lambda().eq(IhInterface::getValid, BaseConstant.有效性.有效.getCode());
        List<IhInterface> li = ihInterfaceMapper.selectList(wrapper);
        List<InterfaceVO> list = new ArrayList<>(li.size());
        li.stream().forEach(inter -> {
            InterfaceVO vo = new InterfaceVO();
            BeanUtils.copyProperties(inter, vo);
            list.add(vo);
        });
        return list;
    }

    @Override
    public InterfaceVO findInterfaceById(String interfaceId) {
        // 查询接口类
        IhInterface ihInterface = this.findOrgInterfaceById(interfaceId);
        if(ihInterface == null) {
            return null;
        }
        InterfaceVO vo = new InterfaceVO();
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
    public void setParamsByInterfaceId(InterfaceVO interfaceVO) {
        QueryWrapper<IhParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhParams::getInterfaceId, interfaceVO.getInterfaceId());
        List<IhParams> list = ihParamsMapper.selectList(wrapper);
        if(list!=null && list.size()>0) {
            QueryVO queryVO = querySupplier.get();
            HeaderVO headerVO = headerSupplier.get();
            BodyVO bodyVO = bodySupplier.get();
            ResponseVO responseVO = responseSupplier.get();

            list.stream().forEach(param -> {
                switch (param.getPosition()) {
                    case "QUERY": {
                        queryVO.getParams().add(param2VO.apply(param));
                        break;
                    }
                    case "HEADER": {
                        headerVO.getParams().add(param2VO.apply(param));
                        break;
                    }
                    case "BODY": {
                        if(param.getFlag().equals(BaseConstant.例子位置.query.getCode())) {
                            bodyVO.getParams().add(param2VO.apply(param));
                        }else {
                            bodyVO.setExample(new String(param.getExample(), Charset.defaultCharset()));
                        }
                        break;
                    }
                    case "RESPONSE": {
                        if(param.getFlag().equals(BaseConstant.例子位置.example.getCode())) {
                            responseVO.getParams().add(param2VO.apply(param));
                        }else {
                            responseVO.setExample(new String(param.getExample(), Charset.defaultCharset()));
                        }
                        break;
                    }
                    default:
                        break;
                }
            });

            interfaceVO.setQueryVO(queryVO);
            interfaceVO.setHeaderVO(headerVO);
            interfaceVO.setBodyVO(bodyVO);
            interfaceVO.setResponseVO(responseVO);

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
        wrapper.lambda().eq(IhInterface::getInterfaceId, id);
        wrapper.lambda().eq(IhInterface::getValid, BaseConstant.有效性.有效.getCode());
        return ihInterfaceMapper.selectOne(wrapper);
    }

    /**
     * 先物理删除原接口和参数，新增新接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param queryVO 位于params的参数
     * @param headerVO 位于headers的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    @Override
    public int updateInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, BodyVO bodyVO, ResponseVO responseVO) {
        // 删除参数
        QueryWrapper<IhParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhParams::getInterfaceId, interfaceVO.getInterfaceId());
        ihParamsMapper.selectList(wrapper).stream().forEach(p -> ihParamsMapper.deleteById(p.getParamId()));

        return this.saveInterface(interfaceVO, queryVO, headerVO, bodyVO, responseVO);
    }

}
