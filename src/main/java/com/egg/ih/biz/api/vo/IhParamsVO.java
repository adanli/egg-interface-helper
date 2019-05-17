package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * 接口入参为params的参数，即跟在url?后的<K, V>对
 * @author Administrator
 */
@Data
public class IhParamsVO {
    private String paramId;

    private String interfaceId;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String name;

    private String code;

    private String description;

    private String position;

    private String necessary;

    private Integer maxLength;

    private String remark;

    private Integer example;

    private String type;

    private String parent;
}
