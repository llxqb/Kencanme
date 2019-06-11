package com.shushan.kencanme.entity.response;

/**
 * ClassName: LoginResponse
 * author: li.liu
 * date: 2019-05-28
 */
public class LoginResponse {


    /**
     * userinfo : {"uid":6,"token":"6cda1757d2284313dba2696da301b445","trait":"","nickname":"","sex":1,"birthday":"","city":"","declaration":"","rongyun_token":"GoMAWtA2Lpma1NegEbv/eGRxbNxvsDT+04ag2atgcEOyMknjltF9Htk80pE8O5UoQxTCwGXOnGF/gIRZTisWrw==","rongyun_third_id":"Kencanme6"}
     */

    private UserinfoBean userinfo;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * uid : 6
         * token : 6cda1757d2284313dba2696da301b445
         * trait :
         * nickname :
         * sex : 1
         * birthday :
         * city :
         * declaration :
         * rongyun_token : GoMAWtA2Lpma1NegEbv/eGRxbNxvsDT+04ag2atgcEOyMknjltF9Htk80pE8O5UoQxTCwGXOnGF/gIRZTisWrw==
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
}
