package com.egg.ih.log.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
public class HiOperDTO {
    private Integer id;
    private String operateUser;
    private String operateIp;
    private String details;
    private Date createTime;
    private Integer logState;
    private String service;
    private String action;
    private String module;
    private String operateClass;
    private String operateMethod;
    private String operateArgs;
    private String errorCode;
    private String errorMsg;
    private String description;
    private String logType;

    @Data
    public class OperateArgs{
        private String name;
        private String code;
        private String valid;

        @Override
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("operateArgs:{");
            if(!StringUtils.isEmpty(name)){
                buffer.append("name:\"" + name + "\",");
            }
            if(!StringUtils.isEmpty(code)){
                buffer.append("code:\"" + code + "\",");
            }
            if(!StringUtils.isEmpty(valid)){
                buffer.append("valid:\"" + valid + "\",");
            }

            return buffer.substring(0, buffer.length()-1)+"}";
        }
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        if(!StringUtils.isEmpty(this.operateUser)){
            buffer.append("\"operateUser\":\"" + this.operateUser + "\",");
        }
        if(!StringUtils.isEmpty(this.operateIp)){
            buffer.append("\"operateIp\":\"" + this.operateIp + "\",");
        }
        if(!StringUtils.isEmpty(this.details)){
            buffer.append("\"details\":\"" + this.details + "\",");
        }
        if(!StringUtils.isEmpty(this.logState)){
            buffer.append("\"logState\":\"" + this.logState + "\",");
        }
        if(!StringUtils.isEmpty(this.service)){
            buffer.append("\"service\":\"" + this.service + "\",");
        }
        if(!StringUtils.isEmpty(this.action)){
            buffer.append("\"action\":\"" + this.action + "\",");
        }
        if(!StringUtils.isEmpty(this.module)){
            buffer.append("\"module\":\"" + this.module + "\",");
        }
        if(!StringUtils.isEmpty(this.operateClass)){
            buffer.append("\"operateClass\":\"" + this.operateClass + "\",");
        }
        if(!StringUtils.isEmpty(this.operateMethod)){
            buffer.append("\"operateMethod\":\"" + this.operateMethod + "\",");
        }
        if(!StringUtils.isEmpty(this.description)){
            buffer.append("\"operateDesc\":\"" + this.description + "\",");
        }
        if(!StringUtils.isEmpty(this.errorCode)){
            buffer.append("\"errorCode\":\"" + this.errorCode + "\",");
        }
        if(!StringUtils.isEmpty(this.errorMsg)){
            buffer.append("\"errorMsg\":\"" + this.errorMsg + "\",");
        }
        if(!StringUtils.isEmpty(this.operateArgs)){
            buffer.append("operateArgs:" + this.operateArgs + ",");
        }

        if(buffer.equals("{")){
            return "{}";
        }else{
            return buffer.substring(0, buffer.length()-1)+"}";
        }


    }
}
