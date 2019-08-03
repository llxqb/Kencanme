package com.shushan.kencanme.app.entity.response;

import java.util.List;

public class ReportUserListResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"id":1,"reason":"Fake Profile or Photo Data"},{"id":2,"reason":"Advertise 、Promotion"},{"id":3,"reason":"Fraud"},{"id":4,"reason":"Vulgar or Pornographic Contant"},{"id":5,"reason":"Malicious Harassment"},{"id":6,"reason":"Others"}]
     */

    private int error;
    private String msg;
    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * reason : Fake Profile or Photo Data
         */

        private int id;
        private String reason;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
