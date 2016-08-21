package com.liumw.chargebaby.dao;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.liumw.chargebaby.R;
import com.liumw.chargebaby.base.AppConstants;
import com.liumw.chargebaby.entity.CommentVo;
import com.liumw.chargebaby.utils.LoginInfoUtils;
import com.liumw.chargebaby.vo.Favorite;
import com.liumw.chargebaby.vo.Json;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论管理
 * Created by liumw on 2016/8/18 0018.
 */
public class CommentDao {
    static Json json = new Json();


    /**
     * 获取所有评论
     * @param chargeNo
     * @return
     */
    public static List<CommentVo> findAllComments(String chargeNo) throws Throwable {
        List<CommentVo> commentVos = new ArrayList<CommentVo>();
        String url = AppConstants.SERVER + AppConstants.ACTION_FIND_ALL_COMMENT;
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("chargeNo", chargeNo);
        String result = x.http().postSync(params, String.class);
        json = JSON.parseObject(result, Json.class);
        if (!json.isSuccess()){
            return null;
        }

       commentVos =  (List<CommentVo>) json.getObj();

        return commentVos;

    }
}
