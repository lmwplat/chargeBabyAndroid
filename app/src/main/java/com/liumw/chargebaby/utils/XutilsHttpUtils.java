package com.liumw.chargebaby.utils;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.vo.Json;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;
/**
 * Created by liumw on 2016/8/13 0013.
 */
public class XutilsHttpUtils {
    static Json json = new Json();

    /**
     * 带缓存的网络请求方式
     *
     * @param method
     * @param params
     * @return
     */
    public static Json CacheUrl(HttpMethod method, RequestParams params) {

        params.setCacheMaxAge(1000 * 30);//缓存的时间,毫秒为单位

        x.http().request(method, params, new Callback.CacheCallback<String>() {
            Json json = new Json();
            @Override
            public boolean onCache(String result) {
                json = JSON.parseObject(result, Json.class);
                return true; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
            }

            @Override
            public void onSuccess(String result) {
                //如果服务返回304或 onCache 选择了信任缓存, 这里将不会被调用
                json = JSON.parseObject(result, Json.class);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                json = null;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                if (json != null) {
                    Toast.makeText(x.app(), "网络请求成功", Toast.LENGTH_LONG).show();
                }
            }
        });

        return json;
    }


    /**
     * 不带缓存的网络请求方式
     *
     * @param method
     * @param params
     * @return
     */
    public static Json NoCacheUrl(HttpMethod method, RequestParams params) {


        x.http().request(method, params, new Callback.CommonCallback<String>() {
            Json json = new Json();
            @Override
            public void onSuccess(String result) {
                json = JSON.parseObject(result, Json.class);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                json = null;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
                if (json != null) {
                    Toast.makeText(x.app(), "网络请求成功", Toast.LENGTH_LONG).show();
                }
            }
        });

        return json;
    }
}
