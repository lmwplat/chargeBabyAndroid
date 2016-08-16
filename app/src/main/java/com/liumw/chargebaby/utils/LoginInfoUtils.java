package com.liumw.chargebaby.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.entity.User;
import com.liumw.chargebaby.vo.UserInfo;

/**
 * Created by Administrator on 2016/7/30 0030.
 * Email:   1879358765@qq.com
 */
public class LoginInfoUtils {


    /**
     * 获取登录信息
     * @param context
     * @return
     */
    public static UserInfo getLoginInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
        String str = sp.getString(Application.LONIN_INFO, null);
        if (str == null || str.equals("null")){
            return null;
        }else {
            UserInfo userInfo = JSON.parseObject(str, UserInfo.class);
            return userInfo;
        }
    }

    /**
     * 移除登录信息
     * @param context
     * @return
     */
    public static Boolean removeLoginInfo(Context  context){
        SharedPreferences sp = context.getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Application.LONIN_INFO, ChargeConstants.LOGIN_NULL).commit();
        String str = sp.getString(Application.LONIN_INFO, null);
        if (str == null || str.equals("null")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 设置登录信息
     * @param context
     * @param value
     * @return
     */
    public static void setLoginInfo(Context context, String value){
        SharedPreferences sp = context.getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
        //将登录信息，存入sharedPreference
        sp.edit().putString(Application.LONIN_INFO, value).commit();
    }


}
