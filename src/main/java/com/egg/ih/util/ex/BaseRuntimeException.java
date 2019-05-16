package com.egg.ih.util.ex;

import com.egg.ih.util.errorcode.IErrorCode;
import lombok.Getter;

/**
 * @author Administrator
 */
@Getter
public class BaseRuntimeException extends RuntimeException{
    private String errCode;
    private String errMsg;
    private Throwable e;

    public BaseRuntimeException(String errorCode, String errMsg, Throwable e) {
        this.errCode = errorCode;
        this.errMsg = errMsg;
        this.e = e;
    }

    public BaseRuntimeException(IErrorCode error, Throwable e) {
        this(error.getCode(), error.getMessage(), e);
    }
}

