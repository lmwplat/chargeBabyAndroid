package com.liumw.chargebaby.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
@Table(name = "charge")
public class Charge {
    private Long id;
    /**创建时间*/
    @Column(name = "create_time")
    private Date createTime;
    /**修改时间*/
    @Column(name = "update_time")
    private Date updateTime;
    /**编码*/
    @Column(name = "charge_no")
    private String chargeNo;
    /**充电点名称*/
    @Column(name = "name")
    private String name;
    /**所属区域*/
    @Column(name = "area")
    private String area;
    /**地址*/
    @Column(name = "address")
    private String address;
    /**交流电已建数量*/
    @Column(name = "ac_builded")
    private Integer acBuilded;
    /**交流电在建数量*/
    @Column(name = "ac_building")
    private Integer acBuilding;
    /**直流电已建数量*/
    @Column(name = "dc_builded")
    private Integer dcBuilded;
    /**直流电在建数量*/
    @Column(name = "dc_building")
    private Integer dcBuilding;
    /**经度*/
    @Column(name = "longitude")
    private Double longitude;
    /**纬度*/
    @Column(name = "latitude")
    private Double latitude;
    /**开发时间*/
    @Column(name = "begin_time")
    private String beginTime;
    /**服务电话*/
    @Column(name = "tel")
    private String tel;
    /**使用标准名称*/
    @Column(name = "standard_name")
    private String standardName;
    /**收费标准*/
    @Column(name = "fee_standard")
    private String feeStandard;
    /**详情*/
    @Column(name = "detail")
    private String detail;
    /**建设单位*/
    @Column(name = "depart")
    private String depart;


    public Charge(){

    }
    public Charge(Date createTime, Date updateTime, String chargeNo, String name, String area, String address, Integer acBuilded, Integer acBuilding, Integer dcBuilded, Integer dcBuilding, Double longitude, Double latitude, String beginTime, String tel, String standardName, String feeStandard, String detail, String depart) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.chargeNo = chargeNo;
        this.name = name;
        this.area = area;
        this.address = address;
        this.acBuilded = acBuilded;
        this.acBuilding = acBuilding;
        this.dcBuilded = dcBuilded;
        this.dcBuilding = dcBuilding;
        this.longitude = longitude;
        this.latitude = latitude;
        this.beginTime = beginTime;
        this.tel = tel;
        this.standardName = standardName;
        this.feeStandard = feeStandard;
        this.detail = detail;
        this.depart = depart;
    }

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

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    @Override
    public String toString() {
        return "Charge{" +
                "createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", chargeNo='" + chargeNo + '\'' +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", acBuilded=" + acBuilded +
                ", acBuilding=" + acBuilding +
                ", dcBuilded=" + dcBuilded +
                ", dcBuilding=" + dcBuilding +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", beginTime='" + beginTime + '\'' +
                ", tel='" + tel + '\'' +
                ", standardName='" + standardName + '\'' +
                ", feeStandard='" + feeStandard + '\'' +
                ", detail='" + detail + '\'' +
                ", depart='" + depart + '\'' +
                '}';
    }
}
