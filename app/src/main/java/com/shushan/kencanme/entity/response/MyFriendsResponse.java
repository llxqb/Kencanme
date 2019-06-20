package com.shushan.kencanme.entity.response;

import java.util.List;

public class MyFriendsResponse {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * nickname : Caroline
         * trait : https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/48375204_2427683587259406_7703629921096040448_n.jpg?_nc_cat=109&_nc_eui2=AeEDHOcMbaS7YMGme3bqHsyqHuXwrj-z8QUbux5Rg3mZ23R1WQksI0GCrnkV7l1pPQEH7OrRDPiS4mm2pWPxiswlXrjzNAw74revFDr4-uJRPw&_nc_ht=scontent-hkg3-1.xx
         * sex : 2
         * age : 26
         * create_time : 1560405374
         * rongyun_userid : Kencanme505
         * uid : 1
         */

        private String nickname;
        private String trait;
        private int sex;
        private int age;
        private int create_time;
        private String rongyun_userid;
        private int uid;
        public boolean isLike;

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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getRongyun_userid() {
            return rongyun_userid;
        }

        public void setRongyun_userid(String rongyun_userid) {
            this.rongyun_userid = rongyun_userid;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
