package com.shushan.kencanme.mvp.ui.activity.rongCloud;


import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.SystemMsgNewResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ConversationFragmentControl {

    public interface ConversationView extends LoadDataView {
       void  getSystemMsgNewInfoSuccess(SystemMsgNewResponse systemMsgNewResponse);
    }

    public interface ConversationFragmentPresenter extends Presenter<ConversationView> {
        /**
         * 最新一条系统消息
         */
        void onRequestSystemMsgNew(TokenRequest tokenRequest);

    }


}
