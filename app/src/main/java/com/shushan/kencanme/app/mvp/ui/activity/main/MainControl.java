package com.shushan.kencanme.app.mvp.ui.activity.main;


import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadDeviceRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.entity.response.MessageIdResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

import io.rong.imlib.model.UserInfo;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MainControl {
    public interface MainView extends LoadDataView {

        // void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);
        void messageIdSuccess(MessageIdResponse messageIdResponse);
    }

    public interface PresenterMain extends Presenter<MainView> {

        /**
         * 根据融云第三方id获取用户头像和昵称
         */
        UserInfo onRequestUserInfoByRid(UserInfoByRidRequest userInfoByRidRequest);

        /**
         * 查看用户嗨豆查看私密照片message_id
         */
        void onRequestMessageId(TokenRequest tokenRequest);

        /**
         * 上传设备接口  后台做统计功能
         */
        void onUploadDevice(UploadDeviceRequest uploadDeviceRequest);
    }

}
