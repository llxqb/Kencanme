package com.shushan.kencanme.app.mvp.ui.activity.main;


import com.shushan.kencanme.app.entity.DialogBuyBean;
import com.shushan.kencanme.app.entity.request.BuyExposureTimeRequest;
import com.shushan.kencanme.app.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.LikeResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HomeFragmentControl {
    public interface HomeView extends LoadDataView {
        void getInfoSuccess(HomeFragmentResponse response);

        void getLikeSuccess(LikeResponse likeResponse);

        void getBuyExposureTimeList(DialogBuyBean dialogBuyBean);

        void getBuyExposureTime(String msg);

//        void personalInfoSuccess(PersonalInfoResponse response);

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

        void exposureSuccess(String msg);

        void chatNumSuccess();
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
         * 曝光次数嗨豆购买规则 (列表: 100嗨豆多少次曝光 200.嗨豆多少次曝光....)
         */
        void onRequestBuyExposureTimeList(TokenRequest tokenRequest);

        /**
         * 嗨豆购买曝光次数
         */
        void onRequestBuyExposureTime(BuyExposureTimeRequest buyExposureTimeRequest);
        /**
         * 查询用户信息（首页）
         */
        void onRequestHomeUserInfo(TokenRequest tokenRequest);
        /**
         * 进行超级曝光
         */
        void onRequestExposure(TokenRequest tokenRequest);

        /**
         * 密聊接口
         * 统计今日密聊次数
         */
        void onRequestChatNum(RequestFreeChat requestFreeChat);
    }


}
