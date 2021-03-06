package com.shushan.kencanme.app.di.modules;

import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.ui.fragment.mine.MineFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class MineFragmentModule {

    private MineFragmentControl.MineView mMineFragmentView;

    public MineFragmentModule(LoadDataView view) {
        if (view instanceof MineFragmentControl.MineView) {
            mMineFragmentView = (MineFragmentControl.MineView) view;
        }
    }

    @Provides
    @PerActivity
    MineFragmentControl.MineView mineFragmentView() {
        return this.mMineFragmentView;
    }
}
