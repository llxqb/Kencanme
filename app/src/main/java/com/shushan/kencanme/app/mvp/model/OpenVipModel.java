package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.OpenVipRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.network.networkapi.BuyApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * ReChargeModel
 */

public class OpenVipModel {
    private final BuyApi mBuyApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public OpenVipModel(BuyApi api, Gson gson, ModelTransform transform) {
        mBuyApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> openVipListRequest(OpenVipRequest request) {
        return mBuyApi.openVipListRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 创建订单
     */
    public Observable<ResponseData> onRequestCreateOrder(CreateOrderRequest request) {
        return mBuyApi.onRequestCreateOrder(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求我的-首页接口，更新个人信息
     */
    public Observable<ResponseData> onRequestHomeUserInfo(TokenRequest request) {
        return mBuyApi.onRequestHomeUserInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 支付成功上报
     */
    public Observable<ResponseData> onPayFinishUpload(PayFinishUploadRequest request) {
        return mBuyApi.onRequestPaySuccess(request.INAPP_PURCHASE_DATA,request.INAPP_DATA_SIGNATURE,request.order_no).map(mTransform::transformCommon);
    }


}
