package com.shushan.kencanme.app.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
     * 用户首页信息
     */
    @POST("menggoda/index/user_exposure")
    Observable<String> onRequestHomeUserInfo(@Body String request);
    /**
     * 创建订单Google支付
     */
    @POST("menggoda/order")
    Observable<String> onRequestCreateOrder(@Body String request);
    /**
     * 创建订单--AHDI支付
     */
    @POST("menggoda/Ahdipay")
    Observable<String> onRequestCreateOrderAHDI(@Body String request);
    /**
     * 创建订单--UniPin支付
     */
    @POST("menggoda/unipinpay")
    Observable<String> onRequestCreateOrderByUniPin(@Body String request);
    /**
     * Google支付成功上报
     * 多参数表单提交
     */
    @FormUrlEncoded
    @POST("menggoda/google")
    Observable<String> onRequestPaySuccess(@Field("INAPP_PURCHASE_DATA") String data, @Field("INAPP_DATA_SIGNATURE") String signature,@Field("order_no") String order_no);
    /**
     * AHDI支付成功上报
     */
    @POST("menggoda/Ahdipay/report")
    Observable<String> onPayFinishAHDIUpload(@Body String request);
    /**
     * UniPin支付上报
     */
    @POST("menggoda/unipinpay/inquiry")
    Observable<String> onPayFinishUploadByUniPin(@Body String request);
}
