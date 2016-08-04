package com.liumw.chargebaby.entity;

/**
 * apk更新信息
 * Created by Administrator on 2016/8/4 0004.
 * Email:   1879358765@qq.com
 */
public class ApkInfo {

    /**apk版本号*/
    private Integer versionNo;
    /**apk 版本号名称*/
    private String versionName;
    /**描述*/
    private String description;
    /**下载地址*/
    private String url;

    public ApkInfo(){

    }
    public ApkInfo(Integer versionNo, String versionName, String description, String url) {
        this.versionNo = versionNo;
        this.versionName = versionName;
        this.description = description;
        this.url = url;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public String getVersionName() {

        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ApkInfo{" +
                "versionNo=" + versionNo +
                ", versionName='" + versionName + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
