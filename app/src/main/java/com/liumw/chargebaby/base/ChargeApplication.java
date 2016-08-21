package com.liumw.chargebaby.base;

import android.app.Application;

import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.x;

/**
 * Created by liumw on 2016/8/17 0017.
 */
public class ChargeApplication extends Application{

    //登录用户名
    private String loginName;
    //登录用户信息
    private UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

        userInfo = LoginInfoUtils.getLoginInfo(getApplicationContext());
        if (userInfo == null){
            loginName = null;
        }else{
            loginName = userInfo.getUsername();
        }


    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
