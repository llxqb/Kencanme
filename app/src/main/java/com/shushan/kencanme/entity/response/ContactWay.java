package com.shushan.kencanme.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

public  class ContactWay implements Parcelable{
//    public String contactName;
//    public String contactValue;

    /**
     * contactName : google
     * contactValue :
     */

    public String contactName;
    public String contactValue;


    protected ContactWay(Parcel in) {
        contactName = in.readString();
        contactValue = in.readString();
    }

    public ContactWay() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(contactName);
        dest.writeString(contactValue);
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
