package com.shushan.kencanme.app.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.mvp.model.ModelTransform;
import com.shushan.kencanme.app.mvp.model.PersonalInfoModel;
import com.shushan.kencanme.app.mvp.ui.activity.reportUser.ReportUserControl;
import com.shushan.kencanme.app.mvp.ui.activity.reportUser.ReportUserPresenterImpl;
import com.shushan.kencanme.app.network.RetrofitUtil;
import com.shushan.kencanme.app.network.networkapi.PersonalInfoApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class ReportUserModule {
    private final AppCompatActivity activity;
    private final ReportUserControl.ReportUserView view;

    public ReportUserModule(AppCompatActivity activity, ReportUserControl.ReportUserView view) {
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
    ReportUserControl.ReportUserView view() {
        return this.view;
    }

    @Provides
    @PerActivity
    PersonalInfoModel provideReportUserModel(Gson gson, ModelTransform modelTransform) {
        return new PersonalInfoModel(new RetrofitUtil.Builder()
                .context(activity)
                .baseUrl(ServerConstant.DISPATCH_SERVICE)
                .isHttps(!BuildConfig.DEBUG)
//                .key(BuildConfig.STORE_NAME,BuildConfig.STORE_PASSWORD)
                .isToJson(false)
                .builder()
                .create(PersonalInfoApi.class), gson, modelTransform);
    }

    @Provides
    @PerActivity
    ReportUserControl.PresenterReportUser providePresenterReportUser(ReportUserPresenterImpl reportUserPresenter) {
        return reportUserPresenter;
    }
}
