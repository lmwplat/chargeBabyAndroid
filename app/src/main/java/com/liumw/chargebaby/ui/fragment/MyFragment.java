package com.liumw.chargebaby.ui.fragment;

import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.liumw.chargebaby.R;
import com.liumw.chargebaby.ui.detail.LoginActivity;
import com.liumw.chargebaby.ui.detail.SettingActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 我的
 */
@ContentView(R.layout.fragment_my)
public class MyFragment extends Fragment{
    @ViewInject(R.id.index_my_Login_touxiang)
    ImageView touxiang;
    @ViewInject(R.id.index_my_Login_click)
    TextView tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Event(type = View.OnClickListener.class,value = R.id.index_my_Login_click)
    private void loginActityOnClick(View view){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    @Event(type = View.OnClickListener.class,value = R.id.my_setting)
    private void settingActityOnClick(View view){
        startActivity(new Intent(getActivity(), SettingActivity.class));
    }
}
