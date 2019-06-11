package com.shushan.kencanme.mvp.ui.activity.login;


import com.shushan.kencanme.entity.request.LoginRequest;
import com.shushan.kencanme.entity.response.LoginResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class LoginControl {
    public interface LoginView extends LoadDataView {
        void loginSuccess(LoginResponse response);
        void loginFail(String errorMsg);

    }

    public interface PresenterLogin extends Presenter<LoginView> {
        void onRequestLogin(LoginRequest loginRequest);
    }


}
