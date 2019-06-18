package com.shushan.kencanme.mvp.ui.activity.setting;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.ResponseData;
import com.shushan.kencanme.mvp.model.SettingModel;

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
            mSettingView.updateSuccess("update success");
        } else {
            mSettingView.updateFail(responseData.errorMsg);
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
