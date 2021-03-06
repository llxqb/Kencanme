package com.shushan.kencanme.app.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by helei on 2017/4/27.
 * LoginApi
 */

public interface FirstApi {
    @GET("Query/ShoppingCart/ListByShopDetail")
    Observable<String> lookNum(@Query("userId") String userId, @Query("shopId") String shopId);

    @POST("api/user/login")
    Observable<String> loginRequest(@Body String request);
}
