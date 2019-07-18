package com.shushan.kencanme.app.mvp.ui.fragment.mine;


import com.shushan.kencanme.app.entity.request.MyAlbumRequest;
import com.shushan.kencanme.app.entity.response.MyAlbumResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MineFragmentControl {
    public interface MineView extends LoadDataView {
        void getMyAlbumSuccess(MyAlbumResponse response);
    }

    public interface mineFragmentPresenter extends Presenter<MineView> {
        //查看我的相册
        void onRequestMyAlbum(MyAlbumRequest myAlbumRequest);
    }

}