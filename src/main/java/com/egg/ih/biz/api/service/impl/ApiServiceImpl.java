package com.egg.ih.biz.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.egg.ih.biz.api.service.*;
import com.egg.ih.biz.api.vo.ClassVO;
import com.egg.ih.biz.api.vo.DirectoryVO;
import com.egg.ih.biz.api.vo.InterfaceVO;
import com.egg.ih.biz.api.vo.params.*;
import com.egg.ih.constant.BaseConstant;
import com.egg.ih.db.model.*;
import com.egg.ih.log.vo.HiOperVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
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
    private IhDirectoryService ihDirectoryService;
    @Autowired
    private IhClassService ihClassService;
    @Autowired
    private IhInterfaceService ihInterfaceService;
    @Autowired
    private IhParamsService ihParamsService;
    @Autowired
    private IhHiOperService ihHiOperService;

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
        paramVO.setNecessary(BaseConstant.是否必填.getNameByCode(param.getNecessary()));
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

    private Supplier<PathVO> pathSupplier = () -> {
        PathVO pathVO = new PathVO();
        if(pathVO.getParams() == null) {
            pathVO.setParams(new ArrayList<>());
        }
        return pathVO;
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
    public boolean saveClass(ClassVO ihClassVO) {
        IhClass ihClass = classVO2Class.apply(ihClassVO);
        return ihClassService.saveOrUpdate(ihClass);
    }

    /**
     * 如果存在classId和url一致的数据，不允许保存
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param queryVO 位于query的参数
     * @param headerVO 位于headers的参数
     * @param pathVO 位于path的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    @Override
    public boolean saveInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, PathVO pathVO, BodyVO bodyVO, ResponseVO responseVO) {

        QueryWrapper<IhInterface> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhInterface::getClassId, interfaceVO.getClassId()).eq(IhInterface::getUrl, interfaceVO.getUrl()).eq(IhInterface::getValid, BaseConstant.有效性.有效.getCode()).eq(IhInterface::getType, interfaceVO.getType());
        IhInterface ihInterface = ihInterfaceService.getOne(wrapper);
        if(ihInterface != null) {
            System.out.println("当前记录已存在");
            return false;
        }

        this.assembleShortCode(interfaceVO);
        ihInterface = interfaceVO2Interface.apply(interfaceVO);
        // 保存接口
        boolean flag = ihInterfaceService.saveOrUpdate(ihInterface);

        // 保存接口参数
        this.saveParamsVOs(ihInterface.getInterfaceId(), queryVO, headerVO, pathVO, bodyVO, responseVO);

        return flag;
    }


    private void assembleShortCode(InterfaceVO interfaceVO) {
        IhClass ihClass = ihClassService.getById(interfaceVO.getClassId());
        if(ihClass == null) {
            System.out.println("当前接口的类不存在");
        }

        String shortCode = interfaceVO.getUrl().substring(ihClass.getUrl().length());


        interfaceVO.setShortCode(shortCode);
    }

    private void saveParamsVOs(String interfaceId, QueryVO queryVO, HeaderVO headerVO, PathVO pathVO, BodyVO bodyVO, ResponseVO responseVO) {
        // 保存接口参数
        if(queryVO!=null && queryVO.getParams()!=null) {
            this.saveParams(queryVO, interfaceId, BaseConstant.参数存储位置.QUERY.getName());
        }
        if(headerVO!=null && headerVO.getParams()!=null) {
            this.saveParams(headerVO, interfaceId, BaseConstant.参数存储位置.HEADER.getName());
        }
        if(pathVO!=null && pathVO.getParams()!=null) {
            this.saveParams(pathVO, interfaceId, BaseConstant.参数存储位置.PATH.getName());
        }
        if(bodyVO!=null && bodyVO.getParams()!=null) {
            this.saveParams(bodyVO, interfaceId, BaseConstant.参数存储位置.BODY.getName());
            saveExample(bodyVO.getExample().getBytes(), BaseConstant.参数存储位置.BODY.getName(), interfaceId);
        }
        if(responseVO!=null && responseVO.getParams()!=null) {
            this.saveParams(responseVO, interfaceId, BaseConstant.参数存储位置.RESPONSE.getName());
            saveExample(responseVO.getExample().getBytes(), BaseConstant.参数存储位置.RESPONSE.name(), interfaceId);
        }
    }

    private void saveParams(AbstractParamVO param, String interfaceId, String position) {
        param.getParams().stream().map(vo -> {
            IhParams p = new IhParams();
            BeanUtils.copyProperties(vo, p);
            inQuery.accept(p);
            p.setCreateTime(new Date());
            p.setUpdateTime(new Date());
            p.setPosition(BaseConstant.参数存储位置.getCodeByName(position));
            p.setInterfaceId(interfaceId);
            return p;
        }).forEach(p -> {
            ihParamsService.saveOrUpdate(p);
        });

    }

    @Override
    public List<ClassVO> findClasses() {
        QueryWrapper<IhClass> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        List<IhClass> li = ihClassService.list(wrapper);
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
        List<IhInterface> li = ihInterfaceService.list(wrapper);
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
            ihInterfaceService.saveOrUpdate(ihInterface);
        }

    }

    @Override
    public void deleteClassById(String classId) {
        IhClass ihClass = this.findOrgClassById(classId);

        if(ihClass != null) {
            ihClass.setValid(BaseConstant.有效性.无效.getCode());
            ihClassService.saveOrUpdate(ihClass);
        }
    }

    @Override
    public boolean updateClass(String classId, ClassVO classVO) {

        IhClass ihClass = this.findOrgClassById(classId);
        if(ihClass != null) {
            ihClass = new IhClass();
            BeanUtils.copyProperties(classVO, ihClass);
            return ihClassService.saveOrUpdate(ihClass);
        }

        return Boolean.FALSE;
    }

    private void saveExample(byte[] example, String position, String interfaceId) {
        IhParams exampleParams = new IhParams();
        inExample.accept(exampleParams);
        exampleParams.setPosition(BaseConstant.参数存储位置.getCodeByName(position));
        exampleParams.setCreateTime(new Date());
        exampleParams.setUpdateTime(new Date());
        exampleParams.setInterfaceId(interfaceId);
        exampleParams.setExample(example);
        ihParamsService.saveOrUpdate(exampleParams);
    }

    /**
     * 根据接口主键查询参数列表，并拼凑
     * @param interfaceVO
     * @return
     */
    public void setParamsByInterfaceId(InterfaceVO interfaceVO) {
        QueryWrapper<IhParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhParams::getInterfaceId, interfaceVO.getInterfaceId());
        List<IhParams> list = ihParamsService.list(wrapper);
        if(list!=null && list.size()>0) {
            QueryVO queryVO = querySupplier.get();
            HeaderVO headerVO = headerSupplier.get();
            PathVO pathVO = pathSupplier.get();
            BodyVO bodyVO = bodySupplier.get();
            ResponseVO responseVO = responseSupplier.get();

            list.stream().forEach(param -> {
                switch (param.getPosition()) {
                    case 0: {
                        queryVO.getParams().add(param2VO.apply(param));
                        break;
                    }
                    case 1: {
                        headerVO.getParams().add(param2VO.apply(param));
                        break;
                    }
                    case 2: {
                        pathVO.getParams().add(param2VO.apply(param));
                        break;
                    }
                    case 3: {
                        if(param.getFlag().equals(BaseConstant.例子位置.query.getCode())) {
                            bodyVO.getParams().add(param2VO.apply(param));
                        }else {
                            bodyVO.setExample(new String(param.getExample(), Charset.defaultCharset()));
                        }

                        break;
                    }
                    case 4: {
                        if(param.getFlag().equals(BaseConstant.例子位置.query.getCode())) {
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

            // 根据父级排序
            Collections.sort(queryVO.getParams(), new ParamComparator());
            Collections.sort(headerVO.getParams(), new ParamComparator());
            Collections.sort(pathVO.getParams(), new ParamComparator());
            Collections.sort(bodyVO.getParams(), new ParamComparator());
            Collections.sort(responseVO.getParams(), new ParamComparator());

            interfaceVO.setQueryVO(queryVO);
            interfaceVO.setHeaderVO(headerVO);
            interfaceVO.setPathVO(pathVO);
            interfaceVO.setBodyVO(bodyVO);
            interfaceVO.setResponseVO(responseVO);

        }

    }

    class ParamComparator implements Comparator<ParamVO> {
        @Override
        public int compare(ParamVO o1, ParamVO o2) {
            if(o1.getSort() == null) {
                return -1;
            }else if(o2.getSort() == null) {
                return -1;
            }
            return o1.getSort().compareTo(o2.getSort());
        }

        /*@Override
        public int compare(ParamVO o1, ParamVO o2) {
            if(o1.getParent() == null) {
                return -100;
            }else if(o2.getParent() == null) {
                return -200;
            }else {
                return o1.getParent().compareTo(o2.getParamId());
            }
        }*/
    }

    private IhClass findOrgClassById(String id) {
        QueryWrapper<IhClass> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        wrapper.lambda().eq(IhClass::getClassId, id);
        return ihClassService.getOne(wrapper);
    }

    private IhInterface findOrgInterfaceById(String id) {
        QueryWrapper<IhInterface> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhInterface::getInterfaceId, id).eq(IhInterface::getValid, BaseConstant.有效性.有效.getCode());
        return ihInterfaceService.getOne(wrapper);

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
    public boolean updateInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, PathVO pathVO, BodyVO bodyVO, ResponseVO responseVO) {
        // 删除参数
        QueryWrapper<IhParams> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhParams::getInterfaceId, interfaceVO.getInterfaceId());
        ihParamsService.list(wrapper).stream().forEach(p -> ihParamsService.removeById(p.getParamId()));


        this.assembleShortCode(interfaceVO);
        IhInterface ihInterface = new IhInterface();
        BeanUtils.copyProperties(interfaceVO, ihInterface);
        boolean flag = ihInterfaceService.saveOrUpdate(ihInterface);

        this.saveParamsVOs(ihInterface.getInterfaceId(), queryVO, headerVO, pathVO, bodyVO, responseVO);
        return flag;
    }

    @Override
    public ClassVO findClassById(String classId) {
        QueryWrapper<IhClass> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(IhClass::getClassId, classId).eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        IhClass ihClass = ihClassService.getOne(wrapper);
        if(ihClass == null) {
            return null;
        }
        ClassVO classVO = new ClassVO();
        BeanUtils.copyProperties(ihClass, classVO);

        return classVO;
    }

    @Override
    public List<String> findHistoryDate() {
        return ihHiOperService.findHistoryByDate();
    }

    @Override
    public List<HiOperVO> findHistoryByDate(String date) {
        String year = String.valueOf(LocalDateTime.now().getYear());

        String startTime = year + "-" + date + " 00:00:00";
        String endTime = year + "-" + date + " 23:59:59";
        QueryWrapper<IhHiOper> wrapper = new QueryWrapper<>();
        wrapper.lambda().between(IhHiOper::getCreateTime, startTime, endTime).orderByDesc(IhHiOper::getCreateTime);
        List<IhHiOper> li = ihHiOperService.list(wrapper);

        List<HiOperVO> list = new ArrayList<>(li.size());
        li.stream().forEach(o -> {
            HiOperVO vo = new HiOperVO();
            BeanUtils.copyProperties(o, vo);
            list.add(vo);
        });


        return list;
    }

    @Override
    public boolean saveDirectory(String name, String parentId) {
        IhDirectory directory = new IhDirectory();
        directory.setName(name);
        directory.setParentDirectoryId(parentId);
        directory.setCreateTime(new Date());
        directory.setUpdateTime(new Date());
        directory.setValid(BaseConstant.有效性.有效.getCode());
        return ihDirectoryService.saveOrUpdate(directory);
    }

    @Override
    public boolean updateDirectory(DirectoryVO directoryVO) {
        IhDirectory directory = new IhDirectory();
        BeanUtils.copyProperties(directoryVO, directory);
        return ihDirectoryService.saveOrUpdate(directory);
    }

    @Override
    public Map<String, Object> findByParentDirectoryId(String parentDirectoryId) {
        Map<String, Object> map = new HashMap<>(4);

        // breadCrumbs
        LinkedList<String> title = new LinkedList<>();
        String parentId = parentDirectoryId;
        while (parentId != null) {
            DirectoryVO directoryVO = this.findDirectoryById(parentId);
            title.addFirst(directoryVO.getName());
            parentId = directoryVO.getParentDirectoryId();
        }
        map.put("breadCrumbs", StringUtils.join(title.toArray(), "/"));

        // class
        List<ClassVO> classVOS = this.findClassVOsByDirectoryId(parentDirectoryId);
        map.put("class", classVOS);

        // directory
        List<DirectoryVO> directoryVOS = this.findDirectoryVOsByParentDirectory(parentDirectoryId);
        map.put("directory", directoryVOS);


        return map;
    }

    @Override
    public boolean deleteDirectory(String directoryId) {
        List<ClassVO> classVOS = this.findClassVOsByDirectoryId(directoryId);
        if(classVOS.size() != 0) {
            return Boolean.FALSE;
        }
        List<DirectoryVO> directoryVOS = this.findDirectoryVOsByParentDirectory(directoryId);
        if(directoryVOS.size() != 0) {
            return Boolean.FALSE;
        }

        IhDirectory directory = ihDirectoryService.getById(directoryId);
        if(directory != null) {
            directory.setValid(BaseConstant.有效性.无效.getCode());
            directory.setDeleteTime(new Date());
            return ihDirectoryService.saveOrUpdate(directory);
        }

        return Boolean.FALSE;
    }

    @Override
    public DirectoryVO findDirectoryById(String directoryId) {

        QueryWrapper<IhDirectory> wrapper = new QueryWrapper();
        wrapper.lambda().eq(IhDirectory::getDirectoryId, directoryId).eq(IhDirectory::getValid, BaseConstant.有效性.有效.getCode());
        IhDirectory directory = ihDirectoryService.getOne(wrapper);
        if(directory != null) {
            DirectoryVO directoryVO = new DirectoryVO();
            BeanUtils.copyProperties(directory, directoryVO);
            return directoryVO;
        }
        return null;
    }

    private List<ClassVO> findClassVOsByDirectoryId(String directoryId) {
        QueryWrapper<IhClass> cWrapper = new QueryWrapper<>();
        cWrapper.lambda().eq(IhClass::getDirectoryId, directoryId).eq(IhClass::getValid, BaseConstant.有效性.有效.getCode());
        List<IhClass> classes = ihClassService.list(cWrapper);
        List<ClassVO> classVOS = new ArrayList<>(classes.size());

        classes.forEach(c -> {
            ClassVO vo = new ClassVO();
            BeanUtils.copyProperties(c, vo);
            classVOS.add(vo);
        });

        return classVOS;
    }

    private List<DirectoryVO> findDirectoryVOsByParentDirectory(String directoryId) {
        QueryWrapper<IhDirectory> dWrapper = new QueryWrapper<>();
        if(directoryId == null) {
            dWrapper.lambda().eq(IhDirectory::getValid, BaseConstant.有效性.有效.getCode()).isNull(IhDirectory::getParentDirectoryId);
        }else {
            dWrapper.lambda().eq(IhDirectory::getParentDirectoryId, directoryId).eq(IhDirectory::getValid, BaseConstant.有效性.有效.getCode());
        }
        List<IhDirectory> directories = ihDirectoryService.list(dWrapper);
        List<DirectoryVO> directoryVOS = new ArrayList<>(directories.size());
        directories.forEach(d -> {
            DirectoryVO vo = new DirectoryVO();
            BeanUtils.copyProperties(d, vo);
            directoryVOS.add(vo);
        });

        return directoryVOS;
    }

}
