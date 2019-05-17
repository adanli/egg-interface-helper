package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class IhInterfaceVO {
    private String interfaceId;

    private String classId;

    private String valid;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String name;

    private String code;

    private String description;

    private String state;

    private String flag;

    private String url;

    private String type;
}
