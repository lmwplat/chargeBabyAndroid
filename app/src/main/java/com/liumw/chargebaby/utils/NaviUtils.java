package com.liumw.chargebaby.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.PoiItem;
import com.liumw.chargebaby.ui.navi.GPSNaviActivity;

import java.net.URLEncoder;

/**
 * Created by liumw on 2016/8/21 0021.
 */
public class NaviUtils {
    public static void toNavigation(Context context, PoiItem poiItem, LatLng currentLatLng) {
        //1.判断用户手机是否安装高德地图APP
        boolean isInstalled = isPkgInstalled("com.autonavi.minimap", context);
        //2.首选使用高德地图APP完成导航
        if (isInstalled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("androidamap://navi?");
            try {
                //填写应用名称
                stringBuilder.append("sourceApplication=" + URLEncoder.encode("油气", "utf-8"));
                //导航目的地
                stringBuilder.append("&poiname=" + URLEncoder.encode(poiItem.getTitle(), "utf-8"));
                //目的地经纬度
                stringBuilder.append("&lat=" + poiItem.getLatLonPoint().getLatitude());
                stringBuilder.append("&lon=" + poiItem.getLatLonPoint().getLongitude());
                stringBuilder.append("&dev=1&style=2");
            } catch (Exception e) {
                e.printStackTrace();
            }
            //调用高德地图APP
            Intent intent = new Intent();
            intent.setPackage("com.autonavi.minimap");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setAction(Intent.ACTION_VIEW);
            //传递组装的数据
            intent.setData(Uri.parse(stringBuilder.toString()));
            context.startActivity(intent);

        } else {
            //使用高德地图导航sdk完成导航
            Intent intent = new Intent(context, GPSNaviActivity.class);
            intent.putExtra("point_start", new LatLng(currentLatLng.latitude, currentLatLng.longitude));
            intent.putExtra("point_end", new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()));
            context.startActivity(intent);
        }
    }

    private static boolean isPkgInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
