package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by li.liu on 2019/05/28.
 * LoginApi
 */

public interface PersonalInfoApi {
    /**
     * 更新用户资料
     */
    @POST("menggoda/user/setuserinfo")
    Observable<String> createPersonalInfoRequest(@Body String request);

    /**
     * 上传文件
     */
    @Multipart
    @POST("menggoda/upload/video")
    Observable<String> uploadVideoRequest(@Part MultipartBody.Part video);

    /**
     * 上传图片
     */
    @POST("menggoda/upload")
    Observable<String> uploadImageRequest(@Body String request);

    /**
     * 增加、修改 我的相册
     */
    @POST("menggoda/user/album_add")
    Observable<String> updateMyAlbum(@Body String request);


}
