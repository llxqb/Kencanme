package com.shushan.kencanme.app.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface BuyApi {

    /**
     * 购买嗨豆 嗨豆信息
     */
    @POST("menggoda/user/beansinfo")
    Observable<String> beansInfoRequest(@Body String request);

    /**
     * 成为Vip  列表
     */
    @POST("menggoda/user/vipinfo")
    Observable<String> openVipListRequest(@Body String request);
    /**
     * 创建订单
     */
    @POST("menggoda/order")
    Observable<String> onRequestCreateOrder(@Body String request);
    /**
     * 支付成功上报
     */
    @POST("menggoda/apple")
    Observable<String> onRequestPaySuccess(@Body String request);
    /**
     * 用户首页信息
     */
    @POST("menggoda/index/user_exposure")
    Observable<String> onRequestHomeUserInfo(@Body String request);
}
