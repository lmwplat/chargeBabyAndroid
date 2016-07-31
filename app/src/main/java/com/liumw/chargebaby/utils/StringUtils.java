package com.liumw.chargebaby.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by Administrator on 2016/7/31 0031.
 * Email:   1879358765@qq.com
 */
public class StringUtils {
    public static String formateString(String str, String... params) {
        for (int i = 0; i < params.length; i++) {
            str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
        }
        return str;
    }


    public static String[] str2Arr(String str,String tag) {
        if (ValidateUtils.isValid(str)) {
            return str.split(tag);
        }
        return null;
    }


    public static boolean contains(String[] values, String value) {
        if(ValidateUtils.isValid(values)){
            for(String s : values){
                if(s.equals(value)){
                    return true ;
                }
            }
        }
        return false;
    }


    public static String arr2Str(Object[] arr) {
        String temp = "" ;
        if(ValidateUtils.isValid(arr)){
            for(Object s : arr){
                temp = temp + s + "," ;
            }
            return temp.substring(0,temp.length() - 1);
        }
        return temp;
    }

    /**
     * 检查字符串是否为null或空值
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        if(null == str || "".equals(str) || "".equals(str.trim())){
            return true;
        }
        return false;
    }

    /**
     * 检查字符串是否为邮箱格式
     * @param email
     * @author liuhaibo
     * @return
     */
    public static boolean isEmail(String email){
        if(isEmpty(email))return false;
        boolean bool = true;
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            bool = false;
        }
        return bool;
    }

    /**
     * 检查字符串是否为数值类型
     * @param number
     * @author liuhaibo
     * @return
     */
    public static boolean isNumeric(String number){
        if(number.matches("//d*")){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 判断参数是否为NULL
     *
     * @param params
     * @return
     */
    public static boolean isNull(String... params) {
        if (params == null || params.length == 0) {
            return true;
        }
        for (String obj : params) {
            if (obj == null || "".equals(obj) || "".equals(obj.trim())) {
                return true;
            }
        }
        return false;
    }

}
