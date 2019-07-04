package com.shushan.kencanme.mvp.ui.activity.login;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.LoginRequest;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.response.LoginResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.LoginModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class LoginPresenterImpl implements LoginControl.PresenterLogin {

    private LoginControl.LoginView mLoginView;
    private final LoginModel mLoginModel;
    private final Context mContext;

    @Inject
    public LoginPresenterImpl(Context context, LoginModel model, LoginControl.LoginView loginView) {
        mContext = context;
        mLoginModel = model;
        mLoginView = loginView;
    }

    /**
     * new RetryWithDelay(3, 3000) 总共重试3次，重试间隔3000毫秒
     * subscribe订阅
     * mLoginView.showErrMessage(throwable)加载出错 ，若加载集合数据用 mLoginView.loadFail(throwable)
     * ::全局作用域符号,修饰方法而不是变量
     */
    @Override
    public void onRequestLogin(LoginRequest request) {
        mLoginView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mLoginModel.LoginRequest(request).compose(mLoginView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mLoginView.showErrMessage(throwable),
                        () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    @Override
    public void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest) {
        mLoginView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mLoginModel.onRequestPersonalInfo(personalInfoRequest).compose(mLoginView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPersonalInfoSuccess, throwable -> mLoginView.showErrMessage(throwable),
                        () -> mLoginView.dismissLoading());
        mLoginView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(LoginResponse.class);
            if (responseData.parsedData != null) {
                LoginResponse response = (LoginResponse) responseData.parsedData;
                mLoginView.loginSuccess(response);
            }
        } else {
            mLoginView.loginFail(responseData.errorMsg);
        }
    }

    private void requestPersonalInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(PersonalInfoResponse.class);
            if (responseData.parsedData != null) {
                PersonalInfoResponse response = (PersonalInfoResponse) responseData.parsedData;
                mLoginView.personalInfoSuccess(response);
            }
        } else {
            mLoginView.personalInfoFail(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
    }


}
