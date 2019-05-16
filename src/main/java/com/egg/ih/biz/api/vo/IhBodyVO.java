package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Map;

/**
 * 接口入参放在body中的参数
 * @author Administrator
 */
@Data
public class IhBodyVO {
    private Map<String, Object> body;
    private String example;
}
