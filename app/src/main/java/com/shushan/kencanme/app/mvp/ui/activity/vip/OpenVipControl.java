package com.shushan.kencanme.app.mvp.ui.activity.vip;


import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.OpenVipRequest;
import com.shushan.kencanme.app.entity.request.PaySuccessRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.OpenVipResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class OpenVipControl {
    public interface OpenVipView extends LoadDataView {
        void OpenVipListSuccess(OpenVipResponse openVipResponse);

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

        void createOrderSuccess(CreateOrderResponse createOrderResponse);
    }

    public interface PresenterOpenVip extends Presenter<OpenVipView> {
        void openVipListRequest(OpenVipRequest openVipRequest);
        /**
         * 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);

        /**
         * 查询用户信息（首页）
         */
        void onRequestHomeUserInfo(TokenRequest tokenRequest);

        /**
         * APPLE支付成功上报
         */
        void onRequestPaySuccess(PaySuccessRequest paySuccessRequest);
    }


}
