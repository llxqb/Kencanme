package com.shushan.kencanme.app.di.modules;

import android.app.Activity;

import com.shushan.kencanme.app.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * 提供baseactivity的module
 * Created by niuxiaowei on 16/3/20.
 * Provides最终解决第三方类库依赖注入问题
 */
@Module
public class ActivityModule {

    private final Activity activity;
    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return activity;
    }



}
