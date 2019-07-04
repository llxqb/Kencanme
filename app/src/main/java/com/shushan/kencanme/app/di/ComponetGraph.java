package com.shushan.kencanme.app.di;


import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.base.BaseFragment;
import com.shushan.kencanme.app.mvp.ui.activity.register.EarnBeansActivity;
import com.shushan.kencanme.app.mvp.ui.activity.setting.MessageReminderActivity;
import com.shushan.kencanme.app.mvp.ui.activity.splash.SplashActivity;
import com.shushan.kencanme.app.mvp.views.dialog.MatchSuccessDialog;

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
    void inject(EarnBeansActivity earnBeansActivity);
    void inject(MatchSuccessDialog matchSuccessDialog);

//    void inject(CustomerService service);

}
