package com.liumw.chargebaby.ui.detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.AppConstants;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Json;

import org.xutils.common.Callback;
import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 忘记密码
 */
@ContentView(R.layout.activity_forget_password)
public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ForgetPasswordActivity";

    @ViewInject(R.id.forget_password_back)
    ImageView img_forget_password_back;
    @ViewInject(R.id.et_forget_password_username)
    TextView et_usr;    //账号输入框
    @ViewInject(R.id.et_forget_password_password1)
    TextView et_pw1;    //密码输入框
    @ViewInject(R.id.et_forget_password_password2)
    TextView et_pw2;    //确认密码输入框
    @ViewInject(R.id.bt_forget_password_confirm)
    Button bt_forget_password; //确认按钮

    private String username;
    private String password;
    private String password2;

    private Json json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        et_pw2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (et_pw2.getText().toString().length() >= 6) {
                    bt_forget_password.setEnabled(true);
                    Log.e(TAG, "confirm-set-true");
                } else {
                    bt_forget_password.setEnabled(false);
                    Log.e(TAG, "confirm-set-false");
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

    @Event(value={R.id.forget_password_back, R.id.bt_forget_password_confirm},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.forget_password_back:
                finish();
                break;
            case R.id.bt_forget_password_confirm:
                Log.e(TAG, "点击了注册按钮");
                username = et_usr.getText().toString().trim();
                password = et_pw1.getText().toString().trim();
                password2 = et_pw2.getText().toString().trim();
                //验证两次密码是否一致
                if (vilidate()) {
                    // TODO 信息校验通过，提交到服务器进行注册
                    String md5Pass = MD5.md5(password);
                    forgetPasswordConfirm(username, md5Pass);
                }
                ;
                break;

            default:
                break;
        }
    }

    /**
     * 确认提交密码
     * @param user
     * @param pass
     */
    private void forgetPasswordConfirm(final String user, String pass) {
        String requestUrl = AppConstants.SERVER + AppConstants.ACTION_CONFIRM;
        final ProgressDialog progressDialog = new ProgressDialog(ForgetPasswordActivity.this);
        progressDialog.setMessage("请稍候...");
        RequestParams params = new RequestParams(requestUrl);
        params.addBodyParameter("username", user);
        params.addBodyParameter("password", pass);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess result<<" + result);
                json = JSON.parseObject(result, Json.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                if (!json.isSuccess()){

                    Log.e(TAG, "密码修改失败" + json.getMsg());
                    Toast.makeText(ForgetPasswordActivity.this,  "密码修改失败" + json.getMsg(), Toast.LENGTH_LONG).show();
                }else{
                    //将登录信息，存入sharedPreference
                    LoginInfoUtils.setLoginInfo(ForgetPasswordActivity.this, JSON.toJSONString(json.getObj()));

                    Toast.makeText(ForgetPasswordActivity.this, username + "密码修改成功", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    // 跳转到登录页面
                    Intent intent=new Intent();
                    intent.putExtra("username", user);
                    setResult(ChargeConstants.REGISTER_SUCCESS_RESULT_CODE, intent);

                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(ForgetPasswordActivity.this, username + "密码修改失败", Toast.LENGTH_LONG).show();
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

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            Toast.makeText(this, "信息填写不完整", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6){
            Toast.makeText(this, "密码长度不足6位", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
