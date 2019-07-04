package com.shushan.kencanme.app.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Desci: //增加、更新我的相册 返回 Response
 */
public class MyAlbumResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"id":6,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190613/5d02066162657.png","album_type":1,"cost":0},{"id":7,"album_url":"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190613/5d020b7c814df.png","album_type":2,"cost":0}]
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

    public static class DataBean implements Parcelable{
        /**
         * id : 6
         * album_url : https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190613/5d02066162657.png
         * album_type : 1
         * cost : 0
         */

        private int id;
        private String album_url;
        private int album_type;
        private int cost;
        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            id = in.readInt();
            album_url = in.readString();
            album_type = in.readInt();
            cost = in.readInt();
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAlbum_url() {
            return album_url;
        }

        public void setAlbum_url(String album_url) {
            this.album_url = album_url;
        }

        public int getAlbum_type() {
            return album_type;
        }

        public void setAlbum_type(int album_type) {
            this.album_type = album_type;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(album_url);
            dest.writeInt(album_type);
            dest.writeInt(cost);
            dest.writeInt(state);
        }
    }
}
