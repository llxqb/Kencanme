package com.shushan.kencanme.app.mvp.ui.activity.pay;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.PayFinishUploadResponse;
import com.shushan.kencanme.app.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.ReChargeBeansModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class RechargePresenterImpl implements RechargeControl.PresenterRecharge {

    private RechargeControl.RechargeView mRechargeView;
    private final ReChargeBeansModel mReChargeBeansModel;
    private final Context mContext;

    @Inject
    public RechargePresenterImpl(Context context, ReChargeBeansModel model, RechargeControl.RechargeView RechargeView) {
        mContext = context;
        mReChargeBeansModel = model;
        mRechargeView = RechargeView;
    }


    @Override
    public void onRequestBeansInfo(ReChargeBeansInfoRequest reChargeBeansInfoRequest) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.beansInfoRequest(reChargeBeansInfoRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mRechargeView.showErrMessage(throwable),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }


    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(ReChargeBeansInfoResponse.class);
            if (responseData.parsedData != null) {
                ReChargeBeansInfoResponse response = (ReChargeBeansInfoResponse) responseData.parsedData;
                mRechargeView.RechargeBeansInfoSuccess(response);
            }
        } else {
            mRechargeView.showLoading(responseData.errorMsg);
        }
    }

    /**
     * 创建订单
     */
    @Override
    public void onRequestCreateOrder(CreateOrderRequest createOrderRequest) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onRequestCreateOrder(createOrderRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::createOrderSuccess, throwable -> mRechargeView.showErrMessage(throwable),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }


    private void createOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderResponse response = (CreateOrderResponse) responseData.parsedData;
                mRechargeView.createOrderSuccess(response);
            }
        } else {
            mRechargeView.showLoading(responseData.errorMsg);
        }
    }

    /**
     * 获取个人信息（首页）
     */
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onRequestHomeUserInfo(tokenRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mRechargeView.showErrMessage(throwable),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            if (responseData.parsedData != null) {
                HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
                mRechargeView.homeUserInfoSuccess(response);
            }
        } else {
            mRechargeView.showToast(responseData.errorMsg);
        }
    }

    /**
     * APP支付成功上报
     */
    @Override
    public void onPayFinishUpload(PayFinishUploadRequest payFinishUpload) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onPayFinishUpload(payFinishUpload).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUploadPaySuccess, throwable -> mRechargeView.showErrMessage(throwable),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestUploadPaySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRechargeView.getPayFinishUploadSuccess();
//            responseData.parseData(PayFinishUploadResponse.class);
//            if (responseData.parsedData != null) {
//                PayFinishUploadResponse response = (PayFinishUploadResponse) responseData.parsedData;
//                mRechargeView.getPayFinishUploadSuccess(response);
//            }
        } else {
            mRechargeView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mRechargeView = null;
    }
}
