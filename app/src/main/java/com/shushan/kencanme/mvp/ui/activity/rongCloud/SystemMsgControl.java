package com.shushan.kencanme.mvp.ui.activity.rongCloud;


import com.shushan.kencanme.entity.request.SystemMsgRequest;
import com.shushan.kencanme.entity.response.SystemMsgResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SystemMsgControl {
    public interface SystemMsgView extends LoadDataView {
        void  getSystemMsgSuccess(SystemMsgResponse systemMsgResponse);
    }

    public interface PresenterSystemMsg extends Presenter<SystemMsgView> {
        /**
         * 系统消息列表
         */
        void onRequestSystemMsgList(SystemMsgRequest systemMsgRequest);
    }


}
