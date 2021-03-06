package com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.BlackUserRequest;
import com.shushan.kencanme.app.entity.request.DeleteUserRequest;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.LookAlbumByBeansRequest;
import com.shushan.kencanme.app.entity.request.LookContactTypeRequest;
import com.shushan.kencanme.app.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.LikeResponse;
import com.shushan.kencanme.app.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.RecommendUserInfoModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * RecommendUserInfoPresenterImpl
 */

public class RecommendUserInfoPresenterImpl implements RecommendUserInfoControl.PresenterRecommendUserInfo {

    private RecommendUserInfoControl.RecommendUserInfoView mRecommendUserInfoView;
    private final RecommendUserInfoModel mRecommendUserInfoModel;
    private final Context mContext;

    @Inject
    public RecommendUserInfoPresenterImpl(Context context, RecommendUserInfoModel model, RecommendUserInfoControl.RecommendUserInfoView loginView) {
        mContext = context;
        mRecommendUserInfoModel = model;
        mRecommendUserInfoView = loginView;
    }

    /**
     * new RetryWithDelay(3, 3000) 总共重试3次，重试间隔3000毫秒
     * subscribe订阅
     * mLoginView.showErrMessage(throwable)加载出错 ，若加载集合数据用 mLoginView.loadFail(throwable)
     * ::全局作用域符号,修饰方法而不是变量
     */
    @Override
    public void onRequestRecommendUserInfo(RecommendUserInfoRequest request) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.recommendUserInfoRequest(request).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }


    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(RecommendUserInfoResponse.class);
            if (responseData.parsedData != null) {
                RecommendUserInfoResponse response = (RecommendUserInfoResponse) responseData.parsedData;
                mRecommendUserInfoView.getRecommendUserInfoSuccess(response);
            }
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 加入黑名单
     */
    @Override
    public void onRequestBlackUser(BlackUserRequest blackUserRequest) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestBlackUser(blackUserRequest).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestBlackUserSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }


    private void requestBlackUserSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRecommendUserInfoView.getBlackUserSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 删除好友
     */
    @Override
    public void onRequestDeleteUser(DeleteUserRequest deleteUserRequest) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestDeleteUser(deleteUserRequest).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDeleteUserSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }

    private void requestDeleteUserSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRecommendUserInfoView.getDeleteUserSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 喜欢
     */
    @Override
    public void onRequestLike(LikeRequest likeRequest) {
//        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestLike(likeRequest).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestLikeSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }

    private void requestLikeSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(LikeResponse.class);
            if (responseData.parsedData != null) {
                LikeResponse response = (LikeResponse) responseData.parsedData;
                mRecommendUserInfoView.getLikeSuccess(response);
            }
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 查看联系方式
     */
    @Override
    public void onRequestContact(LookContactTypeRequest lookContactTypeRequest) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestContact(lookContactTypeRequest).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestContactSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }

    private void requestContactSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRecommendUserInfoView.getContactSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 查看联系方式
     */
    @Override
    public void onRequestAlbumByBeans(LookAlbumByBeansRequest lookAlbumByBeansRequest) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestAlbumByBeans(lookAlbumByBeansRequest).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestAlbumByBeansSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }

    private void requestAlbumByBeansSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRecommendUserInfoView.getAlbumByBeansSuccess("");
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 获取个人信息（首页）
     */
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestHomeUserInfo(tokenRequest).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            if (responseData.parsedData != null) {
                HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
                mRecommendUserInfoView.getHomeUserInfoSuccess(response);
            }
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 统计今日密聊次数
     */
    @Override
    public void onRequestChatNum(RequestFreeChat requestFreeChat) {
        mRecommendUserInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mRecommendUserInfoModel.onRequestChatNum(requestFreeChat).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestChatNumSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
                        () -> mRecommendUserInfoView.dismissLoading());
        mRecommendUserInfoView.addSubscription(disposable);
    }

    private void requestChatNumSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mRecommendUserInfoView.chatNumSuccess();
        } else {
            mRecommendUserInfoView.showToast(responseData.errorMsg);
        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mRecommendUserInfoView = null;
    }


}
