package com.shushan.kencanme.mvp.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
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
import com.shushan.kencanme.entity.request.DeleteMyAlbumRequest;
import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.MyAlbumAdapter;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
    private int deletePhotoId;
    private ArrayList<MyAlbumResponse.DataBean> mPhotoBeanList = new ArrayList<>();
    @Inject
    MyAlbumControl.PresenterMyAlbum mPresenter;

    public static void start(Context context, List<MyAlbumResponse.DataBean> photoBeanList) {
        Intent intent = new Intent(context, MyAlbumActivity.class);
        intent.putParcelableArrayListExtra("photoBeanList", (ArrayList<? extends Parcelable>) photoBeanList);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_album);
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(this, null);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_MY_ALBUM)) {
            //更新我的相册
            MyAlbumRequest myAlbumRequest = new MyAlbumRequest();
            myAlbumRequest.token = mBuProcessor.getToken();
            mPresenter.onRequestMyAlbum(myAlbumRequest);
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
                        assert dataBean != null;
                        deletePhotoId = dataBean.getId();
                        DialogFactory.showCommonDialog(MyAlbumActivity.this, "Are you sure to delete the photo/video?", Constant.DIALOG_ONE);
                        break;
                    case R.id.photo_item_rl:
                        if (position == 0) {
                            if (myAlbumAdapter.getItemCount() >= 13) {
                                showToast(getResources().getString(R.string.album_max_num));
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
        DeleteMyAlbumRequest deleteMyAlbumRequest = new DeleteMyAlbumRequest();
        deleteMyAlbumRequest.token = mBuProcessor.getToken();
        deleteMyAlbumRequest.id = String.valueOf(deletePhotoId);
        mPresenter.deleteMyAlbum(deleteMyAlbumRequest);
    }

    @Override
    public void deleteSuccess(String msg) {
        myAlbumAdapter.remove(deletePos);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_MY_ALBUM_FROM_MYALBUM));
    }

    @Override
    public void getMyAlbumSuccess(MyAlbumResponse myAlbumResponse) {
        mPhotoBeanList = (ArrayList<MyAlbumResponse.DataBean>) myAlbumResponse.getData();
        MyAlbumResponse.DataBean dataBean = new MyAlbumResponse.DataBean();
        dataBean.setAlbum_url("");
        mPhotoBeanList.add(0, dataBean);
        myAlbumAdapter.setNewData(mPhotoBeanList);
    }

    private void initializeInjector() {
        DaggerMyAlbumComponent.builder().appComponent(getAppComponent())
                .myAlbumModule(new MyAlbumModule(MyAlbumActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
