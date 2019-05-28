package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by li.liu on 2019/05/28.
 * LoginApi
 */

public interface LoginApi {
    @POST("teacher/user/login")
    Observable<String> loginRequest(@Body String request);
}
