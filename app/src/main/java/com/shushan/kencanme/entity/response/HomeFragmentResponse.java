package com.shushan.kencanme.entity.response;

import java.util.List;

public class HomeFragmentResponse {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * uid : 6
         * nickname : yuu234
         * trait : https://menggoda.oss-ap-southeast-5.aliyuncs.com/trait/20190613/5d01ba17036ed.png
         * cover : https://menggoda.oss-ap-southeast-5.aliyuncs.com/video/20190613/9ac885d2a646c393ba6bad6c7a8513f3.mp4
         * sex : 2
         * city : yy123
         * last_login_time : 1560859023
         * exposure_time : 1561003697
         * age : 49
         * active_time : 41
         * is_like : 1
         * rongyun_userid : Kencanme6
         */

        private int uid;
        private String nickname;
        private String trait;
        private String cover;
        private int sex;
        private String city;
        private int last_login_time;
        private int exposure_time;
        private int age;
        private int active_time;
        private int is_like;
        private String rongyun_userid;

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

        public String getTrait() {
            return trait;
        }

        public void setTrait(String trait) {
            this.trait = trait;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public int getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(int last_login_time) {
            this.last_login_time = last_login_time;
        }

        public int getExposure_time() {
            return exposure_time;
        }

        public void setExposure_time(int exposure_time) {
            this.exposure_time = exposure_time;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getActive_time() {
            return active_time;
        }

        public void setActive_time(int active_time) {
            this.active_time = active_time;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public String getRongyun_userid() {
            return rongyun_userid;
        }

        public void setRongyun_userid(String rongyun_userid) {
            this.rongyun_userid = rongyun_userid;
        }
    }
}
