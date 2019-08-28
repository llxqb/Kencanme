package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.HiNumRequest;
import com.shushan.kencanme.app.entity.request.SystemMsgRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.request.UseBeansRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.network.networkapi.MessageApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class MessageModel {
    private final MessageApi mMessageApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MessageModel(MessageApi api, Gson gson, ModelTransform transform) {
        mMessageApi = api;
        mGson = gson;
        mTransform = transform;
    }



    /**
     *系统消息列表
     */
    public Observable<ResponseData> onRequestSystemMsg(SystemMsgRequest request) {
        return mMessageApi.onRequestSystemMsg(mGson.toJson(request)).map(mTransform::transformListType);
    }
    /**
     *清空系统消息
     */
    public Observable<ResponseData> onRequestDeleteSystemMsg(TokenRequest request) {
        return mMessageApi.onRequestDeleteSystemMsg(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 上传图片到服务器
     */
    public Observable<ResponseData> uploadImageRequest(UploadImage uploadImage) {
        return mMessageApi.uploadImageRequest(mGson.toJson(uploadImage)).map(mTransform::transformCommon);
    }

    /**
     * 嗨豆回复消息/查看私密照片
     */
    public Observable<ResponseData> onRequestUseBeans(UseBeansRequest useBeansRequest) {
        return mMessageApi.onRequestUseBeans(mGson.toJson(useBeansRequest)).map(mTransform::transformCommon);
    }

    /**
     * 请求首页个人信息  如 是否喜欢数 聊天数
     */
    public Observable<ResponseData> onRequestHomeUserInfo(TokenRequest request) {
        return mMessageApi.onRequestHomeUserInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 根据融云第三方id获取用户头像和昵称
     */
    public Observable<ResponseData> onRequestUserInfoByRid(UserInfoByRidRequest request) {
        return mMessageApi.onRequestUserInfoByRid(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    /**
     * 根据融云第三方id获取关系
     */
    public Observable<ResponseData> onRequestUserRelation(UserInfoByRidRequest request) {
        return mMessageApi.onRequestUserRelation(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    /**
     * 聊天打招呼接口
     */
    public Observable<ResponseData> onRequestHiNum(HiNumRequest request) {
        return mMessageApi.onRequestHiNum(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
