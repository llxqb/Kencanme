package com.shushan.kencanme.app.mvp.ui.activity.pay;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.PayFinishAHDIRequest;
import com.shushan.kencanme.app.entity.request.PayFinishByUniPinRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderAHDIRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderAHDIResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderByUniPinResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
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
                mRechargeView.createOrderGoogleSuccess(response);
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
     * APP支付成功上报--Google
     */
    @Override
    public void onPayFinishUpload(PayFinishUploadRequest payFinishUpload) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onPayFinishUpload(payFinishUpload).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUploadPaySuccess, throwable -> mRechargeView.getPayFinishGoogleUploadThowable(),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestUploadPaySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRechargeView.getPayFinishGoogleUploadSuccess();
//            responseData.parseData(PayFinishUploadResponse.class);
//            if (responseData.parsedData != null) {
//                PayFinishUploadResponse response = (PayFinishUploadResponse) responseData.parsedData;
//                mRechargeView.getPayFinishUploadSuccess(response);
//            }
        } else {
            mRechargeView.getPayFinishGoogleUploadFail(responseData.errorMsg);
        }
    }


    /**
     * AHDI支付创建订单
     */
    @Override
    public void onRequestCreateOrderAHDI(RequestOrderAHDIRequest requestOrderAHDIRequest) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onRequestCreateOrderAHDI(requestOrderAHDIRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateAHDIOrderSuccess, throwable -> mRechargeView.showErrMessage(throwable),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestCreateAHDIOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderAHDIResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderAHDIResponse response = (CreateOrderAHDIResponse) responseData.parsedData;
                mRechargeView.createOrderAHDISuccess(response);
            }
        } else {
            mRechargeView.showToast(responseData.errorMsg);
        }
    }

    /**
     * AHDI支付上报（查询是否已经支付完成）
     */
    @Override
    public void onPayFinishAHDIUpload(PayFinishAHDIRequest payFinishAHDIRequest) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onPayFinishAHDIUpload(payFinishAHDIRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishAHDISuccess, throwable -> mRechargeView.getPayFinishAHDIUploadThowable(),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestPayFinishAHDISuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRechargeView.getPayFinishAHDIUploadSuccess();
        } else {
            mRechargeView.getPayFinishAHDIUploadFail(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付创建订单
     */
    @Override
    public void onRequestCreateOrderByUniPin(RequestOrderUniPinPayRequest requestOrderUniPinPayRequest) {
        mRechargeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mReChargeBeansModel.onRequestCreateOrderByUniPin(requestOrderUniPinPayRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateOrderByUniPinSuccess, throwable -> mRechargeView.showErrMessage(throwable),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestCreateOrderByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderByUniPinResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderByUniPinResponse response = (CreateOrderByUniPinResponse) responseData.parsedData;
                mRechargeView.createOrderByUniPinSuccess(response);
            }
        } else {
            mRechargeView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付上报
     */
    @Override
    public void onPayFinishUploadByUniPin(PayFinishByUniPinRequest payFinishByUniPinRequest) {
        Disposable disposable = mReChargeBeansModel.onPayFinishUploadByUniPin(payFinishByUniPinRequest).compose(mRechargeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishByUniPinSuccess, throwable -> mRechargeView.getPayFinishUploadByUniPinThowable(),
                        () -> mRechargeView.dismissLoading());
        mRechargeView.addSubscription(disposable);
    }

    private void requestPayFinishByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRechargeView.getPayFinishUploadByUniPinSuccess();
        } else {
            mRechargeView.getPayFinishUploadByUniPinFail(mContext.getResources().getString(R.string.payment_fail));
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
