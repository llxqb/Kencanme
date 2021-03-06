package com.shushan.kencanme.app.mvp.ui.activity.main;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadDeviceRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.entity.response.MessageIdResponse;
import com.shushan.kencanme.app.entity.response.UploadDeviceResponse;
import com.shushan.kencanme.app.entity.response.UserInfoByRidResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.MainModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class MainPresenterImpl implements MainControl.PresenterMain {

    private MainControl.MainView mMainView;
    private final MainModel mMainModel;
    private final Context mContext;

    @Inject
    public MainPresenterImpl(Context context, MainModel model, MainControl.MainView mainView) {
        mContext = context;
        mMainModel = model;
        mMainView = mainView;
    }


    /**
     * 请求个人信息（首页）
     *//*
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        mMainView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onRequestHomeUserInfo(tokenRequest).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
            mMainView.homeUserInfoSuccess(response);
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }*/

    /**
     * 根据融云第三方id获取用户头像和昵称
     */
    private UserInfoByRidRequest mUserInfoByRidRequest;
    private UserInfo userInfo = null;

    @Override
    public UserInfo onRequestUserInfoByRid(UserInfoByRidRequest userInfoByRidRequest) {
        mUserInfoByRidRequest = userInfoByRidRequest;
        mMainModel.onRequestUserInfoByRid(userInfoByRidRequest).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(new Observer<ResponseData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseData responseData) {
                        mMainView.dismissLoading();
                        userInfo = requestUserInfoByRidSuccess(responseData);
                        //刷新用户信息  可以刷新会话列表
                        RongIM.getInstance().refreshUserInfoCache(userInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMainView.showErrMessage(e);
                        mMainView.dismissLoading();
                    }

                    @Override
                    public void onComplete() {

                    }

                });
        return userInfo;
    }

    private UserInfo requestUserInfoByRidSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UserInfoByRidResponse.class);
            if (responseData.parsedData != null) {
                UserInfoByRidResponse response = (UserInfoByRidResponse) responseData.parsedData;
                return new UserInfo(mUserInfoByRidRequest.rongyun_third_id, response.getNickname(), Uri.parse(response.getTrait()));
            }
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
        return null;
    }


    /**
     * 查看用户嗨豆查看私密照片message_id
     */
    @Override
    public void onRequestMessageId(TokenRequest tokenRequest) {
        Disposable disposable = mMainModel.onRequestMessageId(tokenRequest).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestMessageIdSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    private void requestMessageIdSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            MessageIdResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), MessageIdResponse.class);
            mMainView.messageIdSuccess(response);
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 上传设备接口  后台做统计功能
     */
    @Override
    public void onUploadDevice(UploadDeviceRequest uploadDeviceRequest) {
        Disposable disposable = mMainModel.onUploadDevice(uploadDeviceRequest).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::uploadDeviceSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    /**
     * 上传设备接口 返回
     */
    private void uploadDeviceSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UploadDeviceResponse.class);
            if (responseData.parsedData != null) {
                UploadDeviceResponse response = (UploadDeviceResponse) responseData.parsedData;
                mMainView.getDeviceInfoSuccess(response);
            }
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
