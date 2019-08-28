package com.shushan.kencanme.app.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MessageApi {
    /**
     * 系统消息列表
     */
    @POST("menggoda/message")
    Observable<String> onRequestSystemMsg(@Body String request);
    /**
     * 清空系统消息
     */
    @POST("menggoda/message/del")
    Observable<String> onRequestDeleteSystemMsg(@Body String request);
    /**
     * 上传图片
     */
    @POST("menggoda/upload")
    Observable<String> uploadImageRequest(@Body String request);
    /**
     * 嗨豆回复消息/查看私密照片
     */
    @POST("menggoda/operation/consume_beans")
    Observable<String> onRequestUseBeans(@Body String request);

    /**
     * 用户首页信息
     */
    @POST("menggoda/index/user_exposure")
    Observable<String> onRequestHomeUserInfo(@Body String request);

    /**
     * 根据融云第三方id获取用户头像和昵称
     */
    @POST("menggoda/user/rongyun_info")
    Observable<String> onRequestUserInfoByRid(@Body String request);
    /**
     * 根据融云第三方id获取关系
     */
    @POST("menggoda/user/rongyun_relation")
    Observable<String> onRequestUserRelation(@Body String request);
    /**
     * 聊天打招呼接口
     */
    @POST("menggoda/operation/call")
    Observable<String> onRequestHiNum(@Body String request);
}
