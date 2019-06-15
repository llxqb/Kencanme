package com.shushan.kencanme.mvp.ui.activity.pay;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.ReChargeBeansModel;
import com.shushan.kencanme.mvp.model.ResponseData;

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
            ReChargeBeansInfoResponse response = (ReChargeBeansInfoResponse) responseData.parsedData;
            mRechargeView.RechargeBeansInfoSuccess(response);
        } else {
            mRechargeView.showLoading(responseData.errorMsg);
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
