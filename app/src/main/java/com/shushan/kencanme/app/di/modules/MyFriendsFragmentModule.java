package com.shushan.kencanme.app.di.modules;

import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.ui.fragment.message.MyFriendsFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class MyFriendsFragmentModule {

    private MyFriendsFragmentControl.MyFriendsView mFriendsFragmentView;

    public MyFriendsFragmentModule(LoadDataView view) {
        if (view instanceof MyFriendsFragmentControl.MyFriendsView) {
            mFriendsFragmentView = (MyFriendsFragmentControl.MyFriendsView) view;
        }
    }

    @Provides
    @PerActivity
    MyFriendsFragmentControl.MyFriendsView myFriendsFragmentView() {
        return this.mFriendsFragmentView;
    }
}
