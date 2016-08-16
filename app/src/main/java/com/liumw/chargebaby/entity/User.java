package com.liumw.chargebaby.entity;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/26 0026.
 * Email:   1879358765@qq.com
 */
public class User {

    private Long id;
    /**创建时间*/
    private Date createTime = new Date();
    /**修改时间*/
    private Date updateTime = new Date();
    /**用户名*/
    private String username;
    /**真实姓名*/
    private String realName;
    /**手机*/
    private String phone;
    /**email*/
    private String email;
    /**头像*/
    private String headUrl;

}
