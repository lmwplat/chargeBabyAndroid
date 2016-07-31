package com.liumw.chargebaby.utils;

import java.util.Collection;

/**
 * 校验工具
 * Created by Administrator on 2016/7/31 0031.
 * Email:   1879358765@qq.com
 */
public class ValidateUtils {
    /**
     * 判断字符串有效性
     */
    public static boolean isValid(String src) {
        if(src == null || "".equals(src.trim())){
            return false;
        }
        return true;
    }

    /**
     * 判断集合的有效性
     */
    public static boolean isValid(Collection col) {
        if (col == null || col.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 判断数组是否有效
     */
    public static boolean isValid(Object[] arr){
        if(arr == null || arr.length == 0){
            return false ;
        }
        return true ;
    }
}
