package com.shushan.kencanme.app.mvp.ui.activity.loveMe;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.MyFriendsRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.response.MyFriendsResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.LoveMePeopleModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

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
            if (responseData.parsedData != null) {
                MyFriendsResponse response = (MyFriendsResponse) responseData.parsedData;
                mLoveMePeopleView.getLoveMePeopleInfoSuccess(response);
            }
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

    /**
     *统计今日密聊次数
     */
    @Override
    public void onRequestChatNum(RequestFreeChat requestFreeChat) {
        mLoveMePeopleView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mLoveMePeopleModel.onRequestChatNum(requestFreeChat).compose(mLoveMePeopleView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestChatNumSuccess, throwable -> mLoveMePeopleView.showErrMessage(throwable),
                        () -> mLoveMePeopleView.dismissLoading());
        mLoveMePeopleView.addSubscription(disposable);
    }

    private void requestChatNumSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mLoveMePeopleView.chatNumSuccess();
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
