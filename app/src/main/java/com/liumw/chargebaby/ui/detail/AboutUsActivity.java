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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value={R.id.about_us_back},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.about_us_back:
                Log.e(TAG, "退出");
                finish();
                break;

            default:
                break;
        }
    }


}
