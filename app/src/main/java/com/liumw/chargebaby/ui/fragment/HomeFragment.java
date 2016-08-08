package com.liumw.chargebaby.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.db.DBManager;
import com.liumw.chargebaby.entity.BDMapData;
import com.liumw.chargebaby.entity.Charge;
import com.liumw.chargebaby.ui.MainActivity;
import com.liumw.chargebaby.ui.SearchActivity;
import com.liumw.chargebaby.ui.popwindow.SelectPicPopupWindow;
import com.liumw.chargebaby.utils.ToastUtils;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 首页--附近默认
 */
@ContentView(R.layout.fragment_home)
public class HomeFragment extends Fragment implements LocationSource, AMapLocationListener, AMap.OnMarkerClickListener{
    private static final String TAG = "HomeFragment";
    private MapView mapView;

    private AMap aMap;

    private TextView tvSearch;

    private AMapLocationClientOption mLocationOption = null;

    private AMapLocationClient mLocationClient;

    private Double myLatitude;
    private Double myLongitude;

    private LocationSource.OnLocationChangedListener mListener;

    private boolean isFirstLoc = true;

    //自定义的弹出框类
    private SelectPicPopupWindow menuWindow;

    private List<BDMapData> bdMapClientList = new ArrayList<BDMapData>();

    //数据库处理
    DbManager.DaoConfig daoConfig= DBManager.getDaoConfig();
    DbManager db = x.getDb(daoConfig);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mapView = (MapView) view.findViewById(R.id.mapView);
        tvSearch = (TextView) view.findViewById(R.id.tv_search);
        mapView.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(getActivity());

        aMap.setOnMarkerClickListener(this);

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
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
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
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
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
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
                    myLatitude = aMapLocation.getLatitude();
                    myLongitude = aMapLocation.getLongitude();
                    try {
                        addOverlay();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

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

    /**
     * 显示标记
     * @param x         坐标
     * @param y         坐标
     * @param title     点击标记显示的标题
     * @param snippet   副标题
     * @param imgRes    要展示图片
     * @return
     */
    private MarkerOptions createAndMoveMarkOptions(double x, double y, String title, String snippet, int imgRes) {
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
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(x, y)));
        return options;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        Bundle bundle = (Bundle) marker.getObject();
        String popName = bundle.getString("name");
        String popFeeStandard = bundle.getString("feeStandard");
        String popAddress = bundle.getString("address");
        Double popDistance = bundle.getDouble("distance");


        Log.e(TAG, "点击了覆盖物");
        //实例化SelectPicPopupWindow
        menuWindow = new SelectPicPopupWindow(getActivity(), null);
        //显示窗口
        menuWindow.showAtLocation(getActivity().findViewById(R.id.main_content), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置


        TextView tv_pop_name = (TextView) menuWindow.getContentView().findViewById(R.id.pop_name);
        tv_pop_name.setText(popName);
        TextView tv_price = (TextView) menuWindow.getContentView().findViewById(R.id.pop_price);
        tv_price.setText(popFeeStandard);
        TextView tv_address = (TextView) menuWindow.getContentView().findViewById(R.id.pop_address);
        tv_address.setText(popAddress);
        //得到点击的覆盖物的经纬度
//        LatLng ll = marker.getPosition();

        //让地图以备点击的覆盖物为中心
        /*MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.setMapStatus(status);*/
        return true;
    }

    /**
     * 获取附近的所有充电桩
     */
    private void getAllCharge() throws DbException {
        LatLng myLatLng = new LatLng(myLatitude, myLongitude);
        List<Charge> all = db.findAll(Charge.class);
        for(Charge charge : all){
            if (charge.getLatitude() != null && charge.getLongitude() != null){
                LatLng  chargeLat = new LatLng(charge.getLatitude(), charge.getLongitude());
                Float distance = AMapUtils.calculateLineDistance(myLatLng, chargeLat);
                BDMapData bDMapData = new BDMapData();
                bDMapData.setName(charge.getName());
                bDMapData.setAddress(charge.getAddress());
                bDMapData.setDistance(distance);
                bDMapData.setFeeStandard(charge.getFeeStandard());
                bDMapData.setLatitude(charge.getLatitude());
                bDMapData.setLongitude(charge.getLongitude());
                bdMapClientList.add(bDMapData);
            }
        }
    }

    /**
     * 添加覆盖物的方法
     */
    private void addOverlay() throws DbException {
        Marker marker = null;
        LatLng point = null;
        MarkerOptions option = null;
        BitmapDescriptor bitmap =BitmapDescriptorFactory.fromResource(R.mipmap.icon_marka);
        //Marker marker = aMap.addMarker(createMarkOptions(39.936713, 116.386475, "测试1", "测试1内容", R.mipmap.ic_launcher));
        //获取附近的所有充电桩
        getAllCharge();

        for (BDMapData data : bdMapClientList) {
            point = new LatLng(data.getLatitude(), data.getLongitude());
            option = new MarkerOptions().position(point).icon(bitmap);
            marker = aMap.addMarker(option);
            //Bundle用于通信
            Bundle bundle = new Bundle();
            bundle.putString("name", data.getName());
            bundle.putString("feeStandard", data.getFeeStandard());
            bundle.putString("address", data.getAddress());
            bundle.putDouble("distance", data.getDistance());
            marker.setObject(bundle);//将bundle值传入marker中，给baiduMap设置监听时可以得到它
        }
        //将地图移动到最后一个标志点
        /*MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(status);*/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 101:
                Charge item = (Charge) data.getSerializableExtra("data");
                if(item == null) {
                    return;
                }
                /*MarkerOptions options = createAndMoveMarkOptions(item.getLatitude(), item.getLongitude(), item.getName(), item.getAddress(), -1);
                aMap.addMarker(options);*/
                //此处添加标记物或者其他操作

                break;
        }
    }
}
