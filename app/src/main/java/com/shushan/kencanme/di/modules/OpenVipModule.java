package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.BuildConfig;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.model.OpenVipModel;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipControl;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipPresenterImpl;
import com.shushan.kencanme.network.RetrofitUtil;
import com.shushan.kencanme.network.networkapi.BuyApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class OpenVipModule {
    private final AppCompatActivity activity;
    private final OpenVipControl.OpenVipView view;

    public OpenVipModule(AppCompatActivity activity, OpenVipControl.OpenVipView view) {
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
    OpenVipControl.OpenVipView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    OpenVipModel provideOpenVipModel(Gson gson, ModelTransform modelTransform) {
        return new OpenVipModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(ServerConstant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(BuyApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    OpenVipControl.PresenterOpenVip providePresenterOpenVip(OpenVipPresenterImpl openVipPresenter) {
        return openVipPresenter;
    }
}