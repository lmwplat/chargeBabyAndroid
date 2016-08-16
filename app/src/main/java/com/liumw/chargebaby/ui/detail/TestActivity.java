package com.liumw.chargebaby.ui.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liumw.chargebaby.R;
import com.liumw.chargebaby.ui.indicate.DetailFragment;
import com.liumw.chargebaby.ui.indicate.DianpinFragment;
import com.liumw.chargebaby.ui.indicate.FragmentOne;
import com.liumw.chargebaby.ui.indicate.FragmentTwo;
import com.liumw.chargebaby.ui.indicate.IndicatorFragmentActivity;

import java.util.List;

public class TestActivity extends IndicatorFragmentActivity {

    public static final int FRAGMENT_DETAIL = 0;
    public static final int FRAGMENT_DIANPIN = 1;
    public static final int FRAGMENT_THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_DETAIL, getString(R.string.fragment_one),
                DetailFragment.class));
        tabs.add(new TabInfo(FRAGMENT_DIANPIN, getString(R.string.fragment_two),
                DianpinFragment.class));


        return FRAGMENT_DETAIL;
    }
}
