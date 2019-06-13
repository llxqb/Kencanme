package com.shushan.kencanme.mvp.ui.activity.main;


import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HomeFragmentControl {
    public interface HomeView extends LoadDataView {
        void getInfoSuccess(HomeFragmentResponse response);
        void getInfoFail(String errMsg);

    }

    public interface homeFragmentPresenter extends Presenter<HomeView> {
        //这个名字得改  home可能不止一个接口
        void onRequestInfo(HomeFragmentRequest homeFragmentRequest);
    }


}
