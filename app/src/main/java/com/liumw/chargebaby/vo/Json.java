package com.liumw.chargebaby.vo;

import java.io.Serializable;

/**
 *  JSON模型
 *
 * 用户后台向前台返回的JSON对象
 * Created by Administrator on 2016/7/30 0030.
 * Email:   1879358765@qq.com
 */
public class Json implements Serializable{
    /**返回码*/
    private String result_code;
    /**是否成功*/
    private boolean success;
    /**返回信息*/
    private String msg = "";
    /**返回对象*/
    private Object obj;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "Json{" +
                "result_code='" + result_code + '\'' +
                ", success=" + success +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
