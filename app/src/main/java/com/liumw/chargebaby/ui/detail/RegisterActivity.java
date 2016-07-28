package com.liumw.chargebaby.ui.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.entity.User;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 注册
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";

    @ViewInject(R.id.register_back)
    ImageView img_reg_back; //返回图标
    @ViewInject(R.id.et_register_username)
    TextView et_usr;    //账号输入框
    @ViewInject(R.id.et_register_password1)
    TextView et_pw1;    //密码输入框
    @ViewInject(R.id.et_register_password2)
    TextView et_pw2;    //确认密码输入框
    @ViewInject(R.id.bt_register)
    Button bt_register; //注册按钮

    private String username;
    private String password;
    private User user;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Log.e(TAG, "RegisterActivity.class :: onCreate()");

        sp = getSharedPreferences(Application.SP_FILE_NAME, Context.MODE_PRIVATE);
        et_usr.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (et_usr.getText().toString().length() >= 5) {
                    bt_register.setEnabled(true);
                    Log.e(TAG, "register-set-true");
                } else {
                    bt_register.setEnabled(false);
                    Log.e(TAG, "register-set-false");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Event(value = {R.id.register_back, R.id.bt_register}, type = View.OnClickListener.class)
    private void onClick(View view) {
        //必须为private
        switch (view.getId()) {
            case R.id.register_back:
                Log.e(TAG, "点击了login_back.....");
                finish();
                break;
            case R.id.bt_register:
                Log.e(TAG, "点击了注册按钮");
                //验证两次密码是否一致
                if (vilidate()) {
                    // TODO 信息校验通过，提交到服务器进行注册
                    //UserDao userDao = new UserDao();
                    register(username, password);
                    Toast.makeText(RegisterActivity.this, username + "注册成功", Toast.LENGTH_LONG).show();

                    // 跳转到登录页面
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                ;
                break;

            default:
                break;
        }
    }

    private void register(final String username, String password) {
        String requestUrl = Application.SERVER + Application.ACTION_REGIST;
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("请稍候...");
        RequestParams params = new RequestParams(requestUrl);
        params.addBodyParameter("username", username);
        params.addBodyParameter("password", password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(username, "onSuccess result<<" + result);
                Gson gson = new Gson();//初始化
                user = gson.fromJson(result, User.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                //将登录信息，存入sharedPreference
                sp.edit().putString(Application.LONIN_INFO, result).commit();

                progressDialog.cancel();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError" );
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "onCancelled" );
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished" );
                progressDialog.cancel();
            }
        });
    }

    /**
     * 验证用户数据的合法性
     */
    private Boolean vilidate() {
        username = et_usr.getText().toString().trim();
        password = et_pw1.getText().toString().trim();
        String password2 = et_pw2.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "注册信息填写不完整", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!password.equals(password2)) {
                Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                return true;
            }
        }
    }

}
