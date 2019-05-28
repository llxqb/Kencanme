package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.BuildConfig;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.entity.Constant;
import com.shushan.kencanme.mvp.model.LoginModel;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.ui.activity.login.LoginControl;
import com.shushan.kencanme.mvp.ui.activity.login.LoginPresenterImpl;
import com.shushan.kencanme.network.RetrofitUtil;
import com.shushan.kencanme.network.networkapi.LoginApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class LoginModule {
    private final AppCompatActivity activity;
    private final LoginControl.LoginView view;

    public LoginModule(AppCompatActivity activity, LoginControl.LoginView view) {
        this.activity = activity;
        this.view = view;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    LoginControl.LoginView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    LoginModel provideLoginModel(Gson gson, ModelTransform modelTransform) {
        return new LoginModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(Constant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(LoginApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    LoginControl.PresenterLogin providePresenterLogin(LoginPresenterImpl presenterLogin) {
        return presenterLogin;
    }
}
