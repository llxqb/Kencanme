package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.BuildConfig;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.mvp.model.MessageModel;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.SystemMsgControl;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.SystemMsgPresenterImpl;
import com.shushan.kencanme.network.RetrofitUtil;
import com.shushan.kencanme.network.networkapi.MessageApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class SystemMsgModule {
    private final AppCompatActivity activity;
    private final SystemMsgControl.SystemMsgView view;

    public SystemMsgModule(AppCompatActivity activity, SystemMsgControl.SystemMsgView view) {
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
    SystemMsgControl.SystemMsgView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MessageModel provideLoveMePeopleModel(Gson gson, ModelTransform modelTransform) {
        return new MessageModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(ServerConstant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MessageApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    SystemMsgControl.PresenterSystemMsg providePresenterSystemMsg(SystemMsgPresenterImpl presenterSystemMsg) {
        return presenterSystemMsg;
    }
}
