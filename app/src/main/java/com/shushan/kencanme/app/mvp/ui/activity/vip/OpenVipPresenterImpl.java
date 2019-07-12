package com.shushan.kencanme.app.mvp.ui.activity.vip;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.OpenVipRequest;
import com.shushan.kencanme.app.entity.request.PaySuccessRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
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
