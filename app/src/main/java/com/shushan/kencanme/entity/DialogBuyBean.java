package com.shushan.kencanme.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Desciption: //bugDialog  bean对象
 * author: li.liu
 * date: 2019-05-30
 */
public class DialogBuyBean implements Parcelable {
    /**
     * error : 0
     * msg : 成功
     * data : [{"num":100,"time":25},{"num":25,"time":5},{"num":6,"time":1}]
     */

    private int error;
    private String msg;
    private List<DataBean> data;

    protected DialogBuyBean(Parcel in) {
        error = in.readInt();
        msg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(error);
        dest.writeString(msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DialogBuyBean> CREATOR = new Creator<DialogBuyBean>() {
        @Override
        public DialogBuyBean createFromParcel(Parcel in) {
            return new DialogBuyBean(in);
        }

        @Override
        public DialogBuyBean[] newArray(int size) {
            return new DialogBuyBean[size];
        }
    };

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
         * num : 100
         * time : 25
         */

        /**
         * money 金钱
         */
        public int num;
        /**
         * 超级曝光次数
         */
        public int time;
        /**
         * 是否被选中
         */
        public boolean isCheck;
        /**
         * 是否火热抢购
         */
        public boolean isHot;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
//    /**
//     * 超级曝光次数
//     */
//    public int time;
//    /**
//     * money 金钱
//     */
//    public int num;
//    /**
//     * 是否被选中
//     */
//    public boolean isCheck;
//    /**
//     * 是否火热抢购
//     */
//    public boolean isHot;


}
