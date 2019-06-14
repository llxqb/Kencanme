package com.shushan.kencanme.entity.request;

import android.os.Parcel;
import android.os.Parcelable;

public class UpdatePersonalInfoRequest implements Parcelable{

    public UpdatePersonalInfoRequest() {

    }
    public String token;
    public String nickname;
    //封面
    public String cover;
    //公告 交友宣言
    public String declaration;
    //头像
    public String trait;
    public String height;
    public String weight;
    public String bust;
    //职业
    public String occupation;
    //生日
    public String birthday;
    public String city;
    //联系方式
    public String contact;
    //
    public String label;
    /**
     * 最小推送年龄
     */
    public String pushing_small_age;
    /**
     * 最大推送年龄
     */
    public String pushing_large_age;
    /**
     * 推送性别 0不限1男2女
     */
    public int pushing_gender;
    /**
     * 性别 1男2女
     */
    public int sex;


    protected UpdatePersonalInfoRequest(Parcel in) {
        token = in.readString();
        nickname = in.readString();
        cover = in.readString();
        declaration = in.readString();
        trait = in.readString();
        height = in.readString();
        weight = in.readString();
        bust = in.readString();
        occupation = in.readString();
        birthday = in.readString();
        city = in.readString();
        label = in.readString();
        pushing_small_age = in.readString();
        pushing_large_age = in.readString();
        pushing_gender = in.readInt();
        sex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(nickname);
        dest.writeString(cover);
        dest.writeString(declaration);
        dest.writeString(trait);
        dest.writeString(height);
        dest.writeString(weight);
        dest.writeString(bust);
        dest.writeString(occupation);
        dest.writeString(birthday);
        dest.writeString(city);
        dest.writeString(label);
        dest.writeString(pushing_small_age);
        dest.writeString(pushing_large_age);
        dest.writeInt(pushing_gender);
        dest.writeInt(sex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdatePersonalInfoRequest> CREATOR = new Creator<UpdatePersonalInfoRequest>() {
        @Override
        public UpdatePersonalInfoRequest createFromParcel(Parcel in) {
            return new UpdatePersonalInfoRequest(in);
        }

        @Override
        public UpdatePersonalInfoRequest[] newArray(int size) {
            return new UpdatePersonalInfoRequest[size];
        }
    };
}
