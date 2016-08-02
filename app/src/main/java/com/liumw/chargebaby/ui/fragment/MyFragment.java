package com.liumw.chargebaby.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.entity.User;
import com.liumw.chargebaby.ui.detail.LoginActivity;
import com.liumw.chargebaby.ui.detail.SettingActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的
 */
@ContentView(R.layout.fragment_my)
public class MyFragment extends Fragment{
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.index_my_Login_touxiang)
    ImageView touxiang;
    @ViewInject(R.id.index_my_Login_click)
    TextView tv;
    @ViewInject(R.id.index_my_info_click)
    TextView tv_my_info;


    private SharedPreferences sp;
    private  User user;

    @ViewInject(R.id.ll_fg_my_login)
    private LinearLayout ll_fg_my_login;

    @ViewInject(R.id.ll_fg_my_info)
    private LinearLayout ll_fg_my_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //从共享参数获取数据
        sp = this.getActivity().getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
        String str = sp.getString(Application.LONIN_INFO, null);
        Log.e(TAG, "从sp中获取" + str);
        if (str == null || str.equals("null")){
            //未登录，将fragment_my_info 置为gone
            ll_fg_my_login.setVisibility(View.VISIBLE);
            ll_fg_my_info.setVisibility(View.GONE);

        }else{
            user = JSON.parseObject(str, User.class);
            //已经登录，将fragment_my_login 置为gone
            ll_fg_my_login.setVisibility(View.GONE);
            ll_fg_my_info.setVisibility(View.VISIBLE);
            tv_my_info.setText(user.getUsername());

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChargeConstants.LOGIN_REGISTER_REQUEST_CODE && resultCode == ChargeConstants.REGISTER_SUCCESS_RESULT_CODE) {
            //已经登录，将fragment_my_login 置为gone
            ll_fg_my_login.setVisibility(View.GONE);
            ll_fg_my_info.setVisibility(View.VISIBLE);
            tv_my_info.setText(data.getStringExtra("username"));
            sp = this.getActivity().getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
            String str = sp.getString(Application.LONIN_INFO, null);
            Log.e(TAG, "从sp中获取" + str);
            return;
        }

        if (requestCode == ChargeConstants.SETTING_LOGOUT_REQUEST_CODE && resultCode == ChargeConstants.LOGOUT_SUCCESS_RESULT_CODE) {
            //已经登出，将fragment_my_login 置为gone
            ll_fg_my_login.setVisibility(View.VISIBLE);
            ll_fg_my_info.setVisibility(View.GONE);
            sp = this.getActivity().getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
            String str = sp.getString(Application.LONIN_INFO, null);
            Log.e(TAG, "从sp中获取" + str);
            return;
        }
    }

    @Event(type = View.OnClickListener.class,value = R.id.index_my_Login_click)
    private void loginActityOnClick(View view){
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), ChargeConstants.LOGIN_REGISTER_REQUEST_CODE);
    }

    @Event(type = View.OnClickListener.class,value = R.id.my_setting)
    private void settingActityOnClick(View view){
        startActivityForResult(new Intent(getActivity(), SettingActivity.class), ChargeConstants.SETTING_LOGOUT_REQUEST_CODE);
    }
}
