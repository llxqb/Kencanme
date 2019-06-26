package com.shushan.kencanme.mvp.model;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.CreateOrderRequest;
import com.shushan.kencanme.entity.request.OpenVipRequest;
import com.shushan.kencanme.network.networkapi.BuyApi;

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


}
