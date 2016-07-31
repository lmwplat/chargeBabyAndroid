package com.liumw.chargebaby.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.db.DBManager;
import com.liumw.chargebaby.entity.User;
import com.liumw.chargebaby.ui.fragment.HomeFragment;
import com.liumw.chargebaby.ui.fragment.MyFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "MainActivity";
    /**是否登录*/
    public static final Boolean IS_LOGIN = false;
    //底部tab
    @ViewInject(R.id.rg_main_bottom_tabs)
    private RadioGroup group;

    //底部tab，按钮-附近
    @ViewInject(R.id.rb_main_home)
    private RadioButton mainHome;

    //底部tab，按钮-我的
    @ViewInject(R.id.rb_main_my)
    private RadioButton mainMy;

    private FragmentManager fragmentManager;//管理fragment
    private HomeFragment home;
    private MyFragment my;
    private long exitTime=0;//两次按返回退出

    User user = null;

    public DBManager dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        //首次执行导入.db文件
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        dbHelper.closeDatabase();

        //初始化fragmentManager
        fragmentManager=getSupportFragmentManager();
        //设置默认选中
        mainHome.setChecked(true);
        group.setOnCheckedChangeListener(this);
        //切换不同的fragment
        changeFragment(0);

    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_home:
                changeFragment(0);
                break;
            case R.id.rb_main_my:
                changeFragment(1);
                break;
            default:
                break;
        }
    }
    //切换不同的fragment
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示home，1表示tuan，2表示search，3表示my。
     */
    public void changeFragment(int index)//同时保存每个fragment
    {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        hideFragments(beginTransaction);
        switch (index) {
            case 0:
                if(home==null){
                    home=new HomeFragment();
                    beginTransaction.add(R.id.main_content,	home);
                }else{
                    beginTransaction.show(home);
                }
                break;
            case 1:
                if(my==null){
                    my=new MyFragment();
                    beginTransaction.add(R.id.main_content,	my);
                }else{
                    beginTransaction.show(my);
                }
                break;

            default:
                break;
        }
        beginTransaction.commit();//需要提交事务
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (home != null)
            transaction.hide(home);
        if (my != null)
            transaction.hide(my);
    }

    @Override
    public void onBackPressed() {
        exit();  ///退出应用
    }

    public void exit() {   //退出应用
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            //System.exit(0);
        }
    }
    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        //	this.overridePendingTransition(0,R.anim.activity_close);
    }


}

