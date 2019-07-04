package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;


import com.shushan.kencanme.app.entity.request.SystemMsgRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.SystemMsgResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SystemMsgControl {
    public interface SystemMsgView extends LoadDataView {
        void  getSystemMsgSuccess(SystemMsgResponse systemMsgResponse);

        void getDeleteMsgSuccess();
    }

    public interface PresenterSystemMsg extends Presenter<SystemMsgView> {
        /**
         * 系统消息列表
         */
        void onRequestSystemMsgList(SystemMsgRequest systemMsgRequest);
        /**
         * 清除系统消息
         */
        void onRequestDeleteSystemMsg(TokenRequest tokenRequest);
    }


}
