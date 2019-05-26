package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author adanl
 */
@Data
public class DirectoryVO {
    private String directoryId;

    private String name;

    private String code;

    private String description;

    private String state;

    private String flag;

    private String valid;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String parentDirectoryId;
}
