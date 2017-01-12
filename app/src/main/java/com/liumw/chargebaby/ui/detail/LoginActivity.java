package com.liumw.chargebaby.ui.detail;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.liumw.chargebaby.Event.LoginEvent;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.AppConstants;
import com.liumw.chargebaby.base.ChargeApplication;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.ui.fragment.MyFragment;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Json;
import com.liumw.chargebaby.vo.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.MD5;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends Activity {
    private static final String TAG = "LoginActivity";

    @ViewInject(R.id.login_back)
    ImageView img_back;
    @ViewInject(R.id.login_register)
    TextView tv_register;
    @ViewInject(R.id.login_forget_password)
    TextView tv_forget_password;
    @ViewInject(R.id.login)
    Button bt_login;
    @ViewInject(R.id.et_login_username)
    EditText et_login_username; //用户名输入框
    @ViewInject(R.id.et_login_password)
    EditText et_login_password; //密码输入框


    //    User user = null;
    private Json json;
    private ChargeApplication app;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        et_login_password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (et_login_password.getText().toString().length() >= 6) {
                    bt_login.setEnabled(true);
                    Log.e(TAG, "register-set-true");
                } else {
                    bt_login.setEnabled(false);
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Event(value = {R.id.login_back, R.id.login_register, R.id.login_forget_password, R.id.login}, type = View.OnClickListener.class)
    private void onClick(View view) {

        //必须为private
        switch (view.getId()) {
            case R.id.login_back:
                LogUtil.e("点击了login_back.....");
                finish();
                break;
            case R.id.login_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), ChargeConstants.LOGIN_REGISTER_REQUEST_CODE);
                break;
            case R.id.login_forget_password:
                startActivityForResult(new Intent(this, ForgetPasswordActivity.class), ChargeConstants.LOGIN_REGISTER_REQUEST_CODE);
                break;
            case R.id.login:
                //TODO 信息校验通过，提交到服务器进行登录认证
                String username = et_login_username.getText().toString();
                String password = et_login_password.getText().toString();
                String md5Pass = MD5.md5(password);
                login(username, md5Pass);
                break;

            default:
                break;
        }
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    private void login(final String username, String password) {
        String requestUrl = AppConstants.SERVER + AppConstants.ACTION_LOGIN;
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("请稍候...");
        RequestParams params = new RequestParams(requestUrl);
        params.addBodyParameter("username", username);
        params.addBodyParameter("password", password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(username, "onSuccess result<<" + result);
                json = JSON.parseObject(result, Json.class);
                if (!json.isSuccess()) {
                    Log.e(TAG, "登录失败" + json.getMsg());
                    Toast.makeText(LoginActivity.this, username + "登录失败", Toast.LENGTH_LONG).show();
                } else {
                    //将登录信息，存入sharedPreference
                    LoginInfoUtils.setLoginInfo(LoginActivity.this, JSON.toJSONString(json.getObj()));
                    UserInfo userInfo = LoginInfoUtils.getLoginInfo(LoginActivity.this);
                    app = (ChargeApplication) getApplication();
                    app.setLoginName(username);
                    app.setUserInfo(userInfo);
                    EventBus.getDefault().post(
                            new LoginEvent(userInfo));

                    Log.i(TAG, "登录时测试从sp中获取" + userInfo.toString());
                    Toast.makeText(LoginActivity.this, username + "登录成功", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    // 跳转到登录页面
                    Intent intent = new Intent();
                    intent.putExtra("userInfo", userInfo);
                    setResult(ChargeConstants.REGISTER_SUCCESS_RESULT_CODE, intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(LoginActivity.this, username + "登录失败", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onError");
            }

            @Override
            public void onCancelled(CancelledException cex) {

                Log.e(TAG, "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "onFinished");
                progressDialog.cancel();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChargeConstants.LOGIN_REGISTER_REQUEST_CODE && resultCode == ChargeConstants.REGISTER_SUCCESS_RESULT_CODE) {
            setResult(ChargeConstants.REGISTER_SUCCESS_RESULT_CODE, data);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      /*  client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.liumw.chargebaby.ui.detail/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       /* Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.liumw.chargebaby.ui.detail/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }
}

