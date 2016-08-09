package com.liumw.chargebaby.vo;

import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liumw on 2016/8/8 0008.
 */
public class Data {
    private static AbstractMap<String, Object> mData = new ConcurrentHashMap<String, Object>();

    private Data() {

    }

    public static void putData(String key,Object obj) {
        mData.put(key, obj);
    }

    public static Object getData(String key) {
        return mData.get(key);
    }
}
