package com.egg.ih.db.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lhx
 * @since 2019-05-16
 */
public class IhInterface extends Model<IhInterface> {

private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private String interfaceId;

    private String classId;

    private String hiOperId;

    private String valid;

    private String name;

    private String code;

    private String description;

    private String state;

    private String flag;

    private String url;

    private String type;


    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getHiOperId() {
        return hiOperId;
    }

    public void setHiOperId(String hiOperId) {
        this.hiOperId = hiOperId;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.interfaceId;
    }

    @Override
    public String toString() {
        return "IhInterface{" +
        "interfaceId=" + interfaceId +
        ", classId=" + classId +
        ", hiOperId=" + hiOperId +
        ", valid=" + valid +
        ", name=" + name +
        ", code=" + code +
        ", description=" + description +
        ", state=" + state +
        ", flag=" + flag +
        ", url=" + url +
        ", type=" + type +
        "}";
    }
}
