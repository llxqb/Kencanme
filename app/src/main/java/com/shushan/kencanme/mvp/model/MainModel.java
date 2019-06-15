package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
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

}
