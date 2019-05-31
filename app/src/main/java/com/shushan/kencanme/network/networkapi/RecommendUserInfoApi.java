package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by li.liu on 2019/05/28.
 * RecommendUserInfoApi
 */

public interface RecommendUserInfoApi {
    @POST("teacher/user/login")
    Observable<String> recommendUserInfoRequest(@Body String request);
}
