package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
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


    public Observable<ResponseData> onRequestInfo(HomeFragmentRequest request) {
        return mMainApi.onRequestInfo(mGson.toJson(request)).map(mTransform::transformCommon);//会员接口
    }

}
