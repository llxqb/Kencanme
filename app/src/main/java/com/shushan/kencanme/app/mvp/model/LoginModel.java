package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.FacebookLoginRequest;
import com.shushan.kencanme.app.entity.request.LoginRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.network.networkapi.LoginApi;

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

    public Observable<ResponseData> onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest) {
        return mLoginApi.onRequestPersonalInfo(mGson.toJson(personalInfoRequest)).map(mTransform::transformCommon);
    }

    /**
     * facebook登录
     */
    public Observable<ResponseData> onRequestLoginFacebook(FacebookLoginRequest request) {
        return mLoginApi.onRequestLoginFacebook(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
