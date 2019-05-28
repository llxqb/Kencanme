package com.shushan.kencanme.di;


import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.base.BaseFragment;

/**
 * Created by wxl on 16/3/30.
 *
 */
public interface ComponetGraph {

    void inject(KencanmeApp application);

    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

//    void inject(CustomerService service);

}
