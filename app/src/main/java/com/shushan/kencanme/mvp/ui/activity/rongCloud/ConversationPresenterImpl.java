package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Context;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.response.UploadImageResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MessageModel;
import com.shushan.kencanme.mvp.model.ResponseData;

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
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mConversationView = null;
    }


}
