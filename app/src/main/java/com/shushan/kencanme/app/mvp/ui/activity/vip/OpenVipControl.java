package com.shushan.kencanme.app.mvp.ui.activity.vip;


import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.OpenVipRequest;
import com.shushan.kencanme.app.entity.request.PayFinishAHDIRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderAHDIRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderAHDIResponse;
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

        void getPayFinishUploadSuccess();

        void createOrderAHDISuccess(CreateOrderAHDIResponse createOrderAHDIResponse);

        void getPayFinishAHDIUploadSuccess();
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
        void onPayFinishUpload(PayFinishUploadRequest payFinishUpload);

        /**
         * AHDI支付创建订单
         */
        void onRequestCreateOrderAHDI(RequestOrderAHDIRequest requestOrderAHDIRequest);

        /**
         * AHDI支付上报（查询是否已经支付完成）
         */
        void onPayFinishAHDIUpload(PayFinishAHDIRequest payFinishAHDIRequest);
    }


}
