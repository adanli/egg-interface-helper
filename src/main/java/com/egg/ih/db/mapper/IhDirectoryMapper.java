package com.egg.ih.db.mapper;

import com.egg.ih.db.model.IhDirectory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lhx
 * @since 2019-05-26
 */
@Mapper
public interface IhDirectoryMapper extends BaseMapper<IhDirectory> {
    /**
     * 查询目录列表
     * @return
     */
    List<IhDirectory> findDirectory();
}
