package com.liumw.chargebaby.ui.navi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.enums.PathPlanningStrategy;
import com.amap.api.navi.model.NaviLatLng;
import com.liumw.chargebaby.R;

/**
 * 创建时间：11/11/15 18:50
 * 项目名称：newNaviDemo
 *
 * @author lingxiang.wang
 * @email lingxiang.wang@alibaba-inc.com
 * 类说明：
 */
public class GPSNaviActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取导航的起止地址（获取起始地址仅仅是为了，导航播报的第一句准确）
        LatLng startLatLng = getIntent().getParcelableExtra("point_start");
        LatLng endLatLng = getIntent().getParcelableExtra("point_end");
        if (startLatLng == null || endLatLng == null) {
            Toast.makeText(this, "数据传递错误,请稍后再试~", Toast.LENGTH_LONG).show();
            this.finish();
            return;
        }
        //对导航起止地址进行赋值
        this.mStartLatlng = new NaviLatLng(startLatLng.latitude, startLatLng.longitude);
        this.mEndLatlng = new NaviLatLng(endLatLng.latitude, endLatLng.longitude);


        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        mStartList.clear();
        noStartCalculate();
      /*  findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FinishActivity.exitApplication(GPSNaviActivity.this);

            }
        });*/

    }

    /**
     * 如果使用无起点算路，请这样写
     */
    private void noStartCalculate() {
        //无起点算路须知：
        //AMapNavi在构造的时候，会startGPS，但是GPS启动需要一定时间
        //在刚构造好AMapNavi类之后立刻进行无起点算路，会立刻返回false
        //给人造成一种等待很久，依然没有算路成功 算路失败回调的错觉
        //因此，建议，提前获得AMapNavi对象实例，并判断GPS是否准备就绪


        if (mAMapNavi.isGpsReady())
            mAMapNavi.calculateDriveRoute(mEndList, mWayPointList, PathPlanningStrategy.DRIVING_DEFAULT);
    }


    @Override
    public void onCalculateRouteSuccess() {
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
