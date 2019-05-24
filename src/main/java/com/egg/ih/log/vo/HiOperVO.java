package com.egg.ih.log.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author adanl
 */
@Data
public class HiOperVO {
    private String interfaceId;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String url;

    private String operIp;
}
