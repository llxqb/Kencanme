package com.shushan.kencanme.mvp.ui.activity.personInfo;


import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class PersonalInfoControl {
    public interface PersonalInfoView extends LoadDataView {
        void updateSuccess(PersonalInfoResponse response);

        void updateFail(String errorMsg);
    }

    public interface PresenterPersonalInfo extends Presenter<PersonalInfoView> {
        void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest);
    }


}
