package com.shushan.kencanme.app.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.mvp.model.ModelTransform;
import com.shushan.kencanme.app.mvp.model.RecommendUserInfoModel;
import com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo.RecommendUserInfoControl;
import com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo.RecommendUserInfoPresenterImpl;
import com.shushan.kencanme.app.network.RetrofitUtil;
import com.shushan.kencanme.app.network.networkapi.RecommendUserInfoApi;

import dagger.Module;
import dagger.Provides;

import static com.shushan.kencanme.app.BuildConfig.KENCANME_BASE_URL;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class RecommendUserInfoModule {
    private final AppCompatActivity activity;
    private final RecommendUserInfoControl.RecommendUserInfoView view;

    public RecommendUserInfoModule(AppCompatActivity activity, RecommendUserInfoControl.RecommendUserInfoView view) {
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
    RecommendUserInfoControl.RecommendUserInfoView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    RecommendUserInfoModel provideRecommendUserInfoModel(Gson gson, ModelTransform modelTransform) {
        return new RecommendUserInfoModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(KENCANME_BASE_URL)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(RecommendUserInfoApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    RecommendUserInfoControl.PresenterRecommendUserInfo providePresenterRecommendUserInfo(RecommendUserInfoPresenterImpl recommendUserInfoPresenter) {
        return recommendUserInfoPresenter;
    }
}
