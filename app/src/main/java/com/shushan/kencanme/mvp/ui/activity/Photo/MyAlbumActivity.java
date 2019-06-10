package com.shushan.kencanme.mvp.ui.activity.photo;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constant;
import com.shushan.kencanme.entity.PhotoBean;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.MyAlbumAdapter;
import com.shushan.kencanme.mvp.views.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAlbumActivity extends BaseActivity implements CommonDialog.CommonDialogListener {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    List<PhotoBean> photoBeanList;
    MyAlbumAdapter myAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    int deletePos;

    @Override
    public void initView() {
        photoBeanList = new ArrayList<>();
        //设置RecyclerView第一张图片为默认图片
        photoBeanList.add(null);
        mAlbumRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myAlbumAdapter = new MyAlbumAdapter(this, photoBeanList, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(myAlbumAdapter);

        mAlbumRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.photo_delete:
                        deletePos = position;
//                        myAlbumAdapter.remove(position);
//                        myAlbumAdapter.notifyDataSetChanged();
                        DialogFactory.showCommonDialog(MyAlbumActivity.this, "Are you sure to delete the photo/video?", Constant.DIALOG_ONE);
                        break;
                    case R.id.photo_item_rl:
                        if (position == 0) {
                            startActivitys(UploadPhotoActivity.class);
                        } else {
                            showToast(position + "");
                        }
                        break;

                }
            }
        });
    }

    @Override
    public void initData() {
        for (int i = 0; i < 7; i++) {
            if (i % 3 == 0) {
                PhotoBean photoBean = new PhotoBean();
                photoBean.isPic = true;
                photoBean.picType = 0;
                photoBean.picPath = "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png";
                photoBeanList.add(photoBean);
            } else if (i % 3 == 1) {
                PhotoBean photoBean = new PhotoBean();
                photoBean.isPic = false;
                photoBean.picType = 1;
                photoBean.picPath = "http://tb-video.bdstatic.com/tieba-smallvideo-transcode/2148489_1c9d8082c70caa732fc0648a21be383c_1.mp4";
                photoBeanList.add(photoBean);
            } else {
                PhotoBean photoBean = new PhotoBean();
                photoBean.isPic = true;
                photoBean.picType = 2;
                photoBean.picPath = "http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png";
                photoBeanList.add(photoBean);
            }
        }
//        myAlbumAdapter.addData(photoBeanList);

    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void commonDialogBtnOkListener() {
        myAlbumAdapter.remove(deletePos);
        myAlbumAdapter.notifyDataSetChanged();
    }
}
