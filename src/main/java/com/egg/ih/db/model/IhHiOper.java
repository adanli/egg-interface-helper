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
 * @since 2019-05-16
 */
public class IhHiOper extends Model<IhHiOper> {

private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private String hiOperId;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private String url;

    private String operIp;


    public String getHiOperId() {
        return hiOperId;
    }

    public void setHiOperId(String hiOperId) {
        this.hiOperId = hiOperId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperIp() {
        return operIp;
    }

    public void setOperIp(String operIp) {
        this.operIp = operIp;
    }

    @Override
    protected Serializable pkVal() {
        return this.hiOperId;
    }

    @Override
    public String toString() {
        return "IhHiOper{" +
        "hiOperId=" + hiOperId +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", deleteTime=" + deleteTime +
        ", url=" + url +
        ", operIp=" + operIp +
        "}";
    }
}
