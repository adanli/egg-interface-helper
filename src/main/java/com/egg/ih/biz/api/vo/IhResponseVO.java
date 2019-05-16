package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author Administrator
 */
@Data
public class IhResponseVO {
    private Map<String, Object> body;
    private String example;
}
