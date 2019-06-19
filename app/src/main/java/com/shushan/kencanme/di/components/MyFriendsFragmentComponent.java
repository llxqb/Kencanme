package com.shushan.kencanme.di.components;


import android.support.v7.app.AppCompatActivity;

import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.di.modules.MyFriendsFragmentModule;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.mvp.ui.fragment.message.MyFriendsFragment;

import dagger.Component;

/**
 * LoginComponent继承了ActivityComponent，假如ActivityComponent中定义了创建类实例方法，则MainComponent中必须提供@Inject或@Provides对应的
 * 初始化类实例的方法
 * Created by li.liu on 18/1/19.
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainModule.class, MyFriendsFragmentModule.class})
public interface MyFriendsFragmentComponent {
    AppCompatActivity activity();

    void inject(MyFriendsFragment fragment);
}