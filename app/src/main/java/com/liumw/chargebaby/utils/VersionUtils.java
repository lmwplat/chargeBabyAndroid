package com.liumw.chargebaby.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.liumw.chargebaby.base.ChargeConstants;

/**
 * 版本控制工具类
 * Created by Administrator on 2016/8/2 0002.
 * Email:   1879358765@qq.com
 */
public class VersionUtils {
    public static final String APK_DOWNLOAD_ADDRESS="http://120.76.194.88:80/chargeBaby/apk/chargebaby.apk";

    /**
     * 获取软件版本号
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = context.getPackageManager().getPackageInfo(
                    ChargeConstants.PACKAGE_NAME, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verCode;
    }
    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(
                    ChargeConstants.PACKAGE_NAME,  0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        return verName;
    }



}
