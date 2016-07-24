package com.liumw.chargebaby.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.liumw.chargebaby.R;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 注册
 */
@ContentView(R.layout.activity_register)
public class RegisterActivity extends Activity {

    @ViewInject(R.id.register_back)
    ImageView img_reg_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }

    @Event(value={R.id.register_back},type=View.OnClickListener.class)
    private void onClick(View view){
        //必须为private
        switch (view.getId()) {
            case R.id.register_back:
                LogUtil.e("点击了login_back.....");
                finish();
                break;
          /*  case R.id.login_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;*/

            default:
                break;
        }
    }

}
