package com.egg.ih.constant;

import lombok.Getter;

/**
 * @author adanl
 */
public class BaseConstant {

    @Getter
    public enum 有效性 {
        /**
         *
         */
        有效("有效", "Y"),
        无效("无效", "N")
        ;
        有效性(String name, String code) {
            this.name = name;
            this.code = code;
        }

        private String name;
        private String code;
    }

    public enum 请求方式 {
        /**
         * 查询，不改变数据
         */
        GET,
        /**
         * 新增
         */
        POST,
        /**
         * 修改，若新字段为null，则覆盖旧值
         */
        PUT,
        /**
         * 修改，若新字段为null，则不覆盖
         */
        PATCH,
        /**
         * 删除
         */
        DELETE
    }

    @Getter
    public enum 是否必填 {
        /**
         *
         */
        是("是", "Y"),
        否("否", "N")
        ;
        是否必填(String name, String code) {
            this.name = name;
            this.code = code;
        }

        private String name;
        private String code;
    }

    @Getter
    public enum 参数存储位置 {
        PARAMS, HEADER, BODY, RESPONSE
    }

    /**
     * 区分body和response的参数位于列表还是example
     */
    @Getter
    public enum 例子位置 {
        /**
         *
         */
        query("query", "0"),
        example("example", "1")
        ;
        例子位置(String name, String code) {
            this.name = name;
            this.code = code;
        }

        private String name;
        private String code;
    }

}
