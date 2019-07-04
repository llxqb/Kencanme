package com.shushan.kencanme.app.di.components;

import android.app.Activity;

import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.scopes.PerActivity;

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
