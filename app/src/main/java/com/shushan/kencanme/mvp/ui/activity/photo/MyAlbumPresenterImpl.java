package com.shushan.kencanme.mvp.ui.activity.photo;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.DeleteMyAlbumRequest;
import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MyAlbumModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class MyAlbumPresenterImpl implements MyAlbumControl.PresenterMyAlbum {

    private MyAlbumControl.MyAlbumView mMyAlbumView;
    private final MyAlbumModel mMyAlbumModel;
    private final Context mContext;

    @Inject
    public MyAlbumPresenterImpl(Context context, MyAlbumModel model, MyAlbumControl.MyAlbumView MyAlbumView) {
        mContext = context;
        mMyAlbumModel = model;
        mMyAlbumView = MyAlbumView;
    }

    @Override
    public void deleteMyAlbum(DeleteMyAlbumRequest deleteMyAlbumRequest) {
        mMyAlbumView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMyAlbumModel.deleteAlbumRequest(deleteMyAlbumRequest).compose(mMyAlbumView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mMyAlbumView.showErrMessage(throwable),
                        () -> mMyAlbumView.dismissLoading());
        mMyAlbumView.addSubscription(disposable);
    }

    @Override
    public void onRequestMyAlbum(MyAlbumRequest myAlbumRequest) {
        mMyAlbumView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMyAlbumModel.onRequestMyAlbum(myAlbumRequest).compose(mMyAlbumView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestMyAlbumSuccess, throwable -> mMyAlbumView.showErrMessage(throwable),
                        () -> mMyAlbumView.dismissLoading());
        mMyAlbumView.addSubscription(disposable);
    }
    
    
    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mMyAlbumView.deleteSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mMyAlbumView.showToast(responseData.errorMsg);
        }
    }


    private void requestMyAlbumSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            MyAlbumResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), MyAlbumResponse.class);
            if(response!=null){
                mMyAlbumView.getMyAlbumSuccess(response);
            }
        } else {
            mMyAlbumView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mMyAlbumView = null;
    }


}
