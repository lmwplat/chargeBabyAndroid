package com.liumw.chargebaby.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.entity.User;
import com.liumw.chargebaby.ui.navi.CustomTrafficBarViewActivity;
import com.liumw.chargebaby.utils.IntentUtils;
import com.liumw.chargebaby.utils.LoginInfoUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 设置
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";

    @ViewInject(R.id.setting_back)
    ImageView img_setting_back;
    @ViewInject(R.id.setting_feedback)
    TextView tv_setting_fallback;
    @ViewInject(R.id.setting_about_us)
    TextView tv_setting_about_us;

    @ViewInject(R.id.ll_setting_logout)
    private LinearLayout ll_setting_logout;
    @ViewInject(R.id.tv_setting_logout)
    TextView tv_setting_logout;
    @ViewInject(R.id.setting_version)
    TextView setting_version;



    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        String versionName = IntentUtils.getCurrentVersionName(this);
        setting_version.setText(versionName);
        user = LoginInfoUtils.getLoginInfo(this);
        if (user == null){
            ll_setting_logout.setVisibility(View.GONE);
        }else{
            ll_setting_logout.setVisibility(View.VISIBLE);
        }
    }

    @Event(value={R.id.setting_back, R.id.setting_feedback, R.id.setting_about_us, R.id.tv_setting_logout},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_feedback:
                Log.e(TAG, "反馈");
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.setting_about_us:
                Log.e(TAG, "关于我们");
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.tv_setting_logout:
                Log.e(TAG, "退出");
                logout();
                setResult(ChargeConstants.LOGOUT_SUCCESS_RESULT_CODE);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 登出
     */
    private void logout() {
        //未登录，将fragment_my_info 置为gone
        /*ll_fg_my_login.setVisibility(View.VISIBLE);
        ll_fg_my_info.setVisibility(View.GONE);*/
        tv_setting_logout.setVisibility(View.GONE);
        LoginInfoUtils.removeLoginInfo(SettingActivity.this);
     //   Log.e(TAG, "登出清除登录信息 sp" + LoginInfoUtils.getLoginInfo(SettingActivity.this).toString());
    }

}
