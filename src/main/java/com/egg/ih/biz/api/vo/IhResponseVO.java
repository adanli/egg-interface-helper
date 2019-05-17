package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class IhResponseVO {
    private List<IhParamsVO> list;
    private String example;
}
