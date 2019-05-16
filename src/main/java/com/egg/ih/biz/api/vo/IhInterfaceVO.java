package com.egg.ih.biz.api.vo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class IhInterfaceVO {
    private String name;
    private String code;
    private String description;
    private String url;
    /**
     * 请求类型
     */
    private String type;
}
