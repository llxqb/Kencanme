package com.shushan.kencanme.entity.response;
/**
 * 长传联系方式时使用
 */

import android.os.Parcel;
import android.os.Parcelable;

public  class ContactWay2 implements Parcelable{
    public String name;
    public String email;


    protected ContactWay2(Parcel in) {
        name = in.readString();
        email = in.readString();
    }

    public ContactWay2() {
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

    public static final Creator<ContactWay2> CREATOR = new Creator<ContactWay2>() {
        @Override
        public ContactWay2 createFromParcel(Parcel in) {
            return new ContactWay2(in);
        }

        @Override
        public ContactWay2[] newArray(int size) {
            return new ContactWay2[size];
        }
    };
}
