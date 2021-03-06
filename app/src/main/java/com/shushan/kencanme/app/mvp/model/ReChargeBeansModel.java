package com.shushan.kencanme.app.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.PayFinishAHDIRequest;
import com.shushan.kencanme.app.entity.request.PayFinishByUniPinRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderAHDIRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.network.networkapi.BuyApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/05/28.
 * ReChargeModel
 */

public class ReChargeBeansModel {
    private final BuyApi mBuyApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public ReChargeBeansModel(BuyApi api, Gson gson, ModelTransform transform) {
        mBuyApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> beansInfoRequest(ReChargeBeansInfoRequest request) {
        return mBuyApi.beansInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }


    /**
     * 创建订单--Google支付
     */
    public Observable<ResponseData> onRequestCreateOrder(CreateOrderRequest request) {
        return mBuyApi.onRequestCreateOrder(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * Google支付完成上传服务器
     */
    public Observable<ResponseData> onPayFinishUpload(PayFinishUploadRequest request) {
        return mBuyApi.onRequestPaySuccess(request.INAPP_PURCHASE_DATA, request.INAPP_DATA_SIGNATURE, request.order_no).map(mTransform::transformCommon);
    }

    /**
     * 创建订单--AHDI支付
     */
    public Observable<ResponseData> onRequestCreateOrderAHDI(RequestOrderAHDIRequest request) {
        return mBuyApi.onRequestCreateOrderAHDI(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * AHDI支付上报
     */
    public Observable<ResponseData> onPayFinishAHDIUpload(PayFinishAHDIRequest request) {
        return mBuyApi.onPayFinishAHDIUpload(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 创建订单--UniPin支付
     */
    public Observable<ResponseData> onRequestCreateOrderByUniPin(RequestOrderUniPinPayRequest request) {
        return mBuyApi.onRequestCreateOrderByUniPin(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * UniPin支付上报
     */
    public Observable<ResponseData> onPayFinishUploadByUniPin(PayFinishByUniPinRequest request) {
        return mBuyApi.onPayFinishUploadByUniPin(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求我的-首页接口，更新个人信息
     */
    public Observable<ResponseData> onRequestHomeUserInfo(TokenRequest request) {
        return mBuyApi.onRequestHomeUserInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
