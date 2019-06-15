package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MainApi {
    /**
     * 请求个人信息
     */
    @POST("menggoda/user")
    Observable<String> onRequestPersonalInfo(@Body String request);

    /**
     * 用户首页信息
     */
    @POST("menggoda/index/user_exposure")
    Observable<String> onRequestHomeUserInfo(@Body String request);

    //首页list数据
    @POST("menggoda")
    Observable<String> onRequestHomeInfo(@Body String request);


    //我的 - 我的相册list
    @POST("menggoda/user/album")
    Observable<String> onRequestMyAlbum(@Body String request);

    /**
     * 喜欢
     */
    @POST("menggoda/Operation")
    Observable<String> onRequestLike(@Body String request);

}
