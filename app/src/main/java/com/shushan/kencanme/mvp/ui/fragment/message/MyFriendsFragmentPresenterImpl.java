package com.shushan.kencanme.mvp.ui.fragment.message;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class MyFriendsFragmentPresenterImpl implements MyFriendsFragmentControl.MyFriendsFragmentPresenter {

    private MyFriendsFragmentControl.MyFriendsView mMyFriendsView;
    private final MainModel mHomeFragmentModel;
    private final Context mContext;

    @Inject
    public MyFriendsFragmentPresenterImpl(Context context, MainModel model, MyFriendsFragmentControl.MyFriendsView MyFriendsView) {
        mContext = context;
        mHomeFragmentModel = model;
        mMyFriendsView = MyFriendsView;
    }

    /**
     * 好友/喜欢的人列表
     */
    @Override
    public void onRequestMyFriendList(MyFriendsRequest myFriendsRequest) {
        mMyFriendsView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestMyFriendList(myFriendsRequest).compose(mMyFriendsView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mMyFriendsView.showErrMessage(throwable),
                        () -> mMyFriendsView.dismissLoading());
        mMyFriendsView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(MyFriendsResponse.class);
            MyFriendsResponse response = (MyFriendsResponse) responseData.parsedData;
            mMyFriendsView.getMyFriendsListInfoSuccess(response);
        } else {
            mMyFriendsView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mMyFriendsView = null;
    }


}
