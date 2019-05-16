package com.egg.ih.biz.api.vo;

import lombok.Data;

import java.util.Map;

/**
 * 接口入参为params的参数，即跟在url?后的<K, V>对
 * @author Administrator
 */
@Data
public class IhParamsVO {
    private Map<String, Object> params;
}
