package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.SystemMsgNewResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class ConversationFragmentPresenterImpl implements ConversationFragmentControl.ConversationFragmentPresenter {

    private ConversationFragmentControl.ConversationView mConversationView;
    private final MainModel mHomeFragmentModel;
    private final Context mContext;

    @Inject
    public ConversationFragmentPresenterImpl(Context context, MainModel model, ConversationFragmentControl.ConversationView ConversationView) {
        mContext = context;
        mHomeFragmentModel = model;
        mConversationView = ConversationView;
    }

    /**
     * 最新一条系统消息
     */
    @Override
    public void onRequestSystemMsgNew(TokenRequest tokenRequest) {
        mConversationView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mHomeFragmentModel.onRequestSystemMsgNew(tokenRequest).compose(mConversationView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mConversationView.showErrMessage(throwable),
                        () -> mConversationView.dismissLoading());
        mConversationView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(SystemMsgNewResponse.class);
            if (responseData.parsedData != null) {
                SystemMsgNewResponse response = (SystemMsgNewResponse) responseData.parsedData;
                mConversationView.getSystemMsgNewInfoSuccess(response);
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
