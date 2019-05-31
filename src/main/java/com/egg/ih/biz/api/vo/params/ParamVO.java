package com.egg.ih.biz.api.vo.params;

import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class ParamVO {
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

    private Integer sort;
}
