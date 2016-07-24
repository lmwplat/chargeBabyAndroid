package com.liumw.chargebaby.ui.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.liumw.chargebaby.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 设置--意见反馈
 */
@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends AppCompatActivity {

    @ViewInject(R.id.feedback_back)
    ImageView img_feedback_back;
    @ViewInject(R.id.setting_feedback)
    LinearLayout ll_setting_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value={R.id.feedback_back, R.id.setting_feedback},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.feedback_back:
                finish();
                break;
         /*   case R.id.setting_fallback:
                startActivity(new Intent(this, FeedBackActivity.class));
                break;*/



            default:
                break;
        }
    }
}
