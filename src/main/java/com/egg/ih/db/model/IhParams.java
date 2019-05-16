package com.egg.ih.db.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value = "ih_params")
public class IhParams extends Model<IhParams> {

private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private String paramId;

    private String interfaceId;

    private String name;

    private String code;

    private String description;

    private String position;

    private String necessary;

    private Integer maxLength;

    private String remark;

    private byte[] example;

    private String type;


    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNecessary() {
        return necessary;
    }

    public void setNecessary(String necessary) {
        this.necessary = necessary;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public byte[] getExample() {
        return example;
    }

    public void setExample(byte[] example) {
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.paramId;
    }

    @Override
    public String toString() {
        return "IhParams{" +
        "paramId=" + paramId +
        ", interfaceId=" + interfaceId +
        ", name=" + name +
        ", code=" + code +
        ", description=" + description +
        ", position=" + position +
        ", necessary=" + necessary +
        ", maxLength=" + maxLength +
        ", remark=" + remark +
        ", example=" + example +
        ", type=" + type +
        "}";
    }
}
