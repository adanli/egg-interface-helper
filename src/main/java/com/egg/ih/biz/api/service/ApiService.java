package com.egg.ih.biz.api.service;

import com.egg.ih.biz.api.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface ApiService {
    /**
     * 保存接口类
     * @param ihClassVO
     * @return
     */
    int saveClass(IhClassVO ihClassVO);

    /**
     * 保存接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param params 位于params的参数
     * @param headers 位于headers的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    int saveInterface(IhInterfaceVO interfaceVO, List<IhParamsVO> params, List<IhHeaderVO> headers, IhBodyVO bodyVO, IhResponseVO responseVO) ;

    /**
     * 查询所有类, 将类名以字典形式顺利展示
     * @return
     */
    List<IhClassVO> findClasses();

    /**
     * 根据类主键查询接口列表
     * @param classId 类主键
     * @return
     */
    List<IhInterfaceVO> findInterfacesByClassId(String classId);

    /**
     * 根据接口主键查询接口详细内容
     * @param interfaceId
     * @return
     */
    IhInterfaceVO findInterfaceById(String interfaceId);

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

}
