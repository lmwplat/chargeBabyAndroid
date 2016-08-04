package com.liumw.chargebaby.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.liumw.chargebaby.entity.ApkInfo;

/**
 * 下载管理
 * Created by liumw on 2016/8/4 0004.
 */
public class DownloadManager {

    private Context mContext;

    final static int CHECK_FAIL = 0;
    final static int CHECK_SUCCESS = 1;
    final static int CHECK_NOUPGRADE = 2;
    final static int CHECK_NETFAIL = 3;

    private ApkInfo apkinfo;
    private AlertDialog noticeDialog;    //提示弹出框
    private ProgressDialog progressDialog;

    private boolean isAccord;  //是否主动检查软件升级
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public DownloadManager(Context mContext,boolean isAccord){
        this.mContext = mContext;
        this.isAccord = isAccord;
    }

    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
            switch(msg.what){
                case CHECK_SUCCESS:{
                    showNoticeDialog();
                    break;
                }
                case CHECK_NOUPGRADE:{  //不需要更新
                    if(isAccord) Toast.makeText(mContext, "当前版本是最新版。", Toast.LENGTH_SHORT).show();
                    break;
                }
                case CHECK_NETFAIL:{
                    if(isAccord) Toast.makeText(mContext, "网络连接不正常。", Toast.LENGTH_SHORT).show();
                    break;
                }
                case CHECK_FAIL:{
                    if(isAccord) Toast.makeText(mContext, "从服务器获取更新数据失败。", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        };
    };

    /** 检查下载更新 [apk下载入口] */
    public void checkDownload(){
        if(isAccord) progressDialog = ProgressDialog.show(mContext, "", "请稍后，正在检查更新...");
        new Thread() {
            @Override
            public void run() {
                if(!IntentUtils.isConnect(mContext)){ //检查网络连接是否正常
                    handler.sendEmptyMessage(CHECK_NETFAIL);
                }else if(checkTodayUpdate() || isAccord){//判断今天是否已自动检查过更新 ；如果手动检查更新，直接进入




                    /*String result = HttpRequestUtil.getSourceResult(Const.apkCheckUpdateUrl, null, mContext);
                    try {
                        //从服务器下载数据有中文，所以服务器对数据进行了编码；在这里需要解码
                        result = Escape.unescape(result);
                        JSONObject obj = new JSONObject(result);
                        String apkVersion = obj.getString("apkVersion");
                        int apkCode = obj.getInt("apkVerCode");
                        String apkSize = obj.getString("apkSize");
                        String apkName = obj.getString("apkName");
                        String downloadUrl = obj.getString("apkDownloadUrl");
                        String apkLog = obj.getString("apklog");
                        apkinfo = new ApkInfo(downloadUrl, apkVersion, apkSize, apkCode, apkName, apkLog);
                        if(apkinfo!=null && checkApkVercode()){  //检查版本号
                            alreayCheckTodayUpdate();    //设置今天已经检查过更新
                            handler.sendEmptyMessage(CHECK_SUCCESS);
                        }else{
                            handler.sendEmptyMessage(CHECK_NOUPGRADE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(CHECK_FAIL);
                    }*/
                }
            }
        }.start();
    }
    /* 弹出软件更新提示对话框*/
    private void showNoticeDialog(){
        StringBuffer sb = new StringBuffer();
        sb.append("版本号："+apkinfo.getApkVersion()+"\n")
                .append("文件大小："+apkinfo.getApkSize()+"\n")
                .append("更新日志：\n"+apkinfo.getApkLog());
        Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.drawable.upgrade).setTitle("版本更新").setMessage(sb.toString());
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String apkPath = Const.apkSavepath + apkinfo.getApkName();
                DownloadCallback downCallback = new DownloadInstall(mContext, apkPath, apkinfo.getApkVersion(), apkinfo.getApkCode());
                DownloadAsyncTask request = new DownloadAsyncTask(downCallback);
                request.execute(apkinfo.getDownloadUrl(),apkPath);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);   //设置最顶层Alertdialog
        noticeDialog.show();
    }

    /**
     * 根据日期检查是否需要进行软件升级
     * @throws Exception
     */
    private boolean checkTodayUpdate() {
        SharedPreferences sharedPreference = mContext.getSharedPreferences(UpdateShared.SETTING_UPDATE_APK_INFO, 0);
        String checkDate = sharedPreference.getString(UpdateShared.CHECK_DATE, "");
        String updateDate = sharedPreference.getString(UpdateShared.UPDATE_DATE, "");
        Log.i("---------------checkDate------------", checkDate);
        Log.i("---------------updateDate------------", updateDate);
        if("".equals(checkDate) && "".equals(updateDate)){  //刚安装的新版本，设置详细信息
            int verCode = IntentUtil.getCurrentVersionCode(mContext);
            String versionName = IntentUtil.getCurrentVersionName(mContext);
            String dateStr = sdf.format(new Date());
            sharedPreference.edit().putString(UpdateShared.CHECK_DATE, dateStr)
                    .putString(UpdateShared.UPDATE_DATE, dateStr)
                    .putString(UpdateShared.APK_VERSION, versionName)
                    .putInt(UpdateShared.APK_VERCODE, verCode).commit();
            return true;
        }
        try {
            //判断defaultMinUpdateDay天内不检查升级
            if((new Date().getTime()-sdf.parse(updateDate).getTime())/1000/3600/24<Const.defaultMinUpdateDay){
                return false;
            }else if(checkDate.equalsIgnoreCase(sdf.format(new Date()))){//判断今天是否检查过升级
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 设置今天已经检查过升级
     * @return
     */
    private void alreayCheckTodayUpdate(){
        String date = sdf.format(new Date());
        SharedPreferences sharedPreference = mContext.getSharedPreferences(UpdateShared.SETTING_UPDATE_APK_INFO, 0);
        sharedPreference.edit().putString(UpdateShared.CHECK_DATE, date).commit();
    }
    /**
     * 检查版本是否需要更新
     * @return
     */
    private boolean checkApkVercode(){
        SharedPreferences sharedPreference = mContext.getSharedPreferences(UpdateShared.SETTING_UPDATE_APK_INFO, 0);
        int verCode = sharedPreference.getInt(UpdateShared.APK_VERCODE, 0);
        if(apkinfo.getApkCode()>verCode){
            return true;
        }else{
            return false;
        }
    }

    static interface UpdateShared{
        String SETTING_UPDATE_APK_INFO = "cbt_upgrade_setting";
        String UPDATE_DATE = "updatedate";
        String APK_VERSION = "apkversion";
        String APK_VERCODE = "apkvercode";
        String CHECK_DATE = "checkdate";
    }
}
