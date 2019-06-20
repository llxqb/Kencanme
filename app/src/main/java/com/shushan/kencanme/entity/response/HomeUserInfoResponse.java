package com.shushan.kencanme.entity.response;

/**
 * 首页用户信息
 * 和 用户信息是分开来的
 */
public class HomeUserInfoResponse {

    /**
     * user : {"vip":0,"svip":1,"sex":2,"beans":25,"exposure":5,"exposure_type":0,"exposure_time":0,"now_time":1111111,"today_like":0,"today_chat":0,"today_see_contact":0,"nickname":"Meong kucing","trait":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559020915007&di=2bc7b18d1cc9ea420d0190e2b1e7e033&imgtype=0&src=http://image.uc.cn/o/wemedia/s/upload/2019/85230ab04aec373d557a2a3b4a849e39.png;,3,jpegx;3,310x"}
     * contact : {"svip_num":10,"vip_num":20,"num":50}
     * message : {"num":20}
     */

    private UserBean user;
    private ContactBean contact;
    private MessageBean message;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class UserBean {
        /**
         * vip : 0
         * svip : 1
         * sex : 2
         * beans : 25
         * exposure : 5
         * exposure_type : 0
         * exposure_time : 0
         * now_time : 1111111
         * today_like : 0
         * today_chat : 0
         * today_see_contact : 0
         * nickname : Meong kucing
         * trait : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559020915007&di=2bc7b18d1cc9ea420d0190e2b1e7e033&imgtype=0&src=http://image.uc.cn/o/wemedia/s/upload/2019/85230ab04aec373d557a2a3b4a849e39.png;,3,jpegx;3,310x
         */

        private int vip;
        private int svip;
        private int sex;
        private int beans;
        private int exposure;
        private int exposure_type;
        private int exposure_time;
        private int now_time;
        private int today_like;
        private int today_chat;
        private int today_see_contact;
        private String nickname;
        private String trait;

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

        public int getNow_time() {
            return now_time;
        }

        public void setNow_time(int now_time) {
            this.now_time = now_time;
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
    }

    public static class ContactBean {
        /**
         * svip_num : 10
         * vip_num : 20
         * num : 5
         */

        private int svip_num;
        private int vip_num;
        private int num;

        public int getSvip_num() {
            return svip_num;
        }

        public void setSvip_num(int svip_num) {
            this.svip_num = svip_num;
        }

        public int getVip_num() {
            return vip_num;
        }

        public void setVip_num(int vip_num) {
            this.vip_num = vip_num;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }

    public static class MessageBean {
        /**
         * num : 20
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
