package com.egg.ih.db.mapper;

import com.egg.ih.db.model.IhClass;
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
public interface IhClassMapper extends BaseMapper<IhClass> {
    /**
     * 根据目录查询类
     * @param directoryId
     * @return
     */
    List<IhClass> findClassesByDirectoryId(String directoryId);
}
