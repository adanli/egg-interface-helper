package com.egg.ih.db.mapper;

import com.egg.ih.db.model.IhParams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lhx
 * @since 2019-05-17
 */
@Mapper
public interface IhParamsMapper extends BaseMapper<IhParams> {
    /**
     * 根据接口主键查询参数列表
     * @param interfaceId
     * @return
     */
    List<IhParams> findParamsByInterfaceId(String interfaceId);
}
