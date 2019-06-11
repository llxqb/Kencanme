package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by li.liu on 2019/05/28.
 * LoginApi
 */

public interface LoginApi {
    @POST("menggoda/login")
    Observable<String> loginRequest(@Body String request);
}
