package com.shushan.kencanme.app.entity.response;

public class FacebookLoginResponse {
    /**
     * userinfo : {"code":"FXL85R6","token":"92a709447df978d5e2add16c743d0847","trait":"https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=102994681034270&height=50&width=50&ext=1566434933&hash=AeQQ_JuYXn2ROye6","nickname":"李红","rongyun_token":"hA5psZRsHP7z6Cg7UeW4kXoWPWp5cMM/yK8w1Vf/zSeaJ3uxrvXjyDgSRon JE2feY0reG7hqoqby7HpJEzp5/TvuCut/1H7","sex":"","birthday":"","city":"","declaration":"","cover":"","uid":"1261","rongyun_third_id":"Kencanme1261"}
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
         * code : FXL85R6
         * token : 92a709447df978d5e2add16c743d0847
         * trait : https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=102994681034270&height=50&width=50&ext=1566434933&hash=AeQQ_JuYXn2ROye6
         * nickname : 李红
         * rongyun_token : hA5psZRsHP7z6Cg7UeW4kXoWPWp5cMM/yK8w1Vf/zSeaJ3uxrvXjyDgSRon JE2feY0reG7hqoqby7HpJEzp5/TvuCut/1H7
         * sex :
         * birthday :
         * city :
         * declaration :
         * cover :
         * uid : 1261
         * rongyun_third_id : Kencanme1261
         */

        private String code;
        private String token;
        private String trait;
        private String nickname;
        private String rongyun_token;
        private String sex;
        private String birthday;
        private String city;
        private String declaration;
        private String cover;
        private String uid;
        private String rongyun_third_id;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getRongyun_token() {
            return rongyun_token;
        }

        public void setRongyun_token(String rongyun_token) {
            this.rongyun_token = rongyun_token;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getRongyun_third_id() {
            return rongyun_third_id;
        }

        public void setRongyun_third_id(String rongyun_third_id) {
            this.rongyun_third_id = rongyun_third_id;
        }
    }
}
