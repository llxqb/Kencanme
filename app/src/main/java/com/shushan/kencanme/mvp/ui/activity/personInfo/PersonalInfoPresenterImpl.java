package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.content.Context;

import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.response.LoginResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.PersonalInfoModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class PersonalInfoPresenterImpl implements PersonalInfoControl.PresenterPersonalInfo {

    private PersonalInfoControl.PersonalInfoView mPersonalInfoView;
    private final PersonalInfoModel mPersonalInfoModel;
    private final Context mContext;

    @Inject
    public PersonalInfoPresenterImpl(Context context, PersonalInfoModel model, PersonalInfoControl.PersonalInfoView personalInfoView) {
        mContext = context;
        mPersonalInfoModel = model;
        mPersonalInfoView = personalInfoView;
    }


    @Override
    public void onRequestPersonalInfo(PersonalInfoRequest createPersonalInfoRequest) {
        mPersonalInfoView.showLoading("加载中...");
        Disposable disposable = mPersonalInfoModel.createPersonalInfoRequest(createPersonalInfoRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(LoginResponse.class);
            PersonalInfoResponse response = (PersonalInfoResponse) responseData.parsedData;
            mPersonalInfoView.updateSuccess(response);
        } else {
//            mCreatePersonalInfoView.loginFail(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mPersonalInfoView = null;
    }


}
