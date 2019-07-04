package com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo;


import com.shushan.kencanme.app.entity.request.BlackUserRequest;
import com.shushan.kencanme.app.entity.request.DeleteUserRequest;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.LookAlbumByBeansRequest;
import com.shushan.kencanme.app.entity.request.LookContactTypeRequest;
import com.shushan.kencanme.app.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.LikeResponse;
import com.shushan.kencanme.app.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RecommendUserInfoControl {
    public interface RecommendUserInfoView extends LoadDataView {
        void getRecommendUserInfoSuccess(RecommendUserInfoResponse response);

        void getBlackUserSuccess(String msg);

        void getDeleteUserSuccess(String msg);

        void getLikeSuccess(LikeResponse likeResponse);

        void getContactSuccess(String msg);

        void getAlbumByBeansSuccess(String msg);

        void getHomeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

        void chatNumSuccess();
    }

    public interface PresenterRecommendUserInfo extends Presenter<RecommendUserInfoView> {
        /**
         * 请求推荐的用户信息
         */
        void onRequestRecommendUserInfo(RecommendUserInfoRequest recommendUserInfoRequest);

        /**
         * 加入黑名单
         */
        void onRequestBlackUser(BlackUserRequest blackUserRequest);

        /**
         * 删除好友
         */
        void onRequestDeleteUser(DeleteUserRequest deleteUserRequest);

        /**
         * 喜欢
         */
        void onRequestLike(LikeRequest likeRequest);
        /**
         * 查看联系方式
         */
        void onRequestContact(LookContactTypeRequest lookContactTypeRequest);

        /**
         * 嗨豆查看相册
         */
        void onRequestAlbumByBeans(LookAlbumByBeansRequest lookAlbumByBeansRequest);

        /**
         * 查询用户信息（首页）
         */
        void onRequestHomeUserInfo(TokenRequest tokenRequest);
        /**
         * 密聊接口
         * 统计今日密聊次数
         */
        void onRequestChatNum(RequestFreeChat requestFreeChat);
    }


}
