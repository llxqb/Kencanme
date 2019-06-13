package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.network.networkapi.PersonalInfoApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Created by li.liu on 2019/05/28.
 * LoginModel
 */

public class PersonalInfoModel {
    private final PersonalInfoApi mPersonalInfoApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public PersonalInfoModel(PersonalInfoApi api, Gson gson, ModelTransform transform) {
        mPersonalInfoApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> createPersonalInfoRequest(UpdatePersonalInfoRequest request) {
        return mPersonalInfoApi.createPersonalInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> uploadVideoRequest(MultipartBody.Part uploadVideo) {
        return mPersonalInfoApi.uploadVideoRequest(uploadVideo).map(mTransform::transformCommon);
//        return null;
    }

    public Observable<ResponseData> uploadImageRequest(UploadImage uploadImage) {
        return mPersonalInfoApi.uploadImageRequest(mGson.toJson(uploadImage)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> updateMyAlbum(UpdateAlbumRequest updateAlbumRequest) {
        return mPersonalInfoApi.updateMyAlbum(mGson.toJson(updateAlbumRequest)).map(mTransform::transformCommon);
    }

}
