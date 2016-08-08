package com.liumw.chargebaby.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.ui.navi.BaseActivity;
import com.liumw.chargebaby.ui.navi.BasicNaviActivity;
import com.liumw.chargebaby.ui.navi.CustomTrafficBarViewActivity;
import com.liumw.chargebaby.ui.navi.GPSNaviActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.net.URLEncoder;

/**
 * 设置--关于我们
 */
@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends AppCompatActivity {
    private static final String TAG = "AboutUsActivity";

    @ViewInject(R.id.about_us_back)
    ImageView img_about_us_back;

    @ViewInject(R.id.ll_setting_test)
    LinearLayout ll_setting_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value={R.id.about_us_back,R.id.ll_setting_test},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.about_us_back:
                Log.e(TAG, "退出");
                finish();
                break;
           /* case R.id.setting_fallback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.setting_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;*/
            case R.id.ll_setting_test:
                Log.e(TAG, "导航test");
                LatLng cuLa = new LatLng(39.9264369337,116.6172933578);
                LatLonPoint desLa = new LatLonPoint(39.9110182533,116.5680865764);
                PoiItem desPoiItem = new PoiItem(null, desLa, "测试", null);

                toNavigation(this, desPoiItem, cuLa);
                break;

            default:
                break;
        }
    }

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
