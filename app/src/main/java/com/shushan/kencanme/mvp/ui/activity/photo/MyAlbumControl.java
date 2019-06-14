package com.shushan.kencanme.mvp.ui.activity.photo;


import com.shushan.kencanme.entity.request.DeleteMyAlbumRequest;
import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MyAlbumControl {
    public interface MyAlbumView extends LoadDataView {
        void deleteSuccess(String msg);

        void getMyAlbumSuccess(MyAlbumResponse myAlbumResponse);
    }

    public interface PresenterMyAlbum extends Presenter<MyAlbumView> {
        /**
         * 删除相片
         */
        void deleteMyAlbum(DeleteMyAlbumRequest deleteMyAlbumRequest);

        //查看我的相册
        void onRequestMyAlbum(MyAlbumRequest myAlbumRequest);

    }

}
