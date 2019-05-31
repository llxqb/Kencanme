package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.network.networkapi.RecommendUserInfoApi;

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

}
