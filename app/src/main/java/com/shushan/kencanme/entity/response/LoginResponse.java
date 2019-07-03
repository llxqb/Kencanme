package com.shushan.kencanme.entity.response;

/**
 * ClassName: LoginResponse
 * author: li.liu
 * date: 2019-05-28
 */
public class LoginResponse {


    /**
     * userinfo : {"uid":932,"token":"def585fea7483596b58d7edfb013d67f","trait":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/trait/20190628/5d1589a714384.png","nickname":"bee359","sex":2,"birthday":"669960449","city":"hhkkk","declaration":"hjkkk","cover":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190627/5d14412eaaf1f.png","code":"APUYG90","rongyun_token":"XRVfejvZJ5Pna8D5zgK4DXoWPWp5cMM/yK8w1Vf/zSfuVrMyufPPHstrj8Fa5CL1eY0reG7hqoqrUg1PPjxZY2h2AJSwo9r0","rongyun_third_id":"Kencanme932"}
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
         * uid : 932
         * token : def585fea7483596b58d7edfb013d67f
         * trait : https://menggoda.oss-ap-southeast-5.aliyuncs.com/trait/20190628/5d1589a714384.png
         * nickname : bee359
         * sex : 2
         * birthday : 669960449
         * city : hhkkk
         * declaration : hjkkk
         * cover : https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190627/5d14412eaaf1f.png
         * code : APUYG90
         * rongyun_token : XRVfejvZJ5Pna8D5zgK4DXoWPWp5cMM/yK8w1Vf/zSfuVrMyufPPHstrj8Fa5CL1eY0reG7hqoqrUg1PPjxZY2h2AJSwo9r0
         * rongyun_third_id : Kencanme932
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
        private String code;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
