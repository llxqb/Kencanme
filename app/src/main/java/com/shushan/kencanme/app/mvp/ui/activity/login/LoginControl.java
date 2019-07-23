package com.shushan.kencanme.app.mvp.ui.activity.login;


import com.shushan.kencanme.app.entity.request.FacebookLoginRequest;
import com.shushan.kencanme.app.entity.request.LoginRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.FacebookLoginResponse;
import com.shushan.kencanme.app.entity.response.LoginResponse;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class LoginControl {
    public interface LoginView extends LoadDataView {
        void googleLoginSuccess(LoginResponse response);

        void personalInfoSuccess(PersonalInfoResponse personalInfoResponse);

        void facebookLoginSuccess(FacebookLoginResponse facebookLoginResponse);
    }

    public interface PresenterLogin extends Presenter<LoginView> {
        /**
         * Google登录
         */
        void onRequestLogin(LoginRequest loginRequest);

        void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest);
        /**
         * facebook登录
         */
        void onRequestLoginFacebook(FacebookLoginRequest facebookLoginRequest);
    }


}
