package com.shushan.kencanme.app.mvp.ui.activity.setting;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.FeedbackProblemRequest;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.ResponseData;
import com.shushan.kencanme.app.mvp.model.SettingModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class SettingPresenterImpl implements SettingControl.PresenterSetting {

    private SettingControl.SettingView mSettingView;
    private final SettingModel mSettingModel;
    private final Context mContext;

    @Inject
    public SettingPresenterImpl(Context context, SettingModel model, SettingControl.SettingView SettingView) {
        mContext = context;
        mSettingModel = model;
        mSettingView = SettingView;
    }


    @Override
    public void onRequestPersonalInfo(UpdatePersonalInfoRequest createPersonalInfoRequest) {
        mSettingView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mSettingModel.updatePersonalInfoRequest(createPersonalInfoRequest).compose(mSettingView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mSettingView.showErrMessage(throwable),
                        () -> mSettingView.dismissLoading());
        mSettingView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mSettingView.updateSuccess(mContext.getResources().getString(R.string.update_successfully));
        } else {
            mSettingView.updateFail(responseData.errorMsg);
        }
    }
    /**
     * 上传图片
     */
    @Override
    public void uploadImage(UploadImage uploadImage) {
        mSettingView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mSettingModel.uploadImageRequest(uploadImage).compose(mSettingView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestImageSuccess, throwable -> mSettingView.showErrMessage(throwable),
                        () -> mSettingView.dismissLoading());
        mSettingView.addSubscription(disposable);
    }

    private void requestImageSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
//            responseData.parseData(UploadImageResponse.class);
            mSettingView.uploadImageSuccess(responseData.result);
        } else {
            mSettingView.showToast(responseData.errorMsg);
        }
    }
    
    /**
     * 问题反馈
     */
    @Override
    public void onRequestFeedbackProblem(FeedbackProblemRequest feedbackProblemRequest) {
        mSettingView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mSettingModel.onRequestFeedbackProblem(feedbackProblemRequest).compose(mSettingView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestFeedbackProblemSuccess, throwable -> mSettingView.showErrMessage(throwable),
                        () -> mSettingView.dismissLoading());
        mSettingView.addSubscription(disposable);
    }

    private void requestFeedbackProblemSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mSettingView.feedbackProblemSuccess(responseData.errorMsg);
        } else {
            mSettingView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mSettingView = null;
    }


}
