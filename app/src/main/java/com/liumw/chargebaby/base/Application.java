package com.liumw.chargebaby.base;

import android.content.Context;

/**
 * Created by Administrator on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class Application extends android.app.Application {

    /**服务器*/
    public static final String SERVER = "http://192.168.0.102:8080/chargebabyforplat/";
    /**远程登录方法接口*/
    public static final String ACTION_LOGIN = "userController/login";
    /**远程注册方法接口*/
    public static final String ACTION_REGIST = "userController/reg";

    /**SharedPreferences 存储的文件名*/
    public static final String SP_FILE_NAME = "share_data";
    /**SharedPreferences 登录信息*/
    public static final String LONIN_INFO = "loginInfo";



    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
    }
}
