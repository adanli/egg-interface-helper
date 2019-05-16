package com.egg.ih.util.errorcode;

public enum DefaultErrorCode implements IErrorCode {
    /**
     * 成功
     */
    SUCCESS("0", "success"),

    /**
     * 空指针
     */
    NULL_POINTER("0*0001", "null pointer exception"),

    /**
     * 数组下标越界
     */
    INDEX_OUT_OF_BOUNDS("0*0002", "index out of bounds"),

    /**
     * 参数异常
     */
    ILLEGAL_ARGUEMENTS("0*0003", "illegal arguments"),

    /**
     * 类型转换异常
     */
    TYPE_CAST_ERROR("0*0004", "type cast error"),

    /**
     * 文件不存在
     */
    FILE_NOT_EXIST("0*0005", "file not exist"),

    /**
     * 线程等待超时
     */
    THREAD_WAIT_TIMEOUT("0*0006", "thread wait timeout"),

    /**
     * 非法字符
     */
    ILLEGAL_CHARACTERS("0*0007", "strings contains illegal characters"),

    /**
     * 长度超出32个字节
     */
    EXTREME_SIZE("0*0008", "strings length extreme 32 bits"),

    /**
     * 类型异常
     */
    TYPE_ERROR("0*0009", "type error")
    ;

    DefaultErrorCode(String code, String message) {
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
