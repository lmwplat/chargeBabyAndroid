package com.liumw.chargebaby.utils;

import android.app.Application;

import org.xutils.x;

/**
 * Created by liumw on 2016/6/16 0016.
 * Email:   1879358765@qq.com
 */
public class ChargeBabyApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
