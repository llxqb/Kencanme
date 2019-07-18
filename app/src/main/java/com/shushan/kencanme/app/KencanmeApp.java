package com.shushan.kencanme.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.app.di.components.AppComponent;
import com.shushan.kencanme.app.di.components.DaggerAppComponent;
import com.shushan.kencanme.app.di.modules.AppModule;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.CustomizeMessage;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.CustomizeMessageItemProvider;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化热修复
        initHotfix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        mAppComponent.inject(this);//必须有
        //初始化融云
        initRongYun();
        //友盟init
        initUM();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private void initHotfix() {
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.0.0";
        }
        // initialize必须放在attachBaseContext最前面，初始化代码直接写在Application类里面，切勿封装到其他类。
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
    }



    public void initRongYun() {
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

    private void initUM() {
        UMConfigure.init(this, ServerConstant.UM_APP_KEY
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }
}
