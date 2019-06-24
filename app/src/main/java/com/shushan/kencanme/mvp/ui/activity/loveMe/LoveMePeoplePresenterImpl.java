package com.shushan.kencanme.mvp.ui.activity.loveMe;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.LoveMePeopleModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class LoveMePeoplePresenterImpl implements LoveMePeopleControl.PresenterLoveMePeople {

    private LoveMePeopleControl.LoveMePeopleView mLoveMePeopleView;
    private final LoveMePeopleModel mLoveMePeopleModel;
    private final Context mContext;

    @Inject
    public LoveMePeoplePresenterImpl(Context context, LoveMePeopleModel model, LoveMePeopleControl.LoveMePeopleView LoveMePeopleView) {
        mContext = context;
        mLoveMePeopleModel = model;
        mLoveMePeopleView = LoveMePeopleView;
    }

    /**
     * 好友/喜欢的人列表
     */
    @Override
    public void onRequestMyFriendList(MyFriendsRequest myFriendsRequest) {
        mLoveMePeopleView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mLoveMePeopleModel.onRequestMyFriendList(myFriendsRequest).compose(mLoveMePeopleView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mLoveMePeopleView.showErrMessage(throwable),
                        () -> mLoveMePeopleView.dismissLoading());
        mLoveMePeopleView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(MyFriendsResponse.class);
            MyFriendsResponse response = (MyFriendsResponse) responseData.parsedData;
            mLoveMePeopleView.getLoveMePeopleInfoSuccess(response);
        } else {
            mLoveMePeopleView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 喜欢
     */
    @Override
    public void onRequestLike(LikeRequest likeRequest) {
        mLoveMePeopleView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mLoveMePeopleModel.onRequestLike(likeRequest).compose(mLoveMePeopleView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestLikeSuccess, throwable -> mLoveMePeopleView.showErrMessage(throwable),
                        () -> mLoveMePeopleView.dismissLoading());
        mLoveMePeopleView.addSubscription(disposable);
    }

    private void requestLikeSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mLoveMePeopleView.getLikeSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mLoveMePeopleView.showToast(responseData.errorMsg);
        }
    }
    
    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mLoveMePeopleView = null;
    }


}
