//package com.shushan.kencanme.entity;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//public class PhotoBean implements Parcelable{
//    public PhotoBean() {
//
//    }
//
//    public String picPath;
//    /**
//     * 图片或视频
//     * 1 普通 2 VIP 3 私密
//     */
//    public int picType;
//    /**
//     * 是否是视频
//     */
//    public boolean isVideo;
//
//    public PhotoBean(Parcel in) {
//        picPath = in.readString();
//        picType = in.readInt();
//        isVideo = in.readByte() != 0;
//    }
//
//    public static final Creator<PhotoBean> CREATOR = new Creator<PhotoBean>() {
//        @Override
//        public PhotoBean createFromParcel(Parcel in) {
//            return new PhotoBean(in);
//        }
//
//        @Override
//        public PhotoBean[] newArray(int size) {
//            return new PhotoBean[size];
//        }
//    };
//
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(picPath);
//        dest.writeInt(picType);
//        dest.writeByte((byte) (isVideo ? 1 : 0));
//    }
//}
