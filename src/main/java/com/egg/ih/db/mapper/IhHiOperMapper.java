package com.egg.ih.db.mapper;

import com.egg.ih.db.model.IhHiOper;
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
public interface IhHiOperMapper extends BaseMapper<IhHiOper> {
    /**
     * 查询历史记录日期列表，格式mm-dd
     * @return
     */
    List<String> findHistoryByDate();
}
