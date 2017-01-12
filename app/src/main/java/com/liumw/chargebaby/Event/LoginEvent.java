package com.liumw.chargebaby.Event;

import com.liumw.chargebaby.vo.UserInfo;

/**
 * 登录事件
 * Created by liumw on 2016/8/22 0022.
 */
public class LoginEvent {
    private UserInfo mUserInfo;

    public UserInfo getMsg() {
        return mUserInfo;
    }

    public LoginEvent(UserInfo userInfo) {
        this.mUserInfo = userInfo;
    }
}
