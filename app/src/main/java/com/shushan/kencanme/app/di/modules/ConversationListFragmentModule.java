package com.shushan.kencanme.app.di.modules;

import com.shushan.kencanme.app.di.scopes.PerActivity;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.ConversationFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 19/05/28.
 */
@Module
public class ConversationListFragmentModule {

    private ConversationFragmentControl.ConversationView mConversationFragmentView;

    public ConversationListFragmentModule(LoadDataView view) {
        if (view instanceof ConversationFragmentControl.ConversationView) {
            mConversationFragmentView = (ConversationFragmentControl.ConversationView) view;
        }
    }

    @Provides
    @PerActivity
    ConversationFragmentControl.ConversationView conversationFragmentView() {
        return this.mConversationFragmentView;
    }
}
