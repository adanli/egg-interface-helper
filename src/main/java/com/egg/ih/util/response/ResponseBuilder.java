package com.egg.ih.util.response;

import com.egg.ih.util.errorcode.IErrorCode;

public class ResponseBuilder {
    public static BaseResponse build(IErrorCode error) {
        return build(error, null);
    }

    public static <T> BaseResponse<T> build(IErrorCode error, T data) {
        return new BaseResponse<>(error, data);
    }
}

