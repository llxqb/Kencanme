package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.BuildConfig;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.mvp.model.MessageModel;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.ConversationControl;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.ConversationPresenterImpl;
import com.shushan.kencanme.network.RetrofitUtil;
import com.shushan.kencanme.network.networkapi.MessageApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class ConversationModule {
    private final AppCompatActivity activity;
    private final ConversationControl.ConversationView view;

    public ConversationModule(AppCompatActivity activity, ConversationControl.ConversationView view) {
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
    ConversationControl.ConversationView view() {
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
    ConversationControl.PresenterConversation providePresenterConversation(ConversationPresenterImpl presenterConversation) {
        return presenterConversation;
    }
}