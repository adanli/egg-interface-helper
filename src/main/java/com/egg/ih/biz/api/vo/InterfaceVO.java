package com.egg.ih.biz.api.vo;

import com.egg.ih.biz.api.vo.params.*;
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

    private String shortCode;

    private QueryVO queryVO;
    private HeaderVO headerVO;
    private PathVO pathVO;
    private BodyVO bodyVO;
    private ResponseVO responseVO;

}
