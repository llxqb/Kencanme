package com.shushan.kencanme.mvp.ui.activity.vip;


import com.shushan.kencanme.entity.request.CreateOrderRequest;
import com.shushan.kencanme.entity.request.OpenVipRequest;
import com.shushan.kencanme.entity.request.PaySuccessRequest;
import com.shushan.kencanme.entity.response.CreateOrderResponse;
import com.shushan.kencanme.entity.response.OpenVipResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class OpenVipControl {
    public interface OpenVipView extends LoadDataView {
        void OpenVipListSuccess(OpenVipResponse openVipResponse);

        void createOrderSuccess(CreateOrderResponse createOrderResponse);
    }

    public interface PresenterOpenVip extends Presenter<OpenVipView> {
        void openVipListRequest(OpenVipRequest openVipRequest);
        /**
         * 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);
        /**
         * APPLE支付成功上报
         */
        void onRequestPaySuccess(PaySuccessRequest paySuccessRequest);
    }


}
