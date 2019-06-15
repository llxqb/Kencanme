package com.shushan.kencanme.mvp.ui.activity.pay;


import com.shushan.kencanme.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RechargeControl {
    public interface RechargeView extends LoadDataView {
        void RechargeBeansInfoSuccess(ReChargeBeansInfoResponse reChargeBeansInfoResponse);
    }

    public interface PresenterRecharge extends Presenter<RechargeView> {
        void onRequestBeansInfo(ReChargeBeansInfoRequest reChargeBeansInfoRequest);
    }


}
