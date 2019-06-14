package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.network.networkapi.PersonalInfoApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * LoginModel
 */

public class MyAlbumModel {
    private final PersonalInfoApi mPersonalInfoApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MyAlbumModel(PersonalInfoApi api, Gson gson, ModelTransform transform) {
        mPersonalInfoApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> deleteAlbumRequest(UpdateAlbumRequest request) {
        return mPersonalInfoApi.deleteMyAlbum(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
