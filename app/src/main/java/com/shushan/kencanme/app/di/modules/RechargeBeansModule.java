package com.shushan.kencanme.app.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.model.ModelTransform;
import com.shushan.kencanme.app.mvp.model.ReChargeBeansModel;
import com.shushan.kencanme.app.mvp.ui.activity.pay.RechargeControl;
import com.shushan.kencanme.app.mvp.ui.activity.pay.RechargePresenterImpl;
import com.shushan.kencanme.app.network.RetrofitUtil;
import com.shushan.kencanme.app.network.networkapi.BuyApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class RechargeBeansModule {
    private final AppCompatActivity activity;
    private final RechargeControl.RechargeView view;

    public RechargeBeansModule(AppCompatActivity activity, RechargeControl.RechargeView view) {
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
    RechargeControl.RechargeView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    ReChargeBeansModel provideRechargeBeansModel(Gson gson, ModelTransform modelTransform) {
        return new ReChargeBeansModel(new RetrofitUtil.Builder()
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
    RechargeControl.PresenterRecharge providePresenterRecharge(RechargePresenterImpl rechargePresenter) {
        return rechargePresenter;
    }
}
