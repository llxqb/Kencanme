package com.shushan.kencanme.app.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.mvp.model.ModelTransform;
import com.shushan.kencanme.app.mvp.model.MyAlbumModel;
import com.shushan.kencanme.app.mvp.ui.activity.photo.MyAlbumControl;
import com.shushan.kencanme.app.mvp.ui.activity.photo.MyAlbumPresenterImpl;
import com.shushan.kencanme.app.network.RetrofitUtil;
import com.shushan.kencanme.app.network.networkapi.PersonalInfoApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class MyAlbumModule {
    private final AppCompatActivity activity;
    private final MyAlbumControl.MyAlbumView view;

    public MyAlbumModule(AppCompatActivity activity, MyAlbumControl.MyAlbumView view) {
        this.activity = activity;
        this.view = view;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MyAlbumControl.MyAlbumView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MyAlbumModel provideMyAlbumModel(Gson gson, ModelTransform modelTransform) {
        return new MyAlbumModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(BuildConfig.KENCANME_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(PersonalInfoApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    MyAlbumControl.PresenterMyAlbum providePresenterMyAlbum(MyAlbumPresenterImpl PresenterMyAlbum) {
        return PresenterMyAlbum;
    }
}
