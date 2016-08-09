package com.liumw.chargebaby.ui.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.entity.BDMapData;
import com.liumw.chargebaby.ui.detail.ChargeDetailActivity;
import com.liumw.chargebaby.ui.navi.GPSNaviActivity;

import org.xutils.view.annotation.ViewInject;

import java.net.URLEncoder;

public class SelectPicPopupWindow extends PopupWindow  {

	private static final String TAG = "SelectPicPopupWindow";
	//private Button btn_take_photo, btn_pick_photo, btn_cancel;
	private View mMenuView;


	public SelectPicPopupWindow(final Activity context, final Object object) {
		super(context);
		final BDMapData bdMapData = (BDMapData)object;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popwindow_main, null);
		LinearLayout ll_pop_navi = (LinearLayout) mMenuView
				.findViewById(R.id.ll_pop_navi);

		TextView tv_pop_address = (TextView) mMenuView
				.findViewById(R.id.tv_pop_address);

		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}
				return true;
			}
		});

		ll_pop_navi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "导航");

				LatLng cuLa = new LatLng(bdMapData.getMyLatitude(),bdMapData.getMyLongitude());
				//LatLonPoint desLa = new LatLonPoint(39.9110182533,116.5680865764);
				LatLonPoint desLa = new LatLonPoint(bdMapData.getLatitude(),bdMapData.getLongitude());
				PoiItem desPoiItem = new PoiItem(null, desLa, "测试", null);

				toNavigation(context, desPoiItem, cuLa);
			}
		});


		tv_pop_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "充电点详情");
				Intent intent = new Intent(context, ChargeDetailActivity.class);
				intent.putExtra("distance", bdMapData.getDistance());
				intent.putExtra("chargeNo", bdMapData.getChargeNo());
				context.startActivity(intent);
			}
		});

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
