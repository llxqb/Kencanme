package com.shushan.kencanme.mvp.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerMyAlbumComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.MyAlbumModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.MyAlbumAdapter;
import com.shushan.kencanme.mvp.views.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAlbumActivity extends BaseActivity implements CommonDialog.CommonDialogListener, MyAlbumControl.MyAlbumView {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    private MyAlbumAdapter myAlbumAdapter;
    private int deletePos;
    private ArrayList<MyAlbumResponse.DataBean> mPhotoBeanList = new ArrayList<>();


    public static void start(Context context, List<MyAlbumResponse.DataBean> photoBeanList) {
        Intent intent = new Intent(context, MyAlbumActivity.class);
        intent.putParcelableArrayListExtra("photoBeanList", (ArrayList<? extends Parcelable>) photoBeanList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_MY_ALBUM)) {
            //更新我的相册
            UpdateAlbumRequest updateAlbumRequest = intent.getParcelableExtra("updateAlbumRequest");
            MyAlbumResponse.DataBean dataBean = new MyAlbumResponse.DataBean();
            dataBean.setAlbum_type(updateAlbumRequest.album_type);
            dataBean.setAlbum_url(updateAlbumRequest.album_url);
            dataBean.setCost(updateAlbumRequest.cost);
            myAlbumAdapter.addData(dataBean);
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_MY_ALBUM);
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            mPhotoBeanList = getIntent().getParcelableArrayListExtra("photoBeanList");
            //设置RecyclerView第一张图片为默认图片
            mPhotoBeanList.add(0, null);
        }
        mAlbumRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        myAlbumAdapter = new MyAlbumAdapter(this, mPhotoBeanList, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(myAlbumAdapter);

        mAlbumRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyAlbumResponse.DataBean dataBean = (MyAlbumResponse.DataBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.photo_delete:
                        deletePos = position;
                        DialogFactory.showCommonDialog(MyAlbumActivity.this, "Are you sure to delete the photo/video?", Constant.DIALOG_ONE);
                        break;
                    case R.id.photo_item_rl:
                        if (position == 0) {
                            if (myAlbumAdapter.getItemCount() >= 13) {
                                showToast("最多传12张");
                            } else {
                                startActivitys(UploadPhotoActivity.class);//上传图片  这里都是新增图片
                            }
                        } else {
                            assert dataBean != null;
                            LookPhotoActivity.start(MyAlbumActivity.this, dataBean.getAlbum_url());//查看大图
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
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

    private void initializeInjector() {
        DaggerMyAlbumComponent.builder().appComponent(getAppComponent())
                .myAlbumModule(new MyAlbumModule(MyAlbumActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
