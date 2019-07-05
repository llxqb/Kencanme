package com.shushan.kencanme.app.network.networkapi;

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
    Observable<String> updatePersonalInfoRequest(@Body String request);

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

    /**
     * 删除 我的相册
     */
    @POST("menggoda/user/album_del")
    Observable<String> deleteMyAlbum(@Body String request);


    //我的 - 我的相册list
    @POST("menggoda/user/album")
    Observable<String> onRequestMyAlbum(@Body String request);

    /**
     * 举报用户
     */
    @POST("menggoda/operation/report")
    Observable<String> reportUserRequest(@Body String request);
    /**
     * 问题反馈
     */
    @POST("menggoda/user/feedback")
    Observable<String> onRequestFeedbackProblem(@Body String request);

    /**
     * 请求个人信息（我的）
     */
    @POST("menggoda/user")
    Observable<String> onRequestPersonalInfo(@Body String request);
}
