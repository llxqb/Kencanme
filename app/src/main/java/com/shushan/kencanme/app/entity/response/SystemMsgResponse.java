package com.shushan.kencanme.app.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SystemMsgResponse {

    /**
     * error : 0
     * msg : sukses
     * data : [{"msg_id":1,"uid":1,"title":"系统消息","detail":"这是一条系统消息","create_time":1559533531,"state":0}]
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

    public static class DataBean implements Parcelable {
        /**
         * msg_id : 1
         * uid : 1
         * title : 系统消息
         * detail : 这是一条系统消息
         * create_time : 1559533531
         * state : 0
         */

        private int msg_id;
        private int uid;
        private String title;
        private String detail;
        private int create_time;
        private int state;

        protected DataBean(Parcel in) {
            msg_id = in.readInt();
            uid = in.readInt();
            title = in.readString();
            detail = in.readString();
            create_time = in.readInt();
            state = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(msg_id);
            dest.writeInt(uid);
            dest.writeString(title);
            dest.writeString(detail);
            dest.writeInt(create_time);
            dest.writeInt(state);
        }
    }
}

