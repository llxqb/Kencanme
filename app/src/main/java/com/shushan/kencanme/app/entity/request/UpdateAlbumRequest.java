package com.shushan.kencanme.app.entity.request;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Desci: //增加、更新我的相册
 */
public class UpdateAlbumRequest implements Parcelable{
    public String token;
    //图片/视频地址
    public String album_url;
    /**
     * 1普通照片/视频  2会员照片/视频  3私密照片/视频
     */
    public int album_type;
    /**
     * 照片id
     * 不为空为修改，为空为新增
     */
    public int id;
    /**
     * 我的相册带过来
     * 嗨豆数量，仅type为3时会用到
     */
    public int cost;

    //my add++++
    /**
     * 是否是视频
     */
    public boolean isVideo;

    /**
     * 鉴黄追踪第三方taskId
     */
    public String taskId;

    public UpdateAlbumRequest() {
    }

    protected UpdateAlbumRequest(Parcel in) {
        token = in.readString();
        album_url = in.readString();
        album_type = in.readInt();
        id = in.readInt();
        cost = in.readInt();
        isVideo = in.readByte() != 0;
        taskId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(album_url);
        dest.writeInt(album_type);
        dest.writeInt(id);
        dest.writeInt(cost);
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeString(taskId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateAlbumRequest> CREATOR = new Creator<UpdateAlbumRequest>() {
        @Override
        public UpdateAlbumRequest createFromParcel(Parcel in) {
            return new UpdateAlbumRequest(in);
        }

        @Override
        public UpdateAlbumRequest[] newArray(int size) {
            return new UpdateAlbumRequest[size];
        }
    };
}
