package com.shushan.kencanme.di.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.help.GoogleLoginHelper;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.help.Sharedprefence;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.utils.SharePreferenceUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * AppModule
 * Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例时候，就知道从哪里去找到需要的依赖。
 * 在Modules中， @Provides 我们定义的方法用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
 */
@Module
public class AppModule {

    private KencanmeApp application;

    public AppModule(KencanmeApp app) {
        this.application = app;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    ModelTransform provideModelTransform() {
        return new ModelTransform();
    }

    @Provides
    @Singleton
    ImageLoaderHelper imageLoaderHelper() {
        return new ImageLoaderHelper(application);
    }

    @Provides
    @Singleton
    GoogleLoginHelper googleLoginHelper() {
        return new GoogleLoginHelper();
    }

    @Provides
    @Singleton
    Sharedprefence sharePreference() {
        return new Sharedprefence(application);
    }

    @Provides
    @Singleton
    SharePreferenceUtil sharePreferenceUtil(Sharedprefence sharedprefence) {
        return new SharePreferenceUtil(application, sharedprefence);
    }

//    @Provides
//    @Singleton
//    BuProcessor buProcessor(SharePreferenceUtil sharePerferenceUtil) {
//        return new BuProcessor(application, sharePerferenceUtil);
//    }
//
//    @Provides
//    @Singleton
//    DaoSession provideDaoSession(DbHelper dbHelper) {
//        return new DaoMaster(dbHelper.getWritableDatabase()).newSession();
//    }
//
//    @Provides
//    @Singleton
//    AMapLocationClient provideAMapLocationClient() {
//        AMapLocationListener mLocationListener = new MyLocationListener(application);
//        AMapLocationClient client = new AMapLocationClient(application);
//        client.setLocationListener(mLocationListener);
//        return client;
//    }
//
//    @Provides
//    @Singleton
//    AMapLocationClientOption AMapLocationClientOptionProvide() {
//        AMapLocationClientOption option = new AMapLocationClientOption();
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        option.setNeedAddress(true);
//        option.setLocationCacheEnable(true);
//        option.setHttpTimeOut(35000);
//        option.setWifiScan(true);
//        option.setInterval(30000);
//        return option;
//    }
}
