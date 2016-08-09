package com.liumw.chargebaby.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.liumw.chargebaby.R;
import com.liumw.chargebaby.db.DBManager;
import com.liumw.chargebaby.entity.Charge;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_charge_detail)
public class ChargeDetailActivity extends AppCompatActivity {
    private static final String TAG = "ChargeDetailActivity";

    private String chargeNo;
    private Double distance;
    private Charge charge;

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



    //数据库处理
    DbManager.DaoConfig daoConfig= DBManager.getDaoConfig();
    DbManager db = x.getDb(daoConfig);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        Intent i =getIntent();
        chargeNo = i.getStringExtra("chargeNo");
        distance = i.getDoubleExtra("distance", -1);
        Log.i(TAG, "chargeNo :" + chargeNo + "distance :" + String.valueOf(distance));

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

    @Event(value={R.id.charge_detail_back},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.charge_detail_back:
                Log.e(TAG, "退出");
                finish();
                break;

            default:
                break;
        }
    }


}
