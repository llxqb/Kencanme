package com.shushan.kencanme.mvp.ui.activity.main;


import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MainControl {
    public interface MainView extends LoadDataView {
        void personalInfoSuccess(PersonalInfoResponse response);

    }

    public interface PresenterMain extends Presenter<MainView> {
        void onRequestPersonalInfo(PersonalInfoRequest request);
    }

}
