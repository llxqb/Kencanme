package com.shushan.kencanme.app.mvp.ui.activity.vip;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.OpenVipRequest;
import com.shushan.kencanme.app.entity.request.PayFinishAHDIRequest;
import com.shushan.kencanme.app.entity.request.PayFinishByUniPinRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderAHDIRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderAHDIResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderByUniPinResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.OpenVipResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.OpenVipModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

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
     * 获取个人信息（首页）
     */
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onRequestHomeUserInfo(tokenRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            if (responseData.parsedData != null) {
                HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
                mOpenVipView.homeUserInfoSuccess(response);
            }
        } else {
            mOpenVipView.showToast(responseData.errorMsg);
        }
    }

    /**
     * APP支付成功上报
     */
    @Override
    public void onPayFinishUpload(PayFinishUploadRequest payFinishUpload) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onPayFinishUpload(payFinishUpload).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUploadPaySuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }

    private void requestUploadPaySuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mOpenVipView.getPayFinishUploadSuccess();
        } else {
            mOpenVipView.showToast(responseData.errorMsg);
        }
    }

    /**
     * AHDI支付创建订单
     */
    @Override
    public void onRequestCreateOrderAHDI(RequestOrderAHDIRequest requestOrderAHDIRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onRequestCreateOrderAHDI(requestOrderAHDIRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateAHDIOrderSuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }

    private void requestCreateAHDIOrderSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderAHDIResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderAHDIResponse response = (CreateOrderAHDIResponse) responseData.parsedData;
                mOpenVipView.createOrderAHDISuccess(response);
            }
        } else {
            mOpenVipView.showToast(responseData.errorMsg);
        }
    }

    /**
     * AHDI支付上报（查询是否已经支付完成）
     */
    @Override
    public void onPayFinishAHDIUpload(PayFinishAHDIRequest payFinishAHDIRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onPayFinishAHDIUpload(payFinishAHDIRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishAHDISuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }

    private void requestPayFinishAHDISuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mOpenVipView.getPayFinishAHDIUploadSuccess();
        } else {
            mOpenVipView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付创建订单
     */
    @Override
    public void onRequestCreateOrderByUniPin(RequestOrderUniPinPayRequest requestOrderUniPinPayRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onRequestCreateOrderByUniPin(requestOrderUniPinPayRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestCreateOrderByUniPinSuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }

    private void requestCreateOrderByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(CreateOrderByUniPinResponse.class);
            if (responseData.parsedData != null) {
                CreateOrderByUniPinResponse response = (CreateOrderByUniPinResponse) responseData.parsedData;
                mOpenVipView.createOrderByUniPinSuccess(response);
            }
        } else {
            mOpenVipView.showToast(responseData.errorMsg);
        }
    }


    /**
     * UniPin支付上报
     */
    @Override
    public void onPayFinishUploadByUniPin(PayFinishByUniPinRequest payFinishByUniPinRequest) {
        mOpenVipView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mOpenVipModel.onPayFinishUploadByUniPin(payFinishByUniPinRequest).compose(mOpenVipView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPayFinishByUniPinSuccess, throwable -> mOpenVipView.showErrMessage(throwable),
                        () -> mOpenVipView.dismissLoading());
        mOpenVipView.addSubscription(disposable);
    }

    private void requestPayFinishByUniPinSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mOpenVipView.getPayFinishUploadByUniPinSuccess();
        } else {
            mOpenVipView.showToast(responseData.errorMsg);
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
