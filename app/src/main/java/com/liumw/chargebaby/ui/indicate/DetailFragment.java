package com.liumw.chargebaby.ui.indicate;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.liumw.chargebaby.db.DBManager;
import com.liumw.chargebaby.entity.Charge;
import com.liumw.chargebaby.utils.LoginInfoUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.utils.NaviUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.sharesdk.framework.ShareSDK;

@ContentView(R.layout.fragment_detail)
public class DetailFragment extends Fragment implements AMapLocationListener {
    private static final String TAG = "DetailFragment";


    @ViewInject(R.id.tv_charge_detail_ac_builded)
    TextView tv_charge_detail_ac_builded;
    @ViewInject(R.id.tv_charge_detail_ac_building)
    TextView tv_charge_detail_ac_building;
    @ViewInject(R.id.tv_charge_detail_dc_builded)
    TextView tv_charge_detail_dc_builded;
    @ViewInject(R.id.tv_charge_detail_dc_building)
    TextView tv_charge_detail_dc_building;
    @ViewInject(R.id.tv_charge_detail_begin_time)
    TextView tv_charge_detail_begin_time;
    @ViewInject(R.id.tv_charge_detail_tel)
    TextView tv_charge_detail_tel;
    @ViewInject(R.id.tv_charge_detail_standard_name)
    TextView tv_charge_detail_standard_name;
    @ViewInject(R.id.tv_charge_detail_fee_standard)
    TextView tv_charge_detail_fee_standard;
    @ViewInject(R.id.tv_charge_detail_detail)
    TextView tv_charge_detail_detail;
    @ViewInject(R.id.tv_charge_detail_depart)
    TextView tv_charge_detail_depart;
    @ViewInject(R.id.ll_charge_detail_navi)
    LinearLayout ll_charge_detail_navi;

    private String chargeNo;
    private Charge charge;

    private Double myLatitude;
    private Double myLongitude;

    private AMapLocationClientOption mLocationOption = null;
    private AMapLocationClient mLocationClient;
    private LocationSource.OnLocationChangedListener mListener;

    private boolean isFirstLoc = true;

    //数据库处理
    DbManager.DaoConfig daoConfig= DBManager.getDaoConfig();
    DbManager db = x.getDb(daoConfig);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(getActivity());
        chargeNo = ((IndicatorFragmentActivity)getActivity()).getChargeNo();
        Log.i(TAG, "charge -----" + chargeNo);

        try {
            charge = db.selector(Charge.class).where("charge_no","=",chargeNo).findFirst();
            if(charge != null){
                Log.i(TAG, "charge :" + charge.toString());
                tv_charge_detail_ac_builded.setText(charge.getAcBuilded() != null ? String.valueOf(charge.getAcBuilded()) : "");
                tv_charge_detail_ac_building.setText(charge.getAcBuilding() != null ? String.valueOf(charge.getAcBuilding()) : "");
                tv_charge_detail_dc_builded.setText(charge.getDcBuilded() != null ? String.valueOf(charge.getDcBuilded()) : "");
                tv_charge_detail_dc_building.setText(charge.getDcBuilding() != null ? String.valueOf(charge.getDcBuilding()) : "");
                tv_charge_detail_begin_time.setText(charge.getBeginTime()!= null ? charge.getBeginTime() : "");
                tv_charge_detail_tel.setText(charge.getTel()!= null ? charge.getTel() : "");
                tv_charge_detail_standard_name.setText(charge.getStandardName()!= null ? charge.getStandardName() : "");
                tv_charge_detail_fee_standard.setText(charge.getFeeStandard()!= null ? charge.getFeeStandard() : "");
                tv_charge_detail_detail.setText(charge.getDetail()!= null ? charge.getDetail() : "");
                tv_charge_detail_depart.setText(charge.getDepart()!= null ? charge.getDepart() : "");

            }
        } catch (DbException e) {
            e.printStackTrace();
            Log.e(TAG, "充电桩详情获取数据库错误" + e.getMessage());
        }
    }

    private void init(Context context) {
        //声明mLocationOption对象
        mLocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mLocationClient.startLocation();

    }



    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                myLatitude =  amapLocation.getLatitude();//获取纬度
                myLongitude = amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Event(type = View.OnClickListener.class,value = R.id.ll_charge_detail_navi)
    private void commentSubmitOnClick(View view){
        Log.i(TAG, "导航");

        LatLng cuLa = new LatLng(myLatitude, myLongitude);
        //LatLonPoint desLa = new LatLonPoint(39.9110182533,116.5680865764);
        LatLonPoint desLa = new LatLonPoint(charge.getLatitude(),charge.getLongitude());
        PoiItem desPoiItem = new PoiItem(null, desLa, "测试", null);
        NaviUtils.toNavigation(getActivity(), desPoiItem, cuLa);


    }



}
