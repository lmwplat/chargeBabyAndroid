package com.liumw.chargebaby.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class Feedback {
    private Long id;
    /**创建时间*/
    private Date createTime;
    /**用户名*/
    private String username = "匿名";
    /**内容*/
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
