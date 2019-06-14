package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.shushan.kencanme.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class LookPhotoModule {
    private final AppCompatActivity activity;

    public LookPhotoModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }



}
