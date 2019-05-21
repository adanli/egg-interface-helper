package com.egg.ih.biz.api.vo.params;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */
@Data
public abstract class AbstractParamVO {
    private List<ParamVO> params;
}
