package com.egg.ih.util.errorcode;

public interface IErrorCode {
    /**
     * 统一错误码提取入口
     * @return
     */
    String getCode();

    /**
     * 统一错误信息提取入口
     * @return
     */
    String getMessage();
}
