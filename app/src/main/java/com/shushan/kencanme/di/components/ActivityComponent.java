package com.shushan.kencanme.di.components;

import android.app.Activity;


import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.scopes.PerActivity;

import dagger.Component;

/**
 *
 * Created by niuxiaowei on 16/3/20.
 */
@PerActivity
@Component(modules = {ActivityModule.class})
public interface ActivityComponent {

    Activity getActivity();
}
