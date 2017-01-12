package com.liumw.chargebaby.ui.detail;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.AppConstants;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Json;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_my_collect)
public class MyCollectActivity extends AppCompatActivity {
    private static final String TAG = "MyCollectActivity";

    @ViewInject(R.id.my_collect_back)
    ImageView my_collect_back;
    @ViewInject(R.id.et_my_collect_content)
    EditText et_my_collect_content;
    @ViewInject(R.id.bt_my_collect_confirm)
    Button bt_my_collect_confirm;

    private Json json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Log.i(TAG, "MyCollectActivity.class :: onCreate()");

        et_my_collect_content.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (et_my_collect_content.getText().toString().length() >= 1) {
                    bt_my_collect_confirm.setEnabled(true);
                    Log.e(TAG, "collect-set-true");
                } else {
                    bt_my_collect_confirm.setEnabled(false);
                    Log.e(TAG, "collect-set-false");
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

    @Event(value={R.id.my_collect_back, R.id.bt_my_collect_confirm},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.my_collect_back:
                finish();
                break;
            case R.id.bt_my_collect_confirm:
                String str = et_my_collect_content.getText().toString();
                collectConfirm(str);
                break;

            default:
                break;
        }
    }

    /**
     * 信息确认
     * @param info
     */
    private void collectConfirm(String info) {

        UserInfo userInfo = LoginInfoUtils.getLoginInfo(MyCollectActivity.this);
        String username =null;
        if (userInfo != null){
            username = userInfo.getUsername();
        }
        String requestUrl = AppConstants.SERVER + AppConstants.ACTION_ADD_COLLECT;
        final ProgressDialog progressDialog = new ProgressDialog(MyCollectActivity.this);
        progressDialog.setMessage("请稍候...");
        RequestParams params = new RequestParams(requestUrl);
        params.addBodyParameter("username", username);
        params.addBodyParameter("info", info);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.e(TAG, "onSuccess result<<" + result);
                json = JSON.parseObject(result, Json.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                if (!json.isSuccess()){
                    Log.e(TAG, "提交失败" + json.getMsg());
                    Toast.makeText(MyCollectActivity.this,  "提交失败" + json.getMsg(), Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(MyCollectActivity.this,  "提交成功", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MyCollectActivity.this, "提交失败", Toast.LENGTH_LONG).show();
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
}
