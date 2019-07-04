package com.shushan.kencanme.mvp.ui.activity.reportUser;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.ReportUserRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.PersonalInfoModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class ReportUserPresenterImpl implements ReportUserControl.PresenterReportUser {

    private ReportUserControl.ReportUserView mReportUserView;
    private final PersonalInfoModel mPersonalInfoModel;
    private final Context mContext;

    @Inject
    public ReportUserPresenterImpl(Context context, PersonalInfoModel model, ReportUserControl.ReportUserView ReportUserView) {
        mContext = context;
        mPersonalInfoModel = model;
        mReportUserView = ReportUserView;
    }


    @Override
    public void onRequestReportUser(ReportUserRequest reportUserRequest) {
        mReportUserView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.reportUserRequest(reportUserRequest).compose(mReportUserView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mReportUserView.showErrMessage(throwable),
                        () -> mReportUserView.dismissLoading());
        mReportUserView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
//            responseData.parseData(UpdatePersonalInfoResponse.class);
//            UpdatePersonalInfoResponse response = (UpdatePersonalInfoResponse) responseData.parsedData;
            mReportUserView.reportUserSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mReportUserView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void uploadImage(UploadImage uploadImage) {
        mReportUserView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mPersonalInfoModel.uploadImageRequest(uploadImage).compose(mReportUserView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestImageSuccess, throwable -> mReportUserView.showErrMessage(throwable),
                        () -> mReportUserView.dismissLoading());
        mReportUserView.addSubscription(disposable);
    }

    private void requestImageSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
//            responseData.parseData(UploadImageResponse.class);
            mReportUserView.uploadImageSuccess(responseData.result);
        } else {
            mReportUserView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mReportUserView = null;
    }


}
