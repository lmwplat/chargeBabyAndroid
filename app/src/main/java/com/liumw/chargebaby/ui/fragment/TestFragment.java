package com.liumw.chargebaby.ui.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.GroundOverlay;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MarkerOptionsCreator;
import com.liumw.chargebaby.R;

import org.xutils.view.annotation.ContentView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_test)
public class TestFragment extends Fragment implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener {

    private MapView mapView;

    private AMap aMap;

    private AMapLocationClientOption mLocationOption = null;

    private AMapLocationClient mLocationClient;

    private OnLocationChangedListener mListener;

    private boolean isFirstLoc = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(getActivity());

        Marker marker = aMap.addMarker(createMarkOptions(39.936713, 116.386475, "测试1", "测试1内容", R.mipmap.ic_launcher));
        marker.showInfoWindow();

        aMap.addMarker(createMarkOptions(39.536713, 116.386475, "测试2", null, 0));

        aMap.addMarker(createMarkOptions(39.136713, 116.386475, "测试3", "测试3", R.mipmap.icon_marka));

        aMap.setOnMarkerClickListener(this);
    }

    private void init(Context context) {
        aMap = mapView.getMap();
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        isFirstLoc = true;
        int i = 0;
    }

    @Override
    public void deactivate() {
        mListener = null;
        int i = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        int i = 0;
        Log.i("tag", aMapLocation.getErrorInfo());
        Log.i("tag", aMapLocation.getErrorCode() + "");

        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(aMapLocation);
                    //添加图钉
                    //aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    //Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();

                    //aMap.addGroundOverlay(createGroundOverlayOptions(R.mipmap.ic_launcher, 39.936713, 116.386475));
                    isFirstLoc = false;
                }


            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                //Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }


    }

    private GroundOverlayOptions createGroundOverlayOptions(int imgRes, double x, double y) {
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(x - 0.002048, y - 0.002048))
                .build();
        GroundOverlayOptions options = new GroundOverlayOptions();
        options.anchor(0.5f, 0.5f).transparency(0.1f);
        options.image(BitmapDescriptorFactory.fromResource(imgRes)).positionFromBounds(bounds);
        return  options;
    }

    /**
     * 显示标记
     * @param x         坐标
     * @param y         坐标
     * @param title     点击标记显示的标题
     * @param snippet   副标题
     * @param imgRes    要展示图片
     * @return
     */
    private MarkerOptions createMarkOptions(double x, double y, String title, String snippet, int imgRes) {
        MarkerOptions options = new MarkerOptions();
        if(!TextUtils.isEmpty(title)) {
            options.title(title);
        }
        if(!TextUtils.isEmpty(snippet)) {
            options.snippet(snippet);
        }
        if(imgRes > 0) {
            options.icon(BitmapDescriptorFactory.fromResource(imgRes));
        }
        options.position(new LatLng(x, y));
        return options;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return false;
    }
}
