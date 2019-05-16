package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Map;

/**
 * 接口入参放在header中的参数，为<K, V>对
 * @author Administrator
 */
@Data
public class IhHeaderVO {
    private Map<String, Object> header;
}
