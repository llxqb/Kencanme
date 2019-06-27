package com.shushan.kencanme.di;


import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.mvp.ui.activity.setting.MessageReminderActivity;
import com.shushan.kencanme.mvp.ui.activity.splash.SplashActivity;

/**
 * Created by wxl on 16/3/30.
 *
 */
public interface ComponetGraph {

    void inject(KencanmeApp application);

    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    void inject(MessageReminderActivity messageReminderActivity);
    void inject(SplashActivity splashActivity);

//    void inject(CustomerService service);

}
