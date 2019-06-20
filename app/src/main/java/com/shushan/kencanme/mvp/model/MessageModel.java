package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.SystemMsgRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.network.networkapi.MessageApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class MessageModel {
    private final MessageApi mMessageApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MessageModel(MessageApi api, Gson gson, ModelTransform transform) {
        mMessageApi = api;
        mGson = gson;
        mTransform = transform;
    }



    /**
     *好友/喜欢的人列表
     */
    public Observable<ResponseData> onRequestSystemMsg(SystemMsgRequest request) {
        return mMessageApi.onRequestSystemMsg(mGson.toJson(request)).map(mTransform::transformListType);
    }

    /**
     * 上传图片到服务器
     */
    public Observable<ResponseData> uploadImageRequest(UploadImage uploadImage) {
        return mMessageApi.uploadImageRequest(mGson.toJson(uploadImage)).map(mTransform::transformCommon);
    }


}
