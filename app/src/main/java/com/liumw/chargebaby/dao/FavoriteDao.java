package com.liumw.chargebaby.dao;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.base.Application;
import com.liumw.chargebaby.utils.XutilsHttpUtils;
import com.liumw.chargebaby.vo.Favorite;
import com.liumw.chargebaby.vo.Json;

import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 收藏管理
 * Created by liumw on 2016/8/12 0012.
 */
public class FavoriteDao {

    /**
     * 判断该充电桩，是否收藏
     * @param chargeNo
     * @param favoriteList
     * @return
     */
    public static Boolean isFavorite(String chargeNo, List<Favorite> favoriteList){
        for (Favorite favorite:favoriteList) {
            if (favorite.getChargeNo().equals(chargeNo)){
                return true;
            }
        }
        return false;
    }

    /**
     * 添加收藏
     * @return
     */
    public static List<Favorite> addFavorite(Long userId, String chargeNo) throws Throwable {
        Json json = new Json();
        String url = Application.SERVER + Application.ACTION_ADD_FAVORITE;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", String.valueOf(userId));
        params.addBodyParameter("chargeNo", chargeNo);
        String result = x.http().postSync(params, String.class);
        json = JSON.parseObject(result, Json.class);

        if (json == null || !json.isSuccess()){
            return null;
        }else {
            List<Favorite> favoriteList = (List<Favorite>) json.getObj();
            return favoriteList;
        }
    }

    /**
     * 取消收藏
     * @param userId
     * @param chargeNo
     * @return
     */
    public static List<Favorite> removeFavorite(Long userId, String chargeNo) throws Throwable {
        Json json = new Json();
        String url = Application.SERVER + Application.ACTION_REMOVE_FAVORITE;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("userId", String.valueOf(userId));
        params.addBodyParameter("chargeNo", chargeNo);
        String result = x.http().postSync(params, String.class);
        json = JSON.parseObject(result, Json.class);

        if (json == null || !json.isSuccess()){
            return null;
        }else {
            List<Favorite> favoriteList = (List<Favorite>) json.getObj();
            return favoriteList;
        }
    }
}
