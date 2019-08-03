package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.ReportUserRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.network.networkapi.PersonalInfoApi;

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


    public Observable<ResponseData> updatePersonalInfoRequest(UpdatePersonalInfoRequest request) {
        return mPersonalInfoApi.updatePersonalInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> uploadVideoRequest(MultipartBody.Part uploadVideo) {
        return mPersonalInfoApi.uploadVideoRequest(uploadVideo).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> uploadImageRequest(UploadImage uploadImage) {
        return mPersonalInfoApi.uploadImageRequest(mGson.toJson(uploadImage)).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> updateMyAlbum(UpdateAlbumRequest updateAlbumRequest) {
        return mPersonalInfoApi.updateMyAlbum(mGson.toJson(updateAlbumRequest)).map(mTransform::transformCommon);
    }

    /**
     * 举报原因列表
     */
    public Observable<ResponseData> reportUserListRequest(TokenRequest request) {
        return mPersonalInfoApi.reportUserListRequest(mGson.toJson(request)).map(mTransform::transformListType);
    }
    /**
     * 举报用户
     */
    public Observable<ResponseData> reportUserRequest(ReportUserRequest reportUserRequest) {
        return mPersonalInfoApi.reportUserRequest(mGson.toJson(reportUserRequest)).map(mTransform::transformCommon);
    }
    /**
     * 请求个人信息（我的）
     */
    public Observable<ResponseData> onRequestPersonalInfo(PersonalInfoRequest request) {
        return mPersonalInfoApi.onRequestPersonalInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
