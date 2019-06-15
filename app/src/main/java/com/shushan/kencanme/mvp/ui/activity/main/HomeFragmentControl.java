package com.shushan.kencanme.mvp.ui.activity.main;


import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
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
        void getLikeSuccess(String msg);
    }

    public interface homeFragmentPresenter extends Presenter<HomeView> {
        void onRequestInfo(HomeFragmentRequest homeFragmentRequest);

        //喜欢
        void onRequestLike(LikeRequest likeRequest);
    }


}
