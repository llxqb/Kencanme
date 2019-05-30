package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Context;


import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

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


//    @Override
//    public void onRequestBanner(MainBannerRequest request) {
//        mMainView.showLoading("加载中...");
//        Disposable disposable = mMainModel.bannerRequest(request).compose(mMainView.applySchedulers())
//                .subscribe(this::requestBannerSuccess, throwable -> mMainView.showErrMessage(throwable),
//                        () -> mMainView.dismissLoading());
//        mMainView.addSubscription(disposable);
//    }
//
//    private void requestBannerSuccess(ResponseData responseData) {
//        if (responseData.resultCode == 0) {
//            responseData.parseData(MainBannerResponse.class);
//            MainBannerResponse response = (MainBannerResponse) responseData.parsedData;
//            mMainView.bannerSuccess(response);
//        } else {
//            mMainView.showToast(responseData.errorDesc);
//        }
//    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }
}
