package com.shushan.kencanme.app.mvp.ui.activity.pay;


import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RechargeControl {
    public interface RechargeView extends LoadDataView {
        void RechargeBeansInfoSuccess(ReChargeBeansInfoResponse reChargeBeansInfoResponse);

        void createOrderSuccess(CreateOrderResponse createOrderResponse);
    }

    public interface PresenterRecharge extends Presenter<RechargeView> {
        void onRequestBeansInfo(ReChargeBeansInfoRequest reChargeBeansInfoRequest);

        /**
         * 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);
    }

}
