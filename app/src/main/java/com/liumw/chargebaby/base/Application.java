package com.liumw.chargebaby.base;

import android.content.Context;

import org.xutils.x;

/**
 * Created by liumw on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class Application extends android.app.Application {

    /**服务器*/
//    public static final String SERVER = "http://192.168.0.100:8080/chargebabyforplat/";
    public static final String SERVER = "http://120.76.194.88:8080/chargebabyforplat/";
    /**远程登录方法接口*/
    public static final String ACTION_LOGIN = "userController/login";
    /**远程注册方法接口*/
    public static final String ACTION_REGIST = "userController/reg";
    /**远程确认修改密码方法接口*/
    public static final String ACTION_CONFIRM = "userController/confirm";
    /**远程提交意见反馈*/
    public static final String ACTION_FEEDBACK_CONFIRM = "feedbackController/feedbackConfirm";
    /**APK版本检测*/
    public static final String ACTION_APK_CHECK = "apkVersion/checkApkVersion";
    /**用户添加收藏*/
    public static final String ACTION_ADD_FAVORITE = "favorite/addFavorite";
    /**用户取消收藏*/
    public static final String ACTION_REMOVE_FAVORITE = "favorite/removeFavorite";
    /**用户添加桩点信息*/
    public static final String ACTION_ADD_COLLECT = "collect/addCollect";


    /**SharedPreferences 存储的文件名*/
    public static final String SP_FILE_NAME = "share_data";
    /**SharedPreferences 登录信息*/
    public static final String LONIN_INFO = "loginInfo";

    /**apk下载地址*/
    public static final String APK_DOWNLOAD_ADDRESS = "http://120.76.194.88:8070/apk/";
    public static final String APK_INSTALL_NAME = "chargebaby.apk";

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
    }
}
