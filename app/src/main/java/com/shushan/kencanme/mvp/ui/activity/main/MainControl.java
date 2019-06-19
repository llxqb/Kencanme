package com.shushan.kencanme.mvp.ui.activity.main;


import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

import io.rong.imlib.model.UserInfo;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MainControl {
    public interface MainView extends LoadDataView {
        void personalInfoSuccess(PersonalInfoResponse response);

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

    }

    public interface PresenterMain extends Presenter<MainView> {
        void onRequestPersonalInfo(PersonalInfoRequest request);

        void onRequestHomeUserInfo(TokenRequest tokenRequest);

        /**
         * 根据融云第三方id获取用户头像和昵称
         */
        UserInfo onRequestUserInfoByRid(UserInfoByRidRequest userInfoByRidRequest);
    }

}
