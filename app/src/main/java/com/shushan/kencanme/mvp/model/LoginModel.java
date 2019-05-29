package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.LoginRequest;
import com.shushan.kencanme.network.networkapi.LoginApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * LoginModel
 */

public class LoginModel {
    private final LoginApi mLoginApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> LoginRequest(LoginRequest request) {
        return mLoginApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
