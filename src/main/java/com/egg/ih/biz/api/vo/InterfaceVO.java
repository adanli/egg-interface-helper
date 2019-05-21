package com.egg.ih.biz.api.vo;

import com.egg.ih.biz.api.vo.params.BodyVO;
import com.egg.ih.biz.api.vo.params.HeaderVO;
import com.egg.ih.biz.api.vo.params.QueryVO;
import com.egg.ih.biz.api.vo.params.ResponseVO;
import lombok.Data;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class InterfaceVO {
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

    private QueryVO queryVO;
    private HeaderVO headerVO;
    private BodyVO bodyVO;
    private ResponseVO responseVO;

}
