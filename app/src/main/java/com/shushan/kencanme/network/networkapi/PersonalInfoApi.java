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
//    @Multipart
//    @POST("menggoda/upload")
//    Observable<String>uploadImageRequest(@Part("dir") RequestBody dir, @Part MultipartBody.Part file);
}
