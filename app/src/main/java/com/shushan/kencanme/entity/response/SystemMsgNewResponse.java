package com.shushan.kencanme.entity.response;

/**
 * 新匹配和最新系统消息
 */
public class SystemMsgNewResponse {
    /**
     * new_message : {"msg_id":1,"uid":1,"title":"系统消息","detail":"这是一条系统消息","create_time":1559533531,"state":0,"count":1}
     * new_like : {"count":1,"trait":"https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/48375204_2427683587259406_7703629921096040448_n.jpg?_nc_cat=109&_nc_eui2=AeEDHOcMbaS7YMGme3bqHsyqHuXwrj-z8QUbux5Rg3mZ23R1WQksI0GCrnkV7l1pPQEH7OrRDPiS4mm2pWPxiswlXrjzNAw74revFDr4-uJRPw&_nc_ht=scontent-hkg3-1.xx"}
     */

    private NewMessageBean new_message;
    private NewLikeBean new_like;

    public NewMessageBean getNew_message() {
        return new_message;
    }

    public void setNew_message(NewMessageBean new_message) {
        this.new_message = new_message;
    }

    public NewLikeBean getNew_like() {
        return new_like;
    }

    public void setNew_like(NewLikeBean new_like) {
        this.new_like = new_like;
    }

    public static class NewMessageBean {
        /**
         * msg_id : 1
         * uid : 1
         * title : 系统消息
         * detail : 这是一条系统消息
         * create_time : 1559533531
         * state : 0
         * count : 1
         */

        private int msg_id;
        private int uid;
        private String title;
        private String detail;
        private int create_time;
        private int state;
        private int count;

        public int getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(int msg_id) {
            this.msg_id = msg_id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class NewLikeBean {
        /**
         * count : 1
         * trait : https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/48375204_2427683587259406_7703629921096040448_n.jpg?_nc_cat=109&_nc_eui2=AeEDHOcMbaS7YMGme3bqHsyqHuXwrj-z8QUbux5Rg3mZ23R1WQksI0GCrnkV7l1pPQEH7OrRDPiS4mm2pWPxiswlXrjzNAw74revFDr4-uJRPw&_nc_ht=scontent-hkg3-1.xx
         */

        private int count;
        private String trait;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTrait() {
            return trait;
        }

        public void setTrait(String trait) {
            this.trait = trait;
        }
    }
}
