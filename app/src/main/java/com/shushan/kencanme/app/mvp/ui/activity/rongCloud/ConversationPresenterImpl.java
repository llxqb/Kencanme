package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;

import android.content.Context;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.request.UseBeansRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.UploadImageResponse;
import com.shushan.kencanme.app.entity.response.UserInfoByRidResponse;
import com.shushan.kencanme.app.entity.response.UserRelationResponse;
import com.shushan.kencanme.app.help.RetryWithDelay;
import com.shushan.kencanme.app.mvp.model.MessageModel;
import com.shushan.kencanme.app.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class ConversationPresenterImpl implements ConversationControl.PresenterConversation {

    private ConversationControl.ConversationView mConversationView;
    private final MessageModel mMessageModel;
    private final Context mContext;

    @Inject
    public ConversationPresenterImpl(Context context, MessageModel model, ConversationControl.ConversationView ConversationView) {
        mContext = context;
        mMessageModel = model;
        mConversationView = ConversationView;
    }

    @Override
    public void uploadImage(UploadImage uploadImage) {
        mConversationView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMessageModel.uploadImageRequest(uploadImage).compose(mConversationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestImageSuccess, throwable -> mConversationView.showErrMessage(throwable),
                        () -> mConversationView.dismissLoading());
        mConversationView.addSubscription(disposable);
    }

    private void requestImageSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UploadImageResponse.class);
            mConversationView.uploadImageSuccess(responseData.result);
        } else {
            mConversationView.uploadImageFail(responseData.errorMsg);
        }
    }

    @Override
    public void onRequestUseBeans(UseBeansRequest useBeansRequest) {
//        mConversationView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMessageModel.onRequestUseBeans(useBeansRequest).compose(mConversationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::useBeansRequestSuccess, throwable -> mConversationView.showErrMessage(throwable),
                        () -> mConversationView.dismissLoading());
        mConversationView.addSubscription(disposable);
    }

    private void useBeansRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            mConversationView.UseBeansSuccess(mContext.getResources().getString(R.string.success));
        } else {
            mConversationView.uploadImageFail(responseData.errorMsg);
        }
    }

    /**
     * 获取个人信息（首页）
     */
    @Override
    public void onRequestHomeUserInfo(TokenRequest tokenRequest) {
        Disposable disposable = mMessageModel.onRequestHomeUserInfo(tokenRequest).compose(mConversationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestHomeUserInfoSuccess, throwable -> mConversationView.showErrMessage(throwable),
                        () -> mConversationView.dismissLoading());
        mConversationView.addSubscription(disposable);
    }

    private void requestHomeUserInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(HomeUserInfoResponse.class);
            if (responseData.parsedData != null) {
                HomeUserInfoResponse response = (HomeUserInfoResponse) responseData.parsedData;
                mConversationView.homeUserInfoSuccess(response);
            }
        } else {
            mConversationView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 根据融云第三方id获取用户头像和昵称
     */
    @Override
    public void onRequestUserInfoByRid(UserInfoByRidRequest userInfoByRidRequest) {
        Disposable disposable = mMessageModel.onRequestUserInfoByRid(userInfoByRidRequest).compose(mConversationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUserInfoByRidSuccess, throwable -> mConversationView.showErrMessage(throwable),
                        () -> mConversationView.dismissLoading());
        mConversationView.addSubscription(disposable);
    }

    private void requestUserInfoByRidSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UserInfoByRidResponse.class);
            if (responseData.parsedData != null) {
                UserInfoByRidResponse response = (UserInfoByRidResponse) responseData.parsedData;
                mConversationView.getUserInfoSuccess(response);
            }
        } else {
            mConversationView.showToast(responseData.errorMsg);
        }
    }

    /**
     * 根据融云第三方id获取关系
     */
    @Override
    public void onRequestUserRelation(UserInfoByRidRequest userInfoByRidRequest) {
        Disposable disposable = mMessageModel.onRequestUserRelation(userInfoByRidRequest).compose(mConversationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestUserRelationSuccess, throwable -> mConversationView.showErrMessage(throwable),
                        () -> mConversationView.dismissLoading());
        mConversationView.addSubscription(disposable);
    }

    private void requestUserRelationSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(UserRelationResponse.class);
            if (responseData.parsedData != null) {
                UserRelationResponse response = (UserRelationResponse) responseData.parsedData;
                mConversationView.getUserRelationSuccess(response);
            }
        } else {
            mConversationView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mConversationView = null;
    }


}
