package com.egg.ih.biz.ex;

import com.egg.ih.util.errorcode.IErrorCode;

/**
 * @author adanl
 */

public enum  BaseErrorCode implements IErrorCode {
    /**
     *
     */
    无法删除("当前目录下存在其他目录或类，请先删除", "0*0010");

    BaseErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
