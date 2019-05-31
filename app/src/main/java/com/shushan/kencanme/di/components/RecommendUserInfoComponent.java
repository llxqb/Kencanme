package com.shushan.kencanme.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.RecommendUserInfoModule;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.mvp.ui.activity.RecommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.RecommendUserInfo.RecommendUserInfoControl;

import dagger.Component;

/**
 * LoginComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 * Created by li.liu on 18/1/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {RecommendUserInfoModule.class, ActivityModule.class})
public interface RecommendUserInfoComponent extends ActivityComponent {
    //对LoginActivity进行依赖注入
    void inject(RecommendUserInfoActivity recommendUserInfoActivity);

    AppCompatActivity activity();

    RecommendUserInfoControl.RecommendUserInfoView view();

}
