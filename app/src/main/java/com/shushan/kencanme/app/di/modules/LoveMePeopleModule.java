package com.shushan.kencanme.app.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.model.LoveMePeopleModel;
import com.shushan.kencanme.app.mvp.model.ModelTransform;
import com.shushan.kencanme.app.mvp.ui.activity.loveMe.LoveMePeopleControl;
import com.shushan.kencanme.app.mvp.ui.activity.loveMe.LoveMePeoplePresenterImpl;
import com.shushan.kencanme.app.network.RetrofitUtil;
import com.shushan.kencanme.app.network.networkapi.MainApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class LoveMePeopleModule {
    private final AppCompatActivity activity;
    private final LoveMePeopleControl.LoveMePeopleView view;

    public LoveMePeopleModule(AppCompatActivity activity, LoveMePeopleControl.LoveMePeopleView view) {
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
    LoveMePeopleControl.LoveMePeopleView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    LoveMePeopleModel provideLoveMePeopleModel(Gson gson, ModelTransform modelTransform) {
        return new LoveMePeopleModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(ServerConstant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MainApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    LoveMePeopleControl.PresenterLoveMePeople providePresenterLoveMePeople(LoveMePeoplePresenterImpl presenterLoveMePeople) {
        return presenterLoveMePeople;
    }
}
