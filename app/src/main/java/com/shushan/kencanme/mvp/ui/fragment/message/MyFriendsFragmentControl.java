package com.shushan.kencanme.mvp.ui.fragment.message;


import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MyFriendsFragmentControl {

    public interface MyFriendsView extends LoadDataView {
       void  getMyFriendsListInfoSuccess(MyFriendsResponse myFriendsResponse);
    }

    public interface MyFriendsFragmentPresenter extends Presenter<MyFriendsView> {
        /**
         * 好友/喜欢的人列表
         */
        void onRequestMyFriendList(MyFriendsRequest myFriendsRequest);

    }


}
