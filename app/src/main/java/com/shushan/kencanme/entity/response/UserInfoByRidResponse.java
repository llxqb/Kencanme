package com.shushan.kencanme.entity.response;

public class UserInfoByRidResponse {

    /**
     * nickname : Aron Carter
     * trait : https://menggoda.oss-ap-southeast-5.aliyuncs.com/trait/20190612/5d00935e3fea7.png
     * uid : 7
     */

    private String nickname;
    private String trait;
    private int uid;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
