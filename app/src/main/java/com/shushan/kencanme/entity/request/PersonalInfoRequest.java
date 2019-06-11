package com.shushan.kencanme.entity.request;

public class PersonalInfoRequest {
    public String token;
    public String nickname;
    //封面
    public String cover;
    //公告
    public String declaration;
    //特点
    public String trait;
    public String height;
    public String weight;
    public String bust;
    //职业
    public String occupation;
    //生日
    public String birthday;
    public String city;
    //联系方式
    public String contact;
    //
    public String label;
    /**
     * 最小推送年龄
     */
    public String pushing_small_age;
    /**
     * 最大推送年龄
     */
    public String pushing_large_age;
    /**
     * 推送性别 0不限1男2女
     */
    public String pushing_gender;
    /**
     * 性别 1男2女
     */
    public String sex;



}
