package com.egg.ih.log.constant;

/**
 * @author Administrator
 */
public class LogConstant {
    public static final Integer LOG_STATE_SUCCESS = 0;
    public static final Integer LOG_STATE_ERROR = 1;

    //系统日志
    public enum 日志类型{
        系统日志("系统日志", "sys_log"),
        操作日志("操作日志", "oper_log"),
        登录日志("登录日志", "login_log"),
        数据库日志("数据库日志", "database_log");
        日志类型(String name, String code){
            this.name = name;
            this.code = code;
        }

        private final String name;
        private final String code;

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }
    }
}
