package com.liumw.chargebaby.entity;

import com.google.gson.Gson;

import java.util.Date;

/**
 * apk更新信息
 * Created by Administrator on 2016/8/4 0004.
 * Email:   1879358765@qq.com
 */
public class ApkInfo {


    /**
     * createTime : 2016-08-07 12:42:24
     * description : 初始版本
     * id : 1
     * updateTime : 2016-08-07 12:42:24
     * url : http://120.76.194.88:8070/apk/chargebaby.apk
     * versionName : Ver 1.0
     * versionNo : 2
     */

    private Date createTime;
    private String description;
    private int id;
    private Date updateTime;
    private String url;
    private String versionName;
    private int versionNo;

    public static ApkInfo objectFromData(String str) {

        return new Gson().fromJson(str, ApkInfo.class);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }
}
