package com.shushan.kencanme.mvp.ui.activity.photo;

import android.content.Context;

import com.shushan.kencanme.entity.request.DeleteMyAlbumRequest;
import com.shushan.kencanme.mvp.model.MyAlbumModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * LoginPresenterImpl
 */

public class MyAlbumPresenterImpl implements MyAlbumControl.PresenterMyAlbum {

    private MyAlbumControl.MyAlbumView mMyAlbumView;
    private final MyAlbumModel mMyAlbumModel;
    private final Context mContext;

    @Inject
    public MyAlbumPresenterImpl(Context context, MyAlbumModel model, MyAlbumControl.MyAlbumView MyAlbumView) {
        mContext = context;
        mMyAlbumModel = model;
        mMyAlbumView = MyAlbumView;
    }

    @Override
    public void deleteMyAlbum(DeleteMyAlbumRequest deleteMyAlbumRequest) {

    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mMyAlbumView = null;
    }



}
