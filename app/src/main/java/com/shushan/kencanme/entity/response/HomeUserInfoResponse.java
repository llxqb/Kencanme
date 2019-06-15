package com.shushan.kencanme.entity.response;

/**
 * 首页用户信息
 * 和 用户信息是分开来的
 */
public class HomeUserInfoResponse {
    /**
     * user : {"vip":0,"svip":0,"sex":1,"beans":0,"exposure":0,"exposure_type":0,"exposure_time":0,"today_like":17,"today_chat":0,"today_see_contact":0}
     */

    private UserBean user;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * vip : 0
         * svip : 0
         * sex : 1
         * beans : 0
         * exposure : 0
         * exposure_type : 0
         * exposure_time : 0
         * today_like : 17
         * today_chat : 0
         * today_see_contact : 0
         */

        private int vip;
        private int svip;
        private int sex;
        private int beans;
        private int exposure;
        private int exposure_type;
        private int exposure_time;
        private int today_like;
        private int today_chat;
        private int today_see_contact;

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public int getSvip() {
            return svip;
        }

        public void setSvip(int svip) {
            this.svip = svip;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getBeans() {
            return beans;
        }

        public void setBeans(int beans) {
            this.beans = beans;
        }

        public int getExposure() {
            return exposure;
        }

        public void setExposure(int exposure) {
            this.exposure = exposure;
        }

        public int getExposure_type() {
            return exposure_type;
        }

        public void setExposure_type(int exposure_type) {
            this.exposure_type = exposure_type;
        }

        public int getExposure_time() {
            return exposure_time;
        }

        public void setExposure_time(int exposure_time) {
            this.exposure_time = exposure_time;
        }

        public int getToday_like() {
            return today_like;
        }

        public void setToday_like(int today_like) {
            this.today_like = today_like;
        }

        public int getToday_chat() {
            return today_chat;
        }

        public void setToday_chat(int today_chat) {
            this.today_chat = today_chat;
        }

        public int getToday_see_contact() {
            return today_see_contact;
        }

        public void setToday_see_contact(int today_see_contact) {
            this.today_see_contact = today_see_contact;
        }
    }
}
