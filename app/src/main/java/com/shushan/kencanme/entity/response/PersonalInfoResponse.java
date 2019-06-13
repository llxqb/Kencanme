package com.shushan.kencanme.entity.response;

import java.io.Serializable;
import java.util.List;

/**
 * 个人资料
 */
public class PersonalInfoResponse implements Serializable{


    /**
     * uid : 6
     * nickname : yyy
     * cover : https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190612/5d0053fa8a97c.png
     * sex : 1
     * birthday : 2010/06/12
     * city : yyyyy
     * declaration : yyyyy
     * vip : 0
     * vip_time : 0
     * svip : 0
     * height : 0
     * weight : 0
     * bust :
     * occupation :
     * label : []
     * beans : 0
     * contact : []
     * token : 9a3085d5b9557a308e23fcae7ecfd64c
     * age : 49
     * forbidden : 0
     * pushing_age : 18-30
     * pushing_gender : 2
     * exposure : 0
     * last_login_time : 1560322633
     * trait :
     * album : []
     */

    private int uid;
    private String nickname;
    //封面
    private String cover;
    //1男2女
    private int sex;
    private String birthday;
    private String city;
    //交友宣言
    private String declaration;
    //0 非vip 1vip
    private int vip;
    private int vip_time;
    private int svip;
    private int height;
    private int weight;
    private String bust;
    private String occupation;
    /**
     * 嗨豆数
     */
    private int beans;
    private String token;
    private int age;
    private int forbidden;
    private String pushing_age;
    /**
     * 最小推送年龄
     */
    public String pushing_small_age;
    /**
     * 最大推送年龄
     */
    public String pushing_large_age;
    private int pushing_gender;
    private int exposure;
    private int last_login_time;
    private String trait;
    private List<?> label;
    private List<?> contact;
    private List<?> album;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public int getVip_time() {
        return vip_time;
    }

    public void setVip_time(int vip_time) {
        this.vip_time = vip_time;
    }

    public int getSvip() {
        return svip;
    }

    public void setSvip(int svip) {
        this.svip = svip;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBust() {
        return bust;
    }

    public void setBust(String bust) {
        this.bust = bust;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getBeans() {
        return beans;
    }

    public void setBeans(int beans) {
        this.beans = beans;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getForbidden() {
        return forbidden;
    }

    public void setForbidden(int forbidden) {
        this.forbidden = forbidden;
    }

    public String getPushing_age() {
        return pushing_age;
    }

    public void setPushing_age(String pushing_age) {
        this.pushing_age = pushing_age;
    }

    public int getPushing_gender() {
        return pushing_gender;
    }

    public void setPushing_gender(int pushing_gender) {
        this.pushing_gender = pushing_gender;
    }

    public int getExposure() {
        return exposure;
    }

    public void setExposure(int exposure) {
        this.exposure = exposure;
    }

    public int getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(int last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public List<?> getLabel() {
        return label;
    }

    public void setLabel(List<?> label) {
        this.label = label;
    }

    public List<?> getContact() {
        return contact;
    }

    public void setContact(List<?> contact) {
        this.contact = contact;
    }

    public List<?> getAlbum() {
        return album;
    }

    public void setAlbum(List<?> album) {
        this.album = album;
    }
}
