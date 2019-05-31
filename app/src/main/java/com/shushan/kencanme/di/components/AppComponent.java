package com.shushan.kencanme.di.components;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.di.ComponetGraph;
import com.shushan.kencanme.di.modules.AppModule;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.model.ModelTransform;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 全局类实例都统一交给ApplicationComponent管理
 * Created by li.liuon 18/1/11.
 * Singleton  表示单例
 * dependencies   Component中的依赖
 * SubComponent   Component中的包含
 * Scope      在PerActivity中    都有必要用自定义的Scope注解标注这些Component
 * Provides最终解决第三方类库依赖注入问题
 * 第三方类到这里注册
 * AppComponent中方法必须到AppModule中实现
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent extends ComponetGraph {
    Context getContext();

    Gson gson();

    ModelTransform modeTransform();

    ImageLoaderHelper imageLoaderHelper();

//    DaoSession daoSession();
//
//    BuProcessor buProcessor();
//
//    AMapLocationClient aMapLocationClient();//MainActivity  有用到  @Inject AMapLocationClient mAMapLocationClient;
//
//    AMapLocationClientOption aMapLocationClientOption();
}
