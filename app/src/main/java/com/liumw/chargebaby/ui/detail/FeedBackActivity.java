package com.liumw.chargebaby.ui.detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.entity.User;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Json;
import com.liumw.chargebaby.vo.UserInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 设置--意见反馈
 */
@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends AppCompatActivity {
    private static final String TAG = "FeedBackActivity";

    @ViewInject(R.id.feedback_back)
    ImageView img_feedback_back;
    @ViewInject(R.id.feedback_content)
    EditText feedback_content;
    @ViewInject(R.id.feedback_confirm)
    Button bt_feedback_confirm;
    private Json json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        Log.i(TAG, "FeedBackActivity.class :: onCreate()");

        feedback_content.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                if (feedback_content.getText().toString().length() >= 10) {
                    bt_feedback_confirm.setEnabled(true);
                    Log.e(TAG, "feedback-set-true");
                } else {
                    bt_feedback_confirm.setEnabled(false);
                    Log.e(TAG, "feedback-set-false");
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

    @Event(value={R.id.feedback_back, R.id.feedback_confirm},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.feedback_back:
                finish();
                break;
            case R.id.feedback_confirm:
                String str = feedback_content.getText().toString();
                feedbackConfirm(str);
                break;
            default:
                break;
        }
    }

    /**
     * 提交意见反馈
     * @param content
     */
    private void feedbackConfirm(String content) {
        UserInfo userInfo = LoginInfoUtils.getLoginInfo(FeedBackActivity.this);
        String username =null;
        if (userInfo != null){
            username = userInfo.getUsername();
        }
        String requestUrl = Application.SERVER + Application.ACTION_FEEDBACK_CONFIRM;
        final ProgressDialog progressDialog = new ProgressDialog(FeedBackActivity.this);
        progressDialog.setMessage("请稍候...");
        RequestParams params = new RequestParams(requestUrl);
        params.addBodyParameter("username", username);
        params.addBodyParameter("info", content);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e(TAG, "onSuccess result<<" + result);
                json = JSON.parseObject(result, Json.class);//result为请求后返回的JSON数据,可以直接使用XUtils获得,NewsData.class为一个bean.如以下数据：
                if (!json.isSuccess()){
                    Log.e(TAG, "提交失败" + json.getMsg());
                    Toast.makeText(FeedBackActivity.this,  "提交失败" + json.getMsg(), Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(FeedBackActivity.this,  "提交成功", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(FeedBackActivity.this, "提交失败", Toast.LENGTH_LONG).show();
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
