package com.shushan.kencanme;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.di.components.AppComponent;
import com.shushan.kencanme.di.components.DaggerAppComponent;
import com.shushan.kencanme.di.modules.AppModule;
import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.help.CrashHandler;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.CustomizeMessage;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.CustomizeMessageItemProvider;
import com.umeng.commonsdk.UMConfigure;

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
    @SuppressLint("StaticFieldLeak")
    public static CustomizeMessageItemProvider mCustomizeMessageItemProvider;
    @Inject
    Gson mGson;
//    @Inject
//    BuProcessor mBuProcessor;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        mContext = this.getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);//必须有
        //初始化融云
        initRongYun();
        //友盟init
        initUM();
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
//        UMConfigure.setLogEnabled(true);
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
            RongIM.init(this);
            //注册自定义消息
            RongIM.registerMessageType(CustomizeMessage.class);
            //注册消息模板
            mCustomizeMessageItemProvider = new CustomizeMessageItemProvider();
            RongIM.registerMessageTemplate(mCustomizeMessageItemProvider);
        }
    }

    private void initUM(){
        UMConfigure.init(this,ServerConstant.UM_APP_KEY
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
    }
}
