package com.shushan.kencanme.mvp.ui.activity.RecommendUserInfo;


import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

import java.util.List;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RecommendUserInfoControl {
    public interface RecommendUserInfoView extends LoadDataView {
        void getRecommendUserInfoSuccess(List<RecommendUserInfoResponse.DataBean> response);

    }

    public interface PresenterRecommendUserInfo extends Presenter<RecommendUserInfoView> {
        void onRequestRecommendUserInfo(RecommendUserInfoRequest recommendUserInfoRequest);
    }


}
