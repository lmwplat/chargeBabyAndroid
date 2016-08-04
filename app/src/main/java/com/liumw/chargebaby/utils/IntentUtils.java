/**
 * Copyright(c)2012 Beijing PeaceMap Co. Ltd.
 * All right reserved. 
 */
package com.liumw.chargebaby.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.widget.Toast;

/**
 * 网络状态检查
 *  Created by liumw on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class IntentUtils {

	public static interface Type{
		String _WIFI = "WIFI";
		String _GPRS = "GPRS";
		String _CMWAP = "CMWAP";
	}
	
	/**
	 * ��⵱ǰʱ���Ƿ��п�������
	 * 
	 * @param context
	 * @return ����true����ǰ�п������� ����false����ǰ�޿�������
	 */
	public static boolean isConnect(Context context) {
		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// ��ȡ�������ӹ���Ķ���
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// �жϵ�ǰ�����Ƿ��Ѿ�����
					if (info.getState() == State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * ��ȡ��������
	 * @param mContext
	 * @return
	 */
	public static String getNetType(Context mContext){
	      ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);  
    	  State stategprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
    	  State statewifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
    	  if(State.CONNECTED==statewifi && State.CONNECTED==stategprs){
    		  return Type._WIFI;
    	  }
    	  if(State.CONNECTED!=stategprs  && State.CONNECTED==statewifi){
    		  return Type._WIFI;
    	  }
    	  if(State.CONNECTED!=statewifi  && State.CONNECTED==stategprs){
    		  Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
        	  try {
        		  ContentResolver cr = mContext.getContentResolver();
        	      Cursor cursor = cr.query(PREFERRED_APN_URI,
        	      new String[] { "_id", "apn", "type" }, null, null, null);
        	      cursor.moveToFirst();
        	      if (cursor.isAfterLast()) {
        	    	  return Type._GPRS;
        	      }
        	      String apn = cursor.getString(1);
        	      if (apn.toUpperCase().equals("CMWAP")){
        	    	  return Type._CMWAP;
        	      }else if (apn.toUpperCase().equals("CMNET")){
        	    	  return Type._GPRS;
        	      }else{
        	    	  return Type._GPRS;
        	      }
       	     } catch (Exception ep) {
       	    	 ep.printStackTrace();
       	     }
    	  }
    	  return Type._GPRS;
	}
	
	/**
	 * ��ȡ��ǰ�汾��ʾ��
	 * @param mContext
	 * @return
	 */
	public static int getCurrentVersionCode(Context mContext){
		try {
			return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * ��ȡ��ǰ�汾��
	 * @param mContext
	 * @return
	 */
	public static String getCurrentVersionName(Context mContext){
		try {
			return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * ����Ƿ����sd��
	 * @param mContext
	 * @return
	 */
	public static boolean checkSoftStage(Context mContext){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  //�ж��Ƿ����SD��
			return true;
		}else{
			Toast.makeText(mContext, "��⵽�ֻ�û�д洢��,�밲װ���ڴ濨����������", Toast.LENGTH_LONG).show();
			return false;
		}
	}
	/**
	 * ���URL��ַ�Ƿ���Ч
	 * @param url
	 * @return
	 */
	public static boolean checkURL(String url){
		try {
			URL u = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection)u.openConnection();
			urlConn.connect();
			if(urlConn.getResponseCode()==HttpsURLConnection.HTTP_OK){
				return true;
			}
        } catch (Exception e) {
        	e.printStackTrace();
        	return false;
		}
        return false;
	}
	
	/* �ж��Ƿ�Ϊ����*/
	public static String isNaN(String msg){
		Pattern pattern = Pattern.compile("^[+-]?\\d*[.]?\\d*$");  
		Matcher isNum = pattern.matcher(msg);  
		if(isNum.matches()){  
			return Double.parseDouble(msg)+"";
		}else{
			return "no";
		}
	}
}
