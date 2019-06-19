package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Context;
import android.net.Uri;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.entity.response.UserInfoByRidResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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
     * 请求个人信息
     */
    @Override
    public void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest) {
        mMainView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMainModel.onRequestPersonalInfo(personalInfoRequest).compose(mMainView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPersonalInfoSuccess, throwable -> mMainView.showErrMessage(throwable),
                        () -> mMainView.dismissLoading());
        mMainView.addSubscription(disposable);
    }

    private void requestPersonalInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(PersonalInfoResponse.class);
            PersonalInfoResponse response = (PersonalInfoResponse) responseData.parsedData;
            mMainView.personalInfoSuccess(response);
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 请求个人信息（首页）
     */
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
    }

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
                        userInfo= requestUserInfoByRidSuccess(responseData);
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
            UserInfoByRidResponse response = (UserInfoByRidResponse) responseData.parsedData;
            return new UserInfo(mUserInfoByRidRequest.rongyun_third_id, response.getNickname(), Uri.parse(response.getTrait()));
        } else {
            mMainView.showToast(responseData.errorMsg);
        }
        return null;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
