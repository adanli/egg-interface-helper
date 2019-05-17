package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * 接口入参放在header中的参数，为<K, V>对
 * @author Administrator
 */
@Data
public class IhHeaderVO {
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
}
