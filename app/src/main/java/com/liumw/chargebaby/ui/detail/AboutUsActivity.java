package com.liumw.chargebaby.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.liumw.chargebaby.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 设置--关于我们
 */
@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends AppCompatActivity {

    @ViewInject(R.id.about_us_back)
    ImageView img_about_us_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value={R.id.about_us_back},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.about_us_back:
                finish();
                break;
           /* case R.id.setting_fallback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;
            case R.id.setting_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;*/


            default:
                break;
        }
    }
}
