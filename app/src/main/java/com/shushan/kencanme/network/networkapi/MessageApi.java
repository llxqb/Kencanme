package com.shushan.kencanme.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MessageApi {
    /**
     * 系统消息列表
     */
    @POST("menggoda/message")
    Observable<String> onRequestSystemMsg(@Body String request);


}
