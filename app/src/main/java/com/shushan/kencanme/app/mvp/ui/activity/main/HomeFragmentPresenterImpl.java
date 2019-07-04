package com.shushan.kencanme.app.mvp.ui.activity.main;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.DialogBuyBean;
import com.shushan.kencanme.app.entity.request.BuyExposureTimeRequest;
import com.shushan.kencanme.app.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.LikeResponse;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.MainModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class HomeFragmentPresenterImpl implements HomeFragmentControl.homeFragmentPresenter {

    private HomeFragmentControl.HomeView mHomeView;
    private final MainModel mHomeFragmentModel;
    private final Context mContext;

    @Inject
    public HomeFragmentPresenterImpl(Context context, MainModel model, HomeFragmentControl.HomeView homeView) {
        mContext = context;
        mHomeFragmentModel = model;
        mHomeView = homeView;
    }

    /**
     * new RetryWithDelay(3, 3000) 总共重试3次，重试间隔3000毫秒
     * subscribe订阅
     * mLoginView.showErrMessage(throwable)加载出错 ，若加载集合数据用 mLoginView.loadFail(throwable)
     * ::全局作用域符号,修饰方法而不是变量
     */
    @Override
    public void onRequestInfo(HomeFragmentRequest request) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestInfo(request).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeFragmentResponse.class);
            if (responseData.parsedData != null) {
                HomeFragmentResponse response = (HomeFragmentResponse) responseData.parsedData;
                mHomeView.getInfoSuccess(response);
            }
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 喜欢
     */
    @Override
    public void onRequestLike(LikeRequest likeRequest) {
//        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestLike(likeRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestLikeSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestLikeSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(LikeResponse.class);
            if (responseData.parsedData != null) {
                LikeResponse response = (LikeResponse) responseData.parsedData;
                mHomeView.getLikeSuccess(response);
            }
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 曝光次数嗨豆购买规则
     */
    @Override
    public void onRequestBuyExposureTimeList(TokenRequest tokenRequest) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestBuyExposureTimeList(tokenRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestBuyExposureTimeListSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestBuyExposureTimeListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            DialogBuyBean response = new Gson().fromJson(responseData.mJsonObject.toString(), DialogBuyBean.class);
            mHomeView.getBuyExposureTimeList(response);
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 嗨豆购买曝光次数
     */
    @Override
    public void onRequestBuyExposureTime(BuyExposureTimeRequest buyExposureTimeRequest) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestBuyExposureTime(buyExposureTimeRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestBuyExposureTimeSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }
    private void requestBuyExposureTimeSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
//            DialogBuyBean response = new Gson().fromJson(responseData.mJsonObject.toString(), DialogBuyBean.class);
            mHomeView.getBuyExposureTime(mContext.getResources().getString(R.string.success));
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 获取个人信息
     */
    @Override
    public void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestPersonalInfo(personalInfoRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPersonalInfoSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestPersonalInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(PersonalInfoResponse.class);
            if (responseData.parsedData != null) {
                PersonalInfoResponse response = (PersonalInfoResponse) responseData.parsedData;
                mHomeView.personalInfoSuccess(response);
            }
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 获取个人信息（首页）
     */
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestHomeUserInfo(tokenRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            if (responseData.parsedData != null) {
                HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
                mHomeView.homeUserInfoSuccess(response);
            }
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }

    /**
     *进行超级曝光
     */
    @Override
    public void onRequestExposure(TokenRequest tokenRequest) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestExposure(tokenRequest).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestExposureSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestExposureSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mHomeView.exposureSuccess(mContext.getResources().getString(R.string.msg_super_expsure_success));
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }
    /**
     *统计今日密聊次数
     */
    @Override
    public void onRequestChatNum(RequestFreeChat requestFreeChat) {
        mHomeView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestChatNum(requestFreeChat).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestChatNumSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestChatNumSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mHomeView.chatNumSuccess();
        } else {
            mHomeView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mHomeView = null;
    }


}
