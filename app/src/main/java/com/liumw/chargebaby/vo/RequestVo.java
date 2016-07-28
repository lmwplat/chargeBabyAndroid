package com.liumw.chargebaby.vo;

import java.util.Map;

/**
 *
 * Created by Administrator on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class RequestVo {
    public String requestUrl;
    public Map<String, String> requestDataMap;

    public RequestVo() {
        super();
    }

    public RequestVo(String requestUrl, Map<String, String> requestDataMap) {
        super();
        this.requestUrl = requestUrl;
        this.requestDataMap = requestDataMap;
    }

    @Override
    public String toString() {
        return "RequestVo [requestUrl=" + requestUrl + ", requestDataMap=" + requestDataMap + "]";
    }
}
