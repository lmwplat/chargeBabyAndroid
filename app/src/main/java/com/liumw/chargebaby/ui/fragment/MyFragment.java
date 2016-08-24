package com.liumw.chargebaby.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liumw.chargebaby.Event.LoginEvent;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.ChargeApplication;
import com.liumw.chargebaby.base.ChargeConstants;
import com.liumw.chargebaby.ui.detail.LoginActivity;
import com.liumw.chargebaby.ui.detail.MyCollectActivity;
import com.liumw.chargebaby.ui.detail.MyFavoriteActivity;
import com.liumw.chargebaby.ui.detail.SettingActivity;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 我的
 */
@ContentView(R.layout.fragment_my)
public class MyFragment extends Fragment{
    private static final String TAG = "MyFragment";


    @ViewInject(R.id.index_my_Login_touxiang)
    ImageView touxiang;
    @ViewInject(R.id.index_my_Login_click)
    TextView tv;
    @ViewInject(R.id.index_my_info_click)
    TextView tv_my_info;
    @ViewInject(R.id.ll_fg_my_login)
    private LinearLayout ll_fg_my_login;
    @ViewInject(R.id.ll_fg_my_info)
    private LinearLayout ll_fg_my_info;

    UserInfo userInfo;
    private ChargeApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
        ShareSDK.initSDK(getActivity());
        //从共享参数获取数据
        userInfo = LoginInfoUtils.getLoginInfo(getActivity());
        if (userInfo == null){
            //未登录，将fragment_my_info 置为gone
            ll_fg_my_login.setVisibility(View.VISIBLE);
            ll_fg_my_info.setVisibility(View.GONE);

        }else{
            //已经登录，将fragment_my_login 置为gone
            ll_fg_my_login.setVisibility(View.GONE);
            ll_fg_my_info.setVisibility(View.VISIBLE);
            tv_my_info.setText(userInfo.getUsername());

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChargeConstants.LOGIN_REGISTER_REQUEST_CODE && resultCode == ChargeConstants.REGISTER_SUCCESS_RESULT_CODE) {
            //已经登录，将fragment_my_login 置为gone
            userInfo = (UserInfo) data.getSerializableExtra("userInfo");
            ll_fg_my_login.setVisibility(View.GONE);
            ll_fg_my_info.setVisibility(View.VISIBLE);
            tv_my_info.setText(userInfo.getUsername());
            return;
        }

        if (requestCode == ChargeConstants.SETTING_LOGOUT_REQUEST_CODE && resultCode == ChargeConstants.LOGOUT_SUCCESS_RESULT_CODE) {
            //已经登出，将fragment_my_login 置为gone
            ll_fg_my_login.setVisibility(View.VISIBLE);
            ll_fg_my_info.setVisibility(View.GONE);
            userInfo = null;
            return;
        }
    }

    @Event(type = View.OnClickListener.class,value = R.id.index_my_Login_click)
    private void loginActityOnClick(View view){
        app = (ChargeApplication) getActivity().getApplication();
        userInfo = app.getUserInfo();
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), ChargeConstants.LOGIN_REGISTER_REQUEST_CODE);
    }

    @Event(type = View.OnClickListener.class,value = R.id.my_setting)
    private void settingActityOnClick(View view){
        startActivityForResult(new Intent(getActivity(), SettingActivity.class), ChargeConstants.SETTING_LOGOUT_REQUEST_CODE);
    }

    @Event(type = View.OnClickListener.class,value = R.id.ll_my_favorite)
    private void myFavoriteActityOnClick(View view){
        app = (ChargeApplication) getActivity().getApplication();
        userInfo = app.getUserInfo();
        if (userInfo == null){
            startActivityForResult(new Intent(getActivity(), LoginActivity.class), ChargeConstants.LOGIN_REGISTER_REQUEST_CODE);
        }else {
            startActivity(new Intent(getActivity(), MyFavoriteActivity.class));
        }
    }
    @Event(type = View.OnClickListener.class,value = R.id.ll_my_collect)
    private void myCollectActityOnClick(View view){
        app = (ChargeApplication) getActivity().getApplication();
        userInfo = app.getUserInfo();
        startActivityForResult(new Intent(getActivity(), MyCollectActivity.class), ChargeConstants.SETTING_LOGOUT_REQUEST_CODE);
    }
    @Event(type = View.OnClickListener.class,value = R.id.ll_my_share)
    private void myShareActityOnClick(View view){
        showShare();
    }

    private void showShare() {
        ShareSDK.initSDK(getActivity());
       /* OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.ssdk_oks_share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(ChargeConstants.SHARE_URL);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(ChargeConstants.SHARE_TEXT);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(ChargeConstants.SHARE_URL);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(ChargeConstants.SHARE_TEXT);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(ChargeConstants.SHARE_URL);
        // 启动分享GUI
        oks.show(getActivity());*/

        OnekeyShare share = new OnekeyShare();
        share.disableSSOWhenAuthorize();
        share.setText(ChargeConstants.SHARE_TEXT);
        // text是分享文本，所有平台都需要这个字段
        share.setTitle(ChargeConstants.SHARE_TITLE);
        // url仅在微信（包括好友和朋友圈）中使用
        share.setUrl(ChargeConstants.SHARE_URL);
        share.setTitleUrl(ChargeConstants.SHARE_URL);
        share.setImageUrl(ChargeConstants.SHARE_IMAGE_URL);
        share.show(getActivity());
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void onEventMainThread(LoginEvent event) {

        userInfo =  event.getMsg();
        ll_fg_my_login.setVisibility(View.GONE);
        ll_fg_my_info.setVisibility(View.VISIBLE);
        tv_my_info.setText(userInfo.getUsername());
        Log.i(TAG, "onEvent 消息事件");
    }

}
