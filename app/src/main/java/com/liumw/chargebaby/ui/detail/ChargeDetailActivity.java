package com.liumw.chargebaby.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.dao.FavoriteDao;
import com.liumw.chargebaby.db.DBManager;
import com.liumw.chargebaby.entity.Charge;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Favorite;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_charge_detail)
public class ChargeDetailActivity extends AppCompatActivity {
    private static final String TAG = "ChargeDetailActivity";

    private String chargeNo;
    private Double distance;
    private Charge charge;
    private Boolean isFavorited;

    @ViewInject(R.id.charge_detail_back)
    ImageView charge_detail_back;
    @ViewInject(R.id.tv_charge_detail_name)
    TextView tv_charge_detail_name;
    @ViewInject(R.id.tv_charge_detail_address)
    TextView tv_charge_detail_address;
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
    @ViewInject(R.id.iv_detail_my_favorite)
    ImageView iv_detail_my_favorite;

    private UserInfo userInfo;



    //数据库处理
    DbManager.DaoConfig daoConfig= DBManager.getDaoConfig();
    DbManager db = x.getDb(daoConfig);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        userInfo = LoginInfoUtils.getLoginInfo(this);
        Intent i =getIntent();
        chargeNo = i.getStringExtra("chargeNo");
        distance = i.getDoubleExtra("distance", -1);
        isFavorited = i.getBooleanExtra("isFavorited", false);
        Log.i(TAG, "chargeNo :" + chargeNo + "distance :" + String.valueOf(distance));

        //如果已收藏，将收藏点亮
        if (isFavorited){
            iv_detail_my_favorite.setImageResource(R.mipmap.my_favorite_red);
        }

        try {
            charge = db.selector(Charge.class).where("charge_no","=",chargeNo).findFirst();
            if(charge != null){
                Log.i(TAG, "charge :" + charge.toString());
                tv_charge_detail_name.setText(charge.getName()!= null ? charge.getName() : "");
                tv_charge_detail_address.setText(charge.getAddress()!= null ? charge.getAddress() : "");
                tv_charge_detail_ac_builded.setText(charge.getAcBuilded() != null ? String.valueOf(charge.getAcBuilded()) : "");
                tv_charge_detail_ac_building.setText(charge.getAcBuilding() != null ? String.valueOf(charge.getAcBuilding()) : "");
                tv_charge_detail_dc_builded.setText(charge.getDcBuilded() != null ? String.valueOf(charge.getDcBuilded()) : "");
                tv_charge_detail_dc_building.setText(charge.getDcBuilding() != null ? String.valueOf(charge.getDcBuilding()) : "");
                tv_charge_detail_begin_time.setText(charge.getBeginTime()!= null ? charge.getAddress() : "");
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



    @Event(value={R.id.charge_detail_back, R.id.iv_detail_my_favorite},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.charge_detail_back:
                Log.i(TAG, "退出");
                finish();

                break;

            case R.id.iv_detail_my_favorite:
                if (userInfo == null){
                    //未登录，跳转登录页面
                    startActivity(new Intent(this, LoginActivity.class));
                }else{
                    if (isFavorited){
                        //已经收藏，取消收藏
                        // 启动线程执行取消收藏
                        new Thread(removeFavoriteRun).start();
                    }else {
                        //还未收藏，添加收藏
                        new Thread(addFavoriteRun).start();
                    }
                }
                break;

            default:
                break;
        }
    }
    /**
     * 添加收藏线程
     */
    Runnable removeFavoriteRun = new Runnable(){

        @Override
        public void run() {
            // TODO Auto-generated method stub
            List<Favorite> favoriteList = null;
            try {

                favoriteList = FavoriteDao.removeFavorite(userInfo.getId(), chargeNo);
                if (favoriteList != null){
                    userInfo.setFavoriteList(favoriteList);
                    LoginInfoUtils.setLoginInfo(ChargeDetailActivity.this, JSON.toJSONString(userInfo));
                    iv_detail_my_favorite.post(new Runnable(){

                        @Override
                        public void run() {
                            iv_detail_my_favorite.setImageResource(R.mipmap.my_favorite_write);
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
     * 下载线程
     */
    Runnable addFavoriteRun = new Runnable(){

        @Override
        public void run() {
            // TODO Auto-generated method stub
            List<Favorite> favoriteList = null;
            try {
                favoriteList = FavoriteDao.addFavorite(userInfo.getId(), chargeNo);
                if (favoriteList != null){
                    userInfo.setFavoriteList(favoriteList);
                    LoginInfoUtils.setLoginInfo(ChargeDetailActivity.this, JSON.toJSONString(userInfo));
                    iv_detail_my_favorite.post(new Runnable(){

                        @Override
                        public void run() {
                            iv_detail_my_favorite.setImageResource(R.mipmap.my_favorite_red);
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
