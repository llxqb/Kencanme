package com.shushan.kencanme.app.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MainApi {
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
    /**
     * 最新一条系统消息
     */
    @POST("menggoda/message/new_message")
    Observable<String> onRequestSystemMsgNew(@Body String request);
    /**
     * 好友/喜欢的人列表
     */
    @POST("menggoda/user/user_friend")
    Observable<String> onRequestMyFriendList(@Body String request);
    /**
     * 查看用户嗨豆查看私密照片message_id
     */
    @POST("menggoda/operation/get_message_ids")
    Observable<String> onRequestMessageId(@Body String request);
    /**
     * 密聊
     * 统计今日密聊次数
     */
    @POST("menggoda/operation/secret_chat")
    Observable<String> onRequestChatNum(@Body String request);
    /**
     * 上传设备接口  后台做统计功能
     */
    @POST("menggoda/index/update_deviceinfo")
    Observable<String> onUploadDevice(@Body String request);

    /**
     * 请求个人信息（我的）
     */
    @POST("menggoda/user")
    Observable<String> onRequestPersonalInfo(@Body String request);

}
