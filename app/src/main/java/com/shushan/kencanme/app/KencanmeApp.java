package com.shushan.kencanme.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.shushan.kencanme.app.di.components.AppComponent;
import com.shushan.kencanme.app.di.components.DaggerAppComponent;
import com.shushan.kencanme.app.di.modules.AppModule;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.CustomizeMessage;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.CustomizeMessageItemProvider;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.commonsdk.UMConfigure;

import javax.inject.Inject;

import io.rong.imkit.RongIM;

import static com.shushan.kencanme.app.mvp.utils.SystemUtils.getCurProcessName;


/**
 * Created by li.liu on 18/1/8.
 * Application
 */
public class KencanmeApp extends Application {

    private AppComponent mAppComponent;
    public Context mContext;
    @SuppressLint("StaticFieldLeak")
    public static CustomizeMessageItemProvider mCustomizeMessageItemProvider;
    @Inject
    Gson mGson;
//    @Inject
//    BuProcessor mBuProcessor;

    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);//必须有
        //初始化内存泄漏检查工具
        initLeaks();
        //初始化融云
        initRongYun();
        //友盟init
        initUM();
        logActivatedAppEvent();
    }

    /**
     * 记录facebook活跃用户
     */
    public void logActivatedAppEvent () {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ACTIVATED_APP);
    }

    /**
     * 内存泄漏检查工具
     */
    private void initLeaks() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    /**
     * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
     * io.rong.push 为融云 push 进程名称，不可修改。
     */
    public void initRongYun() {
        if (getApplicationInfo().packageName
                .equals(getCurProcessName(getApplicationContext()))
                || "io.rong.push"
                .equals(getCurProcessName(getApplicationContext()))) {
            RongIM.init(this);
            //注册自定义消息
            RongIM.registerMessageType(CustomizeMessage.class);
            //注册消息模板
            mCustomizeMessageItemProvider = new CustomizeMessageItemProvider();
            RongIM.registerMessageTemplate(mCustomizeMessageItemProvider);
        }
    }

    /**
     * 初始化友盟
     * 用到了友盟分享
     */
    private void initUM() {
        UMConfigure.init(this, ServerConstant.UM_APP_KEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }
}
