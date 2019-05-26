package com.egg.ih.db.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author lhx
 * @since 2019-05-26
 */
public class IhDirectory extends Model<IhDirectory> {

private static final long serialVersionUID=1L;
    @TableId(type = IdType.UUID)
    private String directoryId;

    private String name;

    private String code;

    private String description;

    private String state;

    private String flag;

    private String valid;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String parentDirectoryId;


    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId;
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

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getParentDirectoryId() {
        return parentDirectoryId;
    }

    public void setParentDirectoryId(String parentDirectoryId) {
        this.parentDirectoryId = parentDirectoryId;
    }

    @Override
    protected Serializable pkVal() {
        return this.directoryId;
    }

    @Override
    public String toString() {
        return "IhDirectory{" +
        "directoryId=" + directoryId +
        ", name=" + name +
        ", code=" + code +
        ", description=" + description +
        ", state=" + state +
        ", flag=" + flag +
        ", valid=" + valid +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", deleteTime=" + deleteTime +
        ", parentDirectoryId=" + parentDirectoryId +
        "}";
    }
}
