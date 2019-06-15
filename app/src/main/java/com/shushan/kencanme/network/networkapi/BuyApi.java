package com.shushan.kencanme.network.networkapi;

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
}
