package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.content.Context;

import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.response.UpdatePersonalInfoResponse;
import com.shushan.kencanme.entity.response.UploadImageResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.PersonalInfoModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.MultipartBody;

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
    public void onRequestPersonalInfo(UpdatePersonalInfoRequest createPersonalInfoRequest) {
        mPersonalInfoView.showLoading("加载中...");
        Disposable disposable = mPersonalInfoModel.createPersonalInfoRequest(createPersonalInfoRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UpdatePersonalInfoResponse.class);
            UpdatePersonalInfoResponse response = (UpdatePersonalInfoResponse) responseData.parsedData;
            mPersonalInfoView.updateSuccess(response);
        } else {
//            mCreatePersonalInfoView.loginFail(responseData.errorMsg);
        }
    }

    @Override
    public void uploadVideo(MultipartBody.Part uploadVideo) {
        mPersonalInfoView.showLoading("加载中...");
        Disposable disposable = mPersonalInfoModel.uploadVideoRequest(uploadVideo).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestVideoSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    @Override
    public void uploadImage(UploadImage uploadImage) {
        mPersonalInfoView.showLoading("Loading...");
        Disposable disposable = mPersonalInfoModel.uploadImageRequest(uploadImage).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestImageSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }


    private void requestVideoSuccess(ResponseData responseData) {
        mPersonalInfoView.dismissLoading();
        responseData.parseData(UploadImageResponse.class);
        if (responseData.resultCode == 0) {
            mPersonalInfoView.uploadVideoSuccess(responseData.result);
        } else {
            mPersonalInfoView.uploadVideoFail(responseData.errorMsg);
        }
    }

    private void requestImageSuccess(ResponseData responseData) {
        responseData.parseData(UploadImageResponse.class);
        if (responseData.resultCode == 0) {
            mPersonalInfoView.uploadImageSuccess(responseData.result);
        } else {
            mPersonalInfoView.uploadImageFail(responseData.errorMsg);
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
