package com.liumw.chargebaby.entity;

/**
 * 地图覆盖物
 * Created by liumw on 2016/6/17 0017.
 * Email:   1879358765@qq.com
 */
public class BDMapData {

    /**充电点名称*/
    private String name;
    /**地址*/
    private String address;
    /**经度*/
    private Double longitude;
    /**纬度*/
    private Double latitude;
    /** 距离*/
    private double distance;
    /**收费标准*/
    private String feeStandard;

    public BDMapData(String name, String address, Double longitude, Double latitude, double distance, String feeStandard) {
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.feeStandard = feeStandard;
    }
    public BDMapData(){

    }

    public BDMapData(String name, Double longitude, Double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getFeeStandard() {
        return feeStandard;
    }

    public void setFeeStandard(String feeStandard) {
        this.feeStandard = feeStandard;
    }
}
