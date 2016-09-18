package com.liumw.chargebaby.base;

import android.app.Application;

import org.xutils.x;

/**
 * Created by liumw on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class AppConstants {

    /**服务器*/
//    public static final String SERVER = "http://192.168.0.101:8080/chargebabyforplat/";
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
    /**用户添加评论*/
    public static final String ACTION_ADD_COMMENT = "comment/addComment";
    /**用户添加回复*/
    public static final String ACTION_ADD_REPLY = "comment/addReply";
    /**用户获取所有评论*/
    public static final String ACTION_FIND_ALL_COMMENT = "comment/findAllComment";



    /**SharedPreferences 存储的文件名*/
    public static final String SP_FILE_NAME = "share_data";
    /**SharedPreferences 登录信息*/
    public static final String LONIN_INFO = "loginInfo";

    /**apk下载地址*/
    public static final String APK_DOWNLOAD_ADDRESS = "http://120.76.194.88:8070/apk/";
    public static final String APK_INSTALL_NAME = "chargebaby.apk";



}
