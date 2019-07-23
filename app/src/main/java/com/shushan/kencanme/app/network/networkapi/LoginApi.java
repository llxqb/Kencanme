package com.shushan.kencanme.app.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by li.liu on 2019/05/28.
 * LoginApi
 */

public interface LoginApi {
    /**
     * Google登录
     */
    @POST("menggoda/login")
    Observable<String> loginRequest(@Body String request);

    /**
     * 我的
     */
    @POST("menggoda/user")
    Observable<String> onRequestPersonalInfo(@Body String token);
    /**
     * facebook登录
     */
    @POST("menggoda/login/facebook")
    Observable<String> onRequestLoginFacebook(@Body String token);
}
