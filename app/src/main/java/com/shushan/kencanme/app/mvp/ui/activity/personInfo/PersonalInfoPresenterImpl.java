package com.shushan.kencanme.app.mvp.ui.activity.personInfo;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.entity.response.UploadImageResponse;
import com.shushan.kencanme.app.entity.response.UploadVideoResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.PersonalInfoModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

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
    public void updatePersonalInfo(UpdatePersonalInfoRequest createPersonalInfoRequest) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.updatePersonalInfoRequest(createPersonalInfoRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
//            responseData.parseData(UpdatePersonalInfoResponse.class);
//            UpdatePersonalInfoResponse response = (UpdatePersonalInfoResponse) responseData.parsedData;
            mPersonalInfoView.updateSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void uploadVideo(MultipartBody.Part uploadVideo) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.uploadVideoRequest(uploadVideo).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestVideoSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    @Override
    public void uploadImage(UploadImage uploadImage) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.uploadImageRequest(uploadImage).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestImageSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    @Override
    public void updateMyAlbum(UpdateAlbumRequest updateAlbumRequest) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.updateMyAlbum(updateAlbumRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::updateMyAlbumSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);

    }

    private void requestVideoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UploadVideoResponse.class);
            if (responseData.parsedData != null) {
                UploadVideoResponse response = (UploadVideoResponse) responseData.parsedData;
                mPersonalInfoView.uploadVideoSuccess(response);
            }
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }

    private void requestImageSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UploadImageResponse.class);
            if (responseData.parsedData != null) {
                UploadImageResponse response = (UploadImageResponse) responseData.parsedData;
                mPersonalInfoView.uploadImageSuccess(response.getUrl());
            }
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }

    private void updateMyAlbumSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mPersonalInfoView.updateMyAlbumSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 请求个人信息(我的)
     */
    @Override
    public void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.onRequestPersonalInfo(personalInfoRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPersonalInfoSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    private void requestPersonalInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(PersonalInfoResponse.class);
            if (responseData.parsedData != null) {
                PersonalInfoResponse response = (PersonalInfoResponse) responseData.parsedData;
                mPersonalInfoView.personalInfoSuccess(response);
            }
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
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
