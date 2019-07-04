package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.FeedbackProblemRequest;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.network.networkapi.PersonalInfoApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class SettingModel {
    private final PersonalInfoApi mPersonalInfoApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public SettingModel(PersonalInfoApi api, Gson gson, ModelTransform transform) {
        mPersonalInfoApi = api;
        mGson = gson;
        mTransform = transform;
    }

    /**
     * 更新用户资料
     */
    public Observable<ResponseData> updatePersonalInfoRequest(UpdatePersonalInfoRequest request) {
        return mPersonalInfoApi.updatePersonalInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    /**
     * 上传图片
     */
    public Observable<ResponseData> uploadImageRequest(UploadImage uploadImage) {
        return mPersonalInfoApi.uploadImageRequest(mGson.toJson(uploadImage)).map(mTransform::transformCommon);
    }
    /**
     * 问题反馈
     */
    public Observable<ResponseData> onRequestFeedbackProblem(FeedbackProblemRequest request) {
        return mPersonalInfoApi.onRequestFeedbackProblem(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
