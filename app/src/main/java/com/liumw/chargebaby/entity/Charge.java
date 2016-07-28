package com.liumw.chargebaby.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class Charge {
    private Long id;
    /**创建时间*/
    private Date createTime;
    /**修改时间*/
    private Date updateTime;
    /**充电点名称*/
    private String name;
    /**所属区域*/
    private String area;
    /**地址*/
    private String address;
    /**交流电已建数量*/
    private Integer acBuilded;
    /**交流电在建数量*/
    private Integer acBuilding;
    /**直流电已建数量*/
    private Integer dcBuilded;
    /**直流电在建数量*/
    private Integer dcBuilding;
    /**经度*/
    private Double longitude;
    /**纬度*/
    private Double latitude;
    /**开发时间*/
    private String beginTime;
    /**服务电话*/
    private String tel;
    /**使用标准*/
    private Integer standard;
    /**使用标准名称*/
    private String standardName;
    /**收费标准*/
    private String feeStandard;
    /**详情*/
    private String detail;
    /**建设单位*/
    private String depart;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAcBuilded() {
        return acBuilded;
    }

    public void setAcBuilded(Integer acBuilded) {
        this.acBuilded = acBuilded;
    }

    public Integer getAcBuilding() {
        return acBuilding;
    }

    public void setAcBuilding(Integer acBuilding) {
        this.acBuilding = acBuilding;
    }

    public Integer getDcBuilded() {
        return dcBuilded;
    }

    public void setDcBuilded(Integer dcBuilded) {
        this.dcBuilded = dcBuilded;
    }

    public Integer getDcBuilding() {
        return dcBuilding;
    }

    public void setDcBuilding(Integer dcBuilding) {
        this.dcBuilding = dcBuilding;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getFeeStandard() {
        return feeStandard;
    }

    public void setFeeStandard(String feeStandard) {
        this.feeStandard = feeStandard;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }
}
