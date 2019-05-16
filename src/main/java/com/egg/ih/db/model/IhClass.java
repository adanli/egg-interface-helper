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
public class IhClass extends Model<IhClass> {

private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private String classId;

    private String state;

    private String flag;

    private String valid;

    private String name;

    private String code;

    private String description;


    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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

    @Override
    protected Serializable pkVal() {
        return this.classId;
    }

    @Override
    public String toString() {
        return "IhClass{" +
        "classId=" + classId +
        ", state=" + state +
        ", flag=" + flag +
        ", valid=" + valid +
        ", name=" + name +
        ", code=" + code +
        ", description=" + description +
        "}";
    }
}
