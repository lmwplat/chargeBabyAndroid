package com.liumw.chargebaby.ui.indicate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liumw.chargebaby.utils.LoginInfoUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;
import com.liumw.chargebaby.R;
import cn.sharesdk.framework.ShareSDK;

@ContentView(R.layout.fragment_detail)
public class DetailFragment extends Fragment {
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
}
