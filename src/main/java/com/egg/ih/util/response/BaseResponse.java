package com.egg.ih.util.response;

import com.egg.ih.util.errorcode.DefaultErrorCode;
import com.egg.ih.util.errorcode.IErrorCode;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class BaseResponse<T> {
    public BaseResponse(T data) {
        this(DefaultErrorCode.SUCCESS.getCode(), DefaultErrorCode.SUCCESS.getMessage(), data);
    }

    public BaseResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(IErrorCode error, T data) {
        this(error.getCode(), error.getMessage(), data);
    }

    private String code;
    private String msg;
    private T data;
}
