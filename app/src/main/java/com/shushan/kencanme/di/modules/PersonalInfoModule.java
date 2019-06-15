package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.BuildConfig;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.model.PersonalInfoModel;
import com.shushan.kencanme.mvp.ui.activity.personInfo.PersonalInfoControl;
import com.shushan.kencanme.mvp.ui.activity.personInfo.PersonalInfoPresenterImpl;
import com.shushan.kencanme.network.RetrofitUtil;
import com.shushan.kencanme.network.networkapi.PersonalInfoApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class PersonalInfoModule {
    private final AppCompatActivity activity;
    private final PersonalInfoControl.PersonalInfoView view;

    public PersonalInfoModule(AppCompatActivity activity, PersonalInfoControl.PersonalInfoView view) {
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
    PersonalInfoControl.PersonalInfoView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    PersonalInfoModel providePersonalInfoModel(Gson gson, ModelTransform modelTransform) {
        return new  PersonalInfoModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(ServerConstant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(PersonalInfoApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    PersonalInfoControl.PresenterPersonalInfo providePresenterPersonalInfo(PersonalInfoPresenterImpl presenterPersonalInfo) {
        return presenterPersonalInfo;
    }

}
