package com.shushan.kencanme;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.di.components.AppComponent;
import com.shushan.kencanme.di.components.DaggerAppComponent;
import com.shushan.kencanme.di.modules.AppModule;

import javax.inject.Inject;

/**
 * Created by li.liu on 18/1/8.
 * Application
 */
public class KencanmeApp extends Application {

    private AppComponent mAppComponent;
    public Context mContext;
    @Inject
    Gson mGson;
//    @Inject
//    BuProcessor mBuProcessor;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);//必须有
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
