package com.shushan.kencanme.entity.response;

import java.io.Serializable;

/**
 * 个人资料
 */
public class PersonalInfoResponse implements Serializable{

    /**
     * uid : 6
     * token : cfae8f9b35c519f5057f3874dc8569ec
     * trait :
     * nickname : yyy
     * sex : 1
     * birthday : 2010/06/12
     * city : yyyyy
     * declaration : yyyyy
     * cover : https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190612/5d0053fa8a97c.png
     * rongyun_token : 25prL11p5U0R1KfmYIh2CWRxbNxvsDT+04ag2atgcEOthE/5zE9BKpVW0IuO+6AyQxTCwGXOnGF/gIRZTisWrw==
     * rongyun_third_id : Kencanme6
     */

    private int uid;
    private String token;
    private String trait;
    private String nickname;
    private int sex;
    private String birthday;
    private String city;
    private String declaration;
    private String cover;
    private String rongyun_token;
    private String rongyun_third_id;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRongyun_token() {
        return rongyun_token;
    }

    public void setRongyun_token(String rongyun_token) {
        this.rongyun_token = rongyun_token;
    }

    public String getRongyun_third_id() {
        return rongyun_third_id;
    }

    public void setRongyun_third_id(String rongyun_third_id) {
        this.rongyun_third_id = rongyun_third_id;
    }
}
