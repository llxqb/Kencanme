package com.shushan.kencanme.app.mvp.ui.fragment.mine;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.MyAlbumRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.MyAlbumResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.MainModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * MineFragmentPresenterImpl
 */

public class MineFragmentPresenterImpl implements MineFragmentControl.mineFragmentPresenter {

    private MineFragmentControl.MineView mMineView;
    private final MainModel mMineFragmentModel;
    private final Context mContext;

    @Inject
    public MineFragmentPresenterImpl(Context context, MainModel model, MineFragmentControl.MineView mineView) {
        mContext = context;
        mMineFragmentModel = model;
        mMineView = mineView;
    }

    @Override
    public void onRequestMyAlbum(MyAlbumRequest myAlbumRequest) {
        mMineView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineFragmentModel.onRequestMyAlbum(myAlbumRequest).compose(mMineView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mMineView.showErrMessage(throwable),
                        () -> mMineView.dismissLoading());
        mMineView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            MyAlbumResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), MyAlbumResponse.class);
            mMineView.getMyAlbumSuccess(response);
        } else {
            mMineView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 获取个人信息（首页）
     */
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        mMineView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineFragmentModel.onRequestHomeUserInfo(tokenRequest).compose(mMineView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mMineView.showErrMessage(throwable),
                        () -> mMineView.dismissLoading());
        mMineView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            if (responseData.parsedData != null) {
                HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
                mMineView.homeUserInfoSuccess(response);
            }
        } else {
            mMineView.showToast(responseData.errorMsg);
        }
    }
    
    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mMineView = null;
    }


}
