package com.liumw.chargebaby.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liumw.chargebaby.R;
import com.tencent.bugly.crashreport.CrashReport;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //配置bugly
        CrashReport.initCrashReport(getApplicationContext());
        setContentView(R.layout.activity_welcome);
        new Handler(new Handler.Callback(){

            @Override
            public boolean handleMessage(Message arg0) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return false;
            }

        }).sendEmptyMessageDelayed(0, 1000*3);
    }
}
