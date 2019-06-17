package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;


import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RecommendUserInfoControl {
    public interface RecommendUserInfoView extends LoadDataView {
        void getRecommendUserInfoSuccess(RecommendUserInfoResponse response);

    }

    public interface PresenterRecommendUserInfo extends Presenter<RecommendUserInfoView> {
        void onRequestRecommendUserInfo(RecommendUserInfoRequest recommendUserInfoRequest);
    }


}
