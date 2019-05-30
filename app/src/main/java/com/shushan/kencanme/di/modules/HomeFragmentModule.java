package com.shushan.kencanme.di.modules;

import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class HomeFragmentModule {

    private HomeFragmentControl.HomeView mHomeFragmentView;

    public HomeFragmentModule(LoadDataView view) {
        if (view instanceof HomeFragmentControl.HomeView) {
            mHomeFragmentView = (HomeFragmentControl.HomeView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     对应起来
     */
    @Provides
    @PerActivity
    HomeFragmentControl.HomeView homeFragmentView() {
        return this.mHomeFragmentView;
    }


}
