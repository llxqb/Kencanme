package com.shushan.kencanme.mvp.ui.activity.main;


import com.shushan.kencanme.entity.DialogBuyBean;
import com.shushan.kencanme.entity.request.BuyExposureTimeRequest;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HomeFragmentControl {
    public interface HomeView extends LoadDataView {
        void getInfoSuccess(HomeFragmentResponse response);

        void getInfoFail(String errMsg);

        void getLikeSuccess(String msg);

        void getBuyExposureTimeList(DialogBuyBean dialogBuyBean);

        void getBuyExposureTime(String msg);

        void personalInfoSuccess(PersonalInfoResponse response);

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

        void exposureSuccess(String msg);
    }

    public interface homeFragmentPresenter extends Presenter<HomeView> {
        /**
         * 请求homeFragment list 数据
         */
        void onRequestInfo(HomeFragmentRequest homeFragmentRequest);

        /**
         * 喜欢
         */
        void onRequestLike(LikeRequest likeRequest);

        /**
         * 曝光次数嗨豆购买规则 (列表: 100嗨豆多少次曝光..)
         */
        void onRequestBuyExposureTimeList(TokenRequest tokenRequest);

        /**
         * 嗨豆购买曝光次数
         */
        void onRequestBuyExposureTime(BuyExposureTimeRequest buyExposureTimeRequest);
        /**
         * 请求查询用户信息
         */
        void onRequestPersonalInfo(PersonalInfoRequest request);
        /**
         * 查询用户信息（首页）
         */
        void onRequestHomeUserInfo(TokenRequest tokenRequest);
        /**
         * 进行超级曝光
         */
        void onRequestExposure(TokenRequest tokenRequest);
    }


}
