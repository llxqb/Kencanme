package com.shushan.kencanme.app.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.kencanme.app.di.modules.MainModule;
import com.shushan.kencanme.app.di.modules.MineFragmentModule;
import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.mvp.ui.fragment.MineFragment;

import dagger.Component;

/**
 * LoginComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 * Created by li.liu on 18/1/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainModule.class, MineFragmentModule.class})
public interface MineFragmentComponent {
    AppCompatActivity activity();

    void inject(MineFragment fragment);
}