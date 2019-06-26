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

    /**
     * 查看联系方式
     */
    @POST("menggoda/operation/see_contact")
    Observable<String> onRequestContact(@Body String request);
    /**
     * 嗨豆查看相册
     */
    @POST("menggoda/order/user_beans")
    Observable<String> onRequestAlbumByBeans(@Body String request);

    /**
     * 用户首页信息
     */
    @POST("menggoda/index/user_exposure")
    Observable<String> onRequestHomeUserInfo(@Body String request);
    /**
     * 密聊
     * 统计今日密聊次数
     */
    @POST("menggoda/operation/secret_chat")
    Observable<String> onRequestChatNum(@Body String request);
}
