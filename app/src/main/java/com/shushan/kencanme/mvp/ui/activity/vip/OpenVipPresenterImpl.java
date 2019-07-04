package com.shushan.kencanme.mvp.ui.activity.vip;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.CreateOrderRequest;
import com.shushan.kencanme.entity.request.OpenVipRequest;
import com.shushan.kencanme.entity.request.PaySuccessRequest;
import com.shushan.kencanme.entity.response.CreateOrderResponse;
import com.shushan.kencanme.entity.response.OpenVipResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.OpenVipModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class OpenVipPresenterImpl implements OpenVipControl.PresenterOpenVip {

    private OpenVipControl.OpenVipView mOpenVipView;
    private final OpenVipModel mOpenVipModel;
    private final Context mContext;

    @Inject
    public OpenVipPresenterImpl(Context context, OpenVipModel model, OpenVipControl.OpenVipView OpenVipView) {
        mContext = context;
        mOpenVipModel = model;
        mOpenVipView = OpenVipView;
    }

    @Override
    public void openVipListRequest(OpenVipRequest openVipRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.openVipListRequest(openVipRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }


    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(OpenVipResponse.class);
            if (responseData.parsedData != null) {
                OpenVipResponse response = (OpenVipResponse) responseData.parsedData;
                mOpenVipView.OpenVipListSuccess(response);
            }
        } else {
            mOpenVipView.showLoading(responseData.errorMsg);
        }
    }

    /**
     * 创建订单
     */
    @Override
    public void onRequestCreateOrder(CreateOrderRequest createOrderRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onRequestCreateOrder(createOrderRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::createOrderSuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }


    private void createOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderResponse response = (CreateOrderResponse) responseData.parsedData;
                mOpenVipView.createOrderSuccess(response);
            }
        } else {
            mOpenVipView.showLoading(responseData.errorMsg);
        }
    }

    /**
     * 支付成功上报
     */
    @Override
    public void onRequestPaySuccess(PaySuccessRequest paySuccessRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onRequestPaySuccess(paySuccessRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::paySuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }


    private void paySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderResponse response = (CreateOrderResponse) responseData.parsedData;
                mOpenVipView.createOrderSuccess(response);
            }
        } else {
            mOpenVipView.showLoading(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mOpenVipView = null;
    }


}
