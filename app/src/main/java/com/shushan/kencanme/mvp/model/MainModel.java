package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.BuyExposureTimeRequest;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.network.networkapi.MainApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class MainModel {
    private final MainApi mMainApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }


    //请求个人信息
    public Observable<ResponseData> onRequestPersonalInfo(PersonalInfoRequest request) {
        return mMainApi.onRequestPersonalInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    //请求首页个人信息  如 是否喜欢数 聊天数
    public Observable<ResponseData> onRequestHomeUserInfo(TokenRequest request) {
        return mMainApi.onRequestHomeUserInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    //请求homeFragment数据
    public Observable<ResponseData> onRequestInfo(HomeFragmentRequest request) {
        return mMainApi.onRequestHomeInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    //请求我的相册数据
    public Observable<ResponseData> onRequestMyAlbum(MyAlbumRequest request) {
        return mMainApi.onRequestMyAlbum(mGson.toJson(request)).map(mTransform::transformListType);
    }

    //添加喜欢
    public Observable<ResponseData> onRequestLike(LikeRequest request) {
        return mMainApi.onRequestLike(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    // 曝光次数嗨豆购买规则 (列表)
    public Observable<ResponseData> onRequestBuyExposureTimeList(TokenRequest request) {
        return mMainApi.onRequestBuyExposureTimeList(mGson.toJson(request)).map(mTransform::transformListType);
    }
    // 嗨豆购买曝光次数
    public Observable<ResponseData> onRequestBuyExposureTime(BuyExposureTimeRequest request) {
        return mMainApi.onRequestBuyExposureTime(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    // 进行超级曝光
    public Observable<ResponseData> onRequestExposure(TokenRequest request) {
        return mMainApi.onRequestExposure(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    // 根据融云第三方id获取用户头像和昵称
    public Observable<ResponseData> onRequestUserInfoByRid(UserInfoByRidRequest request) {
        return mMainApi.onRequestUserInfoByRid(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     *最新一条系统消息
     */
    public Observable<ResponseData> onRequestSystemMsgNew(TokenRequest request) {
        return mMainApi.onRequestSystemMsgNew(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    /**
     *好友/喜欢的人列表
     */
    public Observable<ResponseData> onRequestMyFriendList(MyFriendsRequest request) {
        return mMainApi.onRequestMyFriendList(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
