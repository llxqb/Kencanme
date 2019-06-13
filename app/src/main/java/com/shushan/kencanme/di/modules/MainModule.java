package com.shushan.kencanme.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.BuildConfig;
import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.entity.ServerConstant;
import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ModelTransform;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentPresenterImpl;
import com.shushan.kencanme.mvp.ui.activity.main.MainControl;
import com.shushan.kencanme.mvp.ui.activity.main.MainPresenterImpl;
import com.shushan.kencanme.mvp.ui.fragment.mine.MineFragmentControl;
import com.shushan.kencanme.mvp.ui.fragment.mine.MineFragmentPresenterImpl;
import com.shushan.kencanme.network.RetrofitUtil;
import com.shushan.kencanme.network.networkapi.MainApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class MainModule {
    private final AppCompatActivity activity;
    private MainControl.MainView view;

    public MainModule(AppCompatActivity activity, MainControl.MainView view) {
        this.activity = activity;
        this.view = view;
    }

    public MainModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    AppCompatActivity activity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    MainControl.MainView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    MainControl.PresenterMain providePresenterMain(MainPresenterImpl presenterMain) {
        return presenterMain;
    }

    @Provides
    @PerActivity
    MainModel provideMainModel(Gson gson, ModelTransform modelTransform) {
        return new MainModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(ServerConstant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(MainApi.class), gson, modelTransform);
    }


    //add
    @Provides
    @PerActivity
    HomeFragmentControl.homeFragmentPresenter providePresenterHomeFragment(HomeFragmentPresenterImpl homeFragmentPresenter) {
        return homeFragmentPresenter;
    }

    @Provides
    @PerActivity
    MineFragmentControl.mineFragmentPresenter providePresenterMineFragment(MineFragmentPresenterImpl mineFragmentPresenter) {
        return mineFragmentPresenter;
    }

}
