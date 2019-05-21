package com.egg.ih.biz.api.service;

import com.egg.ih.biz.api.vo.*;
import com.egg.ih.biz.api.vo.params.BodyVO;
import com.egg.ih.biz.api.vo.params.HeaderVO;
import com.egg.ih.biz.api.vo.params.QueryVO;
import com.egg.ih.biz.api.vo.params.ResponseVO;

import java.util.List;

/**
 * @author Administrator
 */
public interface ApiService {
    /**
     * 保存接口类
     * @param ihClassVO
     * @return
     */
    int saveClass(ClassVO ihClassVO);

    /**
     * 保存接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param queryVO 位于query的参数
     * @param headerVO 位于headers的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    int saveInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, BodyVO bodyVO, ResponseVO responseVO) ;

    /**
     * 查询所有类, 将类名以字典形式顺利展示
     * @return
     */
    List<ClassVO> findClasses();

    /**
     * 根据类主键查询接口列表
     * @param classId 类主键
     * @return
     */
    List<InterfaceVO> findInterfacesByClassId(String classId);

    /**
     * 根据接口主键查询接口详细内容
     * @param interfaceId
     * @return
     */
    InterfaceVO findInterfaceById(String interfaceId);

    /**
     * 根据接口主键逻辑删除接口
     * @param interfaceId
     */
    void deleteInterfaceById(String interfaceId);

    /**
     * 根据类主键逻辑删除类
     * @param classId
     */
    void deleteClassById(String classId);

    /**
     * 更新类名称
     * @param classId
     * @param className
     * @return
     */
    int updateClass(String classId, String className);

    /**
     * 更新接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param queryVO 位于params的参数
     * @param headerVO 位于headers的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    int updateInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, BodyVO bodyVO, ResponseVO responseVO) ;

}
