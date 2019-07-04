package com.shushan.kencanme.app.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

public  class ContactWay implements Parcelable{
    public String name;
    public String email;
    /**
     * 是否显示可以查看
     */
    public boolean isShow = false;


    protected ContactWay(Parcel in) {
        name = in.readString();
        email = in.readString();
    }


    public ContactWay() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactWay> CREATOR = new Creator<ContactWay>() {
        @Override
        public ContactWay createFromParcel(Parcel in) {
            return new ContactWay(in);
        }

        @Override
        public ContactWay[] newArray(int size) {
            return new ContactWay[size];
        }
    };
}
