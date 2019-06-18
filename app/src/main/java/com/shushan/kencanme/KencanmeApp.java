package com.shushan.kencanme;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.di.components.AppComponent;
import com.shushan.kencanme.di.components.DaggerAppComponent;
import com.shushan.kencanme.di.modules.AppModule;

import javax.inject.Inject;

import io.rong.imkit.RongIM;

import static com.shushan.kencanme.mvp.utils.SystemUtils.getCurProcessName;

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
        //初始化融云
        initRongYun();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void initRongYun(){
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName
                .equals(getCurProcessName(getApplicationContext()))
                || "io.rong.push"
                .equals(getCurProcessName(getApplicationContext()))) {
            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
//            RongIM.setUserInfoProvider(new CustomerUserInfoProvider(), true);
        }
    }
}
