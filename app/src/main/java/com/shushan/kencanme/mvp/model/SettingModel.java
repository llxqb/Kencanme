package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.network.networkapi.PersonalInfoApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class SettingModel {
    private final PersonalInfoApi mPersonalInfoApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public SettingModel(PersonalInfoApi api, Gson gson, ModelTransform transform) {
        mPersonalInfoApi = api;
        mGson = gson;
        mTransform = transform;
    }


    /**
     * 更新用户资料
     */
    public Observable<ResponseData> updatePersonalInfoRequest(UpdatePersonalInfoRequest request) {
        return mPersonalInfoApi.updatePersonalInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
