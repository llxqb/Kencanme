package com.shushan.kencanme.app.mvp.ui.activity.pay;


import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
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

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

        void getPayFinishUploadSuccess();
    }

    public interface PresenterRecharge extends Presenter<RechargeView> {
        /**
         * 查询列表
         */
        void onRequestBeansInfo(ReChargeBeansInfoRequest reChargeBeansInfoRequest);

        /**
         * 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);

        /**
         * 查询用户信息（首页）
         */
        void onRequestHomeUserInfo(TokenRequest tokenRequest);
        /**
         * APP支付成功上报
         */
        void onPayFinishUpload(PayFinishUploadRequest payFinishUpload);
    }

}
