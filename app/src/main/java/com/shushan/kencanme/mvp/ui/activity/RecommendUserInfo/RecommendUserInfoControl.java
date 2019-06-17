package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;


import com.shushan.kencanme.entity.request.BlackUserRequest;
import com.shushan.kencanme.entity.request.DeleteUserRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RecommendUserInfoControl {
    public interface RecommendUserInfoView extends LoadDataView {
        void getRecommendUserInfoSuccess(RecommendUserInfoResponse response);

        void getBlackUserSuccess(String msg);

        void getDeleteUserSuccess(String msg);

        void getLikeSuccess(String msg);
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
    }


}
