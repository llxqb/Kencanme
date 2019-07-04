package com.shushan.kencanme.app.mvp.ui.activity.loveMe;


import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.MyFriendsRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.response.MyFriendsResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class LoveMePeopleControl {
    public interface LoveMePeopleView extends LoadDataView {
        void getLoveMePeopleInfoSuccess(MyFriendsResponse myFriendsResponse);

        void getLikeSuccess(String msg);

        void chatNumSuccess();
    }

    public interface PresenterLoveMePeople extends Presenter<LoveMePeopleView> {
        /**
         * 好友/喜欢的人列表
         */
        void onRequestMyFriendList(MyFriendsRequest myFriendsRequest);

        /**
         * 喜欢
         */
        void onRequestLike(LikeRequest likeRequest);
        /**
         * 密聊接口
         * 统计今日密聊次数
         */
        void onRequestChatNum(RequestFreeChat requestFreeChat);

    }


}
