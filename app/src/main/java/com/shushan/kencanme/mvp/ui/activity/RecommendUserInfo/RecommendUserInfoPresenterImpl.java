package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.RecommendUserInfoModel;
import com.shushan.kencanme.mvp.model.ResponseData;

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
            RecommendUserInfoResponse response = (RecommendUserInfoResponse) responseData.parsedData;
            mRecommendUserInfoView.getRecommendUserInfoSuccess(response);
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
