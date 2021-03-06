package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.BlackUserRequest;
import com.shushan.kencanme.app.entity.request.DeleteUserRequest;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.LookAlbumByBeansRequest;
import com.shushan.kencanme.app.entity.request.LookContactTypeRequest;
import com.shushan.kencanme.app.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.network.networkapi.RecommendUserInfoApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * RecommendUserInfoModel
 */

public class RecommendUserInfoModel {
    private final RecommendUserInfoApi mRecommendUserInfoApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public RecommendUserInfoModel(RecommendUserInfoApi api, Gson gson, ModelTransform transform) {
        mRecommendUserInfoApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> recommendUserInfoRequest(RecommendUserInfoRequest request) {
        return mRecommendUserInfoApi.recommendUserInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> onRequestBlackUser(BlackUserRequest request) {
        return mRecommendUserInfoApi.onRequestBlackUser(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> onRequestDeleteUser(DeleteUserRequest request) {
        return mRecommendUserInfoApi.onRequestDeleteUser(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    //添加喜欢
    public Observable<ResponseData> onRequestLike(LikeRequest request) {
        return mRecommendUserInfoApi.onRequestLike(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 查看联系方式
     */
    public Observable<ResponseData> onRequestContact(LookContactTypeRequest request) {
        return mRecommendUserInfoApi.onRequestContact(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    /**
     * 嗨豆查看相册
     */
    public Observable<ResponseData> onRequestAlbumByBeans(LookAlbumByBeansRequest request) {
        return mRecommendUserInfoApi.onRequestAlbumByBeans(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    //请求首页个人信息  如 是否喜欢数 聊天数
    public Observable<ResponseData> onRequestHomeUserInfo(TokenRequest request) {
        return mRecommendUserInfoApi.onRequestHomeUserInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 统计今日密聊次数
     */
    public Observable<ResponseData> onRequestChatNum(RequestFreeChat request) {
        return mRecommendUserInfoApi.onRequestChatNum(mGson.toJson(request)).map(mTransform::transformCommon);
    }
}
