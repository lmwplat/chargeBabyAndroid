package com.liumw.chargebaby.ui.popwindow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.ChargeApplication;
import com.liumw.chargebaby.dao.FavoriteDao;
import com.liumw.chargebaby.entity.BDMapData;
import com.liumw.chargebaby.ui.detail.ChargeDetailActivity;
import com.liumw.chargebaby.ui.detail.LoginActivity;
import com.liumw.chargebaby.ui.detail.ChargeDetailDianpinActivity;
import com.liumw.chargebaby.ui.indicate.IndicatorFragmentActivity;
import com.liumw.chargebaby.ui.navi.GPSNaviActivity;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.utils.NaviUtils;
import com.liumw.chargebaby.vo.Favorite;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.x;

import java.net.URLEncoder;
import java.util.List;

public class SelectPicPopupWindow extends PopupWindow  {

	private static final String TAG = "SelectPicPopupWindow";
	private View mMenuView;
	private SharedPreferences sp;
	private Boolean isFavorited = false;
	ImageView iv_my_favorite;
	final BDMapData bdMapData;
	UserInfo userInfo;
	private ChargeApplication app;

	public SelectPicPopupWindow(final Activity context, final Object object) {
		super(context);
		bdMapData = (BDMapData)object;
		final LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.popwindow_main, null);
		LinearLayout ll_pop_navi = (LinearLayout) mMenuView
				.findViewById(R.id.ll_pop_navi);

		LinearLayout ll_pop_dianpin = (LinearLayout) mMenuView.findViewById(R.id.ll_pop_dianpin);

		LinearLayout tv_pop_main_detail = (LinearLayout) mMenuView
				.findViewById(R.id.tv_pop_main_detail);

		iv_my_favorite = (ImageView) mMenuView.findViewById(R.id.iv_my_favorite);

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

		userInfo = LoginInfoUtils.getLoginInfo(context);
		if (userInfo == null){
			//未登录，iv_my_favorite 置为gone
			iv_my_favorite.setImageResource(R.mipmap.my_favorite_write);

		}else{
			//已经登录，
			if (FavoriteDao.isFavorite(bdMapData.getChargeNo(), userInfo.getFavoriteList())){
				iv_my_favorite.setImageResource(R.mipmap.my_favorite_red);
				isFavorited = true;
			}else {
				iv_my_favorite.setImageResource(R.mipmap.my_favorite_write);
			}
		}
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

				NaviUtils.toNavigation(context, desPoiItem, cuLa);
			}
		});


		tv_pop_main_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "充电点详情");
				Intent intent = new Intent(context, IndicatorFragmentActivity.class);
				intent.putExtra("distance", bdMapData.getDistance());
				intent.putExtra("chargeNo", bdMapData.getChargeNo());
				intent.putExtra("name", bdMapData.getName());
				intent.putExtra("address", bdMapData.getAddress());
				intent.putExtra("isFavorited", isFavorited);
				dismiss();
				context.startActivity(intent);
			}
		});
		ll_pop_dianpin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "充电点点评");
				userInfo = LoginInfoUtils.getLoginInfo(context);
				if (userInfo == null){
					//未登录，跳转登录页面
					context.startActivity(new Intent(context, LoginActivity.class));
				}else{
					Intent intent = new Intent(context, IndicatorFragmentActivity.class);
					intent.putExtra("distance", bdMapData.getDistance());
					intent.putExtra("chargeNo", bdMapData.getChargeNo());
					intent.putExtra("name", bdMapData.getName());
					intent.putExtra("address", bdMapData.getAddress());
					intent.putExtra("isFavorited", isFavorited);
					dismiss();
					context.startActivity(intent);
				}


			}
		});

		iv_my_favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				userInfo = LoginInfoUtils.getLoginInfo(context);
				if (userInfo == null){
					//未登录，跳转登录页面
					context.startActivity(new Intent(context, LoginActivity.class));
				}else{
					if (isFavorited){
						//已经收藏，取消收藏

						// 启动线程执行下载任务
						new Thread(removeFavoriteRun).start();


					}else {
						//还未收藏，添加收藏
						new Thread(addFavoriteRun).start();


					}
				}


			}
		});

	}




	/**
	 * 取消收藏线程
	 */
	Runnable removeFavoriteRun = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<Favorite> favoriteList = null;
			try {

				favoriteList = FavoriteDao.removeFavorite(userInfo.getId(), bdMapData.getChargeNo());
				if (favoriteList != null){
					userInfo.setFavoriteList(favoriteList);
					LoginInfoUtils.setLoginInfo(getContentView().getContext(), JSON.toJSONString(userInfo));
					iv_my_favorite.post(new Runnable(){

						@Override
						public void run() {
							iv_my_favorite.setImageResource(R.mipmap.my_favorite_write);
							isFavorited = false;
							Toast.makeText(x.app(), "取消收藏成功", Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	};

	/**
	 * 添加收藏
	 */
	Runnable addFavoriteRun = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<Favorite> favoriteList = null;
			try {
				favoriteList = FavoriteDao.addFavorite(userInfo.getId(), bdMapData.getChargeNo());
				if (favoriteList != null){
					userInfo.setFavoriteList(favoriteList);
					LoginInfoUtils.setLoginInfo(getContentView().getContext(), JSON.toJSONString(userInfo));
					iv_my_favorite.post(new Runnable(){

						@Override
						public void run() {
							iv_my_favorite.setImageResource(R.mipmap.my_favorite_red);
							isFavorited = true;
							Toast.makeText(x.app(), "收藏成功", Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	};
}
