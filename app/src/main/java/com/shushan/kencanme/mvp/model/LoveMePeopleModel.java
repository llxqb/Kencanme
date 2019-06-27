package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.request.RequestFreeChat;
import com.shushan.kencanme.network.networkapi.MainApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * LoginModel
 */

public class LoveMePeopleModel {
    private final MainApi mMainApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public LoveMePeopleModel(MainApi api, Gson gson, ModelTransform transform) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    /**
     *好友/喜欢的人列表
     */
    public Observable<ResponseData> onRequestMyFriendList(MyFriendsRequest request) {
        return mMainApi.onRequestMyFriendList(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 添加喜欢
     */
    public Observable<ResponseData> onRequestLike(LikeRequest request) {
        return mMainApi.onRequestLike(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 统计今日密聊次数
     */
    public Observable<ResponseData> onRequestChatNum(RequestFreeChat request) {
        return mMainApi.onRequestChatNum(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
