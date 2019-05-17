package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.List;

/**
 * 接口入参放在body中的参数
 * @author Administrator
 */
@Data
public class IhBodyVO {
    private List<IhParamsVO> list;
    private String example;
}
