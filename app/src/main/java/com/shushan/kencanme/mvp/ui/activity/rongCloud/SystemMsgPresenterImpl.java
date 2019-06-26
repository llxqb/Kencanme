package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.SystemMsgRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.SystemMsgResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MessageModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class SystemMsgPresenterImpl implements SystemMsgControl.PresenterSystemMsg {

    private SystemMsgControl.SystemMsgView mSystemMsgView;
    private final MessageModel mMessageModel;
    private final Context mContext;

    @Inject
    public SystemMsgPresenterImpl(Context context, MessageModel model, SystemMsgControl.SystemMsgView SystemMsgView) {
        mContext = context;
        mMessageModel = model;
        mSystemMsgView = SystemMsgView;
    }

    @Override
    public void onRequestSystemMsgList(SystemMsgRequest systemMsgRequest) {
        mSystemMsgView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMessageModel.onRequestSystemMsg(systemMsgRequest).compose(mSystemMsgView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mSystemMsgView.showErrMessage(throwable),
                        () -> mSystemMsgView.dismissLoading());
        mSystemMsgView.addSubscription(disposable);
    }


    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            SystemMsgResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), SystemMsgResponse.class);
            mSystemMsgView.getSystemMsgSuccess(response);
        } else {
            mSystemMsgView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 清空系统消息
     */
    @Override
    public void onRequestDeleteSystemMsg(TokenRequest tokenRequest) {
        mSystemMsgView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMessageModel.onRequestDeleteSystemMsg(tokenRequest).compose(mSystemMsgView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDeleteMsgSuccess, throwable -> mSystemMsgView.showErrMessage(throwable),
                        () -> mSystemMsgView.dismissLoading());
        mSystemMsgView.addSubscription(disposable);
    }


    private void requestDeleteMsgSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mSystemMsgView.getDeleteMsgSuccess();
        } else {
            mSystemMsgView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mSystemMsgView = null;
    }


}
