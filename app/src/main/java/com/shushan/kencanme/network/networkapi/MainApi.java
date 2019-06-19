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

    /**
     * 曝光次数嗨豆购买规则
     * 曝光次数嗨豆购买规则 (列表)
     */
    @POST("menggoda/order/exposure_beans_rule")
    Observable<String> onRequestBuyExposureTimeList(@Body String request);

    /**
     * 嗨豆购买曝光次数
     */
    @POST("menggoda/order/buy_exposure")
    Observable<String> onRequestBuyExposureTime(@Body String request);
    /**
     * 进行超级曝光
     */
    @POST("menggoda/user/exposure_open")
    Observable<String> onRequestExposure(@Body String request);
    /**
     * 根据融云第三方id获取用户头像和昵称
     */
    @POST("menggoda/user/rongyun_info")
    Observable<String> onRequestUserInfoByRid(@Body String request);

}
