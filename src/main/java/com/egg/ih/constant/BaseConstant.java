package com.egg.ih.constant;

import com.egg.ih.biz.api.vo.InterfaceVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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

        public static String getNameByCode(String code) {
            是否必填 p = Arrays.stream(是否必填.values()).filter(c -> c.getCode().equals(code)).findFirst().orElseGet(null);
            if(p == null) {
                return null;
            }
            return p.getName();
        }

    }

    @Getter
    public enum 参数存储位置 {
//        QUERY, HEADER, BODY, RESPONSE
        QUERY("query", 0),
        HEADER("header", 1),
        BODY("body", 2),
        RESPONSE("response", 3);
        参数存储位置(String name, Integer code) {
            this.name = name;
            this.code = code;
        }
        private String name;
        private Integer code;

        public static String getNameByCode(Integer code) {
            BaseConstant.参数存储位置 参数 =  Arrays.stream(参数存储位置.values()).filter(c -> c.getCode().equals(code)).findFirst().orElseGet(null);
            if(参数 == null) {
                return null;
            }
            return 参数.getName();

        }

        public static Integer getCodeByName(String name) {
            String _name = name.toLowerCase();
            BaseConstant.参数存储位置 p = Arrays.stream(参数存储位置.values()).filter(c -> c.getName().equals(_name)).findFirst().orElseGet(null);
            if(p == null) {
                return null;
            }
            return p.getCode();
        }

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
