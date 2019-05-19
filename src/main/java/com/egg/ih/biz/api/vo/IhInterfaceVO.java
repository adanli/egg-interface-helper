package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

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

    /**
     * params
     */
    private List<IhParamsVO> params;

    /**
     * header
     */
    private List<IhHeaderVO> headers;

    private IhBodyVO body;

    private IhResponseVO response;

}
