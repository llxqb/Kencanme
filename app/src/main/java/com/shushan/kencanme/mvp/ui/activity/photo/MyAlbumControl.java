package com.shushan.kencanme.mvp.ui.activity.photo;


import com.shushan.kencanme.entity.request.DeleteMyAlbumRequest;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MyAlbumControl {
    public interface MyAlbumView extends LoadDataView {
//        void loginSuccess(LoginResponse response);
//
//        void personalInfoSuccess(PersonalInfoResponse personalInfoResponse);
    }

    public interface PresenterMyAlbum extends Presenter<MyAlbumView> {
        void deleteMyAlbum(DeleteMyAlbumRequest deleteMyAlbumRequest);

    }


}
