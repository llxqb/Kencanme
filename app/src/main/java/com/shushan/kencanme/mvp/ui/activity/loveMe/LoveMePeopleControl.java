package com.shushan.kencanme.mvp.ui.activity.loveMe;


import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class LoveMePeopleControl {
    public interface LoveMePeopleView extends LoadDataView {
        void  getLoveMePeopleInfoSuccess(MyFriendsResponse myFriendsResponse);
    }

    public interface PresenterLoveMePeople extends Presenter<LoveMePeopleView> {
        /**
         * 好友/喜欢的人列表
         */
        void onRequestMyFriendList(MyFriendsRequest myFriendsRequest);
    }


}
