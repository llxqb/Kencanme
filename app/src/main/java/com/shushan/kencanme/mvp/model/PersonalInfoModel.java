package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.network.networkapi.PersonalInfoApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * LoginModel
 */

public class PersonalInfoModel {
    private final PersonalInfoApi mPersonalInfoApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public PersonalInfoModel(PersonalInfoApi api, Gson gson, ModelTransform transform) {
        mPersonalInfoApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> createPersonalInfoRequest(PersonalInfoRequest request) {
        return mPersonalInfoApi.createPersonalInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
