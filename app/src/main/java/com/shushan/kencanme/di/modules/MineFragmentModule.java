package com.shushan.kencanme.di.modules;

import com.shushan.kencanme.di.scopes.PerActivity;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.mvp.ui.fragment.mine.MineFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class MineFragmentModule {

    private MineFragmentControl.MineView mMineFragmentView;

    public MineFragmentModule(LoadDataView view) {
        if (view instanceof HomeFragmentControl.HomeView) {
            mMineFragmentView = (MineFragmentControl.MineView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     对应起来
     */
    @Provides
    @PerActivity
    MineFragmentControl.MineView mineFragmentView() {
        return this.mMineFragmentView;
    }
}
