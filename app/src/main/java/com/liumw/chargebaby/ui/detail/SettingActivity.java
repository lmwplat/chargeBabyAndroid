package com.liumw.chargebaby.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liumw.chargebaby.R;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value={R.id.setting_back, R.id.setting_feedback, R.id.setting_about_us},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.setting_back:
                finish();
                break;
            case R.id.setting_feedback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.setting_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;


            default:
                break;
        }
    }

}
