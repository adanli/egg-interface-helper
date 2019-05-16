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
public class IhHiOper extends Model<IhHiOper> {

private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    private String hiOperId;

    private String url;

    private String operIp;


    public String getHiOperId() {
        return hiOperId;
    }

    public void setHiOperId(String hiOperId) {
        this.hiOperId = hiOperId;
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
        ", url=" + url +
        ", operIp=" + operIp +
        "}";
    }
}
