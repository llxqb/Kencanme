package com.shushan.kencanme.app.mvp.ui.activity.login;


import com.shushan.kencanme.app.entity.request.LoginRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.LoginResponse;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class LoginControl {
    public interface LoginView extends LoadDataView {
        void loginSuccess(LoginResponse response);
        void loginFail(String errorMsg);

        void personalInfoSuccess(PersonalInfoResponse personalInfoResponse);
        void personalInfoFail(String errorMsg);
    }

    public interface PresenterLogin extends Presenter<LoginView> {
        void onRequestLogin(LoginRequest loginRequest);

        void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest);
    }


}
