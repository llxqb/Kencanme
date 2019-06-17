package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by li.liu on 2019/05/28.
 * RecommendUserInfoApi
 */

public interface RecommendUserInfoApi {
    @POST("menggoda/operation/user")
    Observable<String> recommendUserInfoRequest(@Body String request);

    /**
     * 加入黑名单
     */
    @POST("menggoda/operation/blacklist")
    Observable<String> onRequestBlackUser(@Body String request);

    /**
     * 删除好友
     */
    @POST("menggoda/operation/del_friend")
    Observable<String> onRequestDeleteUser(@Body String request);

    /**
     * 喜欢
     */
    @POST("menggoda/Operation")
    Observable<String> onRequestLike(@Body String request);
}
