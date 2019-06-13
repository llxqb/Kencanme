package com.shushan.kencanme.mvp.ui.fragment.mine;

import android.content.Context;

import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ResponseData;

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
        mMineView.showLoading("加载中...");
        Disposable disposable = mMineFragmentModel.onRequestMyAlbum(myAlbumRequest).compose(mMineView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mMineView.showErrMessage(throwable),
                        () -> mMineView.dismissLoading());
        mMineView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(MyAlbumResponse.class);
            MyAlbumResponse response = (MyAlbumResponse) responseData.parsedData;
            mMineView.getMyAlbumSuccess(response);
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
