package com.shushan.kencanme.entity.user;

import java.io.Serializable;

/**
 * Created by li.liu on 2018/1/29.
 * 保存登陆信息
 * sp保存对象必须序列化
 */

public class LoginUser  implements Serializable {

    public int uid;
    public String nickname;
    //头像
    public String trait;
    //封面
    public String cover;
    //1男2女
    public int sex;
    public String birthday;
    public String city;
    //交友宣言
    public String declaration;
    //0 非vip 1 vip
    public int vip;
    public int vip_time;
    //0 非svip 1 svip svip没有到期时间
    public int svip;
    public int height;
    public int weight;
    public String bust;
    public String occupation;
    //嗨豆数
    public int beans;
    public String token;
    public int age;
    public int forbidden;
    public String pushing_age;
    /**
     * 最小推送年龄
     */
    public String pushing_small_age;
    /**
     * 最大推送年龄
     */
    public String pushing_large_age;
    //推送性别   推送性别 0不限1男2女
    public int pushing_gender;
    //曝光
    public int exposure;
    public int last_login_time;

}
