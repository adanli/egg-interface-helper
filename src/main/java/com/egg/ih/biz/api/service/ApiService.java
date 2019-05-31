package com.egg.ih.biz.api.service;

import com.egg.ih.biz.api.vo.*;
import com.egg.ih.biz.api.vo.params.*;
import com.egg.ih.log.vo.HiOperVO;

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
    boolean saveClass(ClassVO ihClassVO);

    /**
     * 保存接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param queryVO 位于query的参数
     * @param headerVO 位于headers的参数
     * @param pathVO 位于path的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    boolean saveInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, PathVO pathVO, BodyVO bodyVO, ResponseVO responseVO) ;

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
     * @return
     */
    boolean updateClass(String classId, ClassVO classVO);

    /**
     * 更新接口
     * @param interfaceVO 接口类,包含名称, 代码, 描述等
     * @param queryVO 位于params的参数
     * @param headerVO 位于headers的参数
     * @param pathVO 位于path的参数
     * @param bodyVO 位于body的参数
     * @param responseVO 返回值
     * @return
     */
    boolean updateInterface(InterfaceVO interfaceVO, QueryVO queryVO, HeaderVO headerVO, PathVO pathVO, BodyVO bodyVO, ResponseVO responseVO) ;

    /**
     * 根据类主键查询类
     * @param classId
     * @return
     */
    ClassVO findClassById(String classId);

    /**
     * 查询历史记录记录的日期列表
     * @return
     */
    List<String> findHistoryDate();

    /**
     * 根据操作日期查询历史记录
     * @param
     * @return
     */
    List<HiOperVO> findHistoryByDate(String date);

    /**
     * 保存目录
     * @param name
     * @param parentId
     * @return
     */
    boolean saveDirectory(String name, String parentId);

    /**
     * 更新目录
     * @param directoryVO
     * @return
     */
    boolean updateDirectory(DirectoryVO directoryVO);

    /**
     * 通过父级目录主键查询内容
     * @param parentDirectoryId
     * @return
     */
    Map<String, Object> findByParentDirectoryId(String parentDirectoryId);

    /**
     * 根据主键删除目录
     * @param directoryId
     * @return
     */
    boolean deleteDirectory(String directoryId);

    /**
     * 根据主键查询目录
     * @param directoryId
     * @return
     */
    DirectoryVO findDirectoryById(String directoryId);
}
