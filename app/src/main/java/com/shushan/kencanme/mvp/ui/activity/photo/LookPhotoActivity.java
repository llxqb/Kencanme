package com.shushan.kencanme.mvp.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerLookPhotoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.LookPhotoModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;

/**
 * desc:查看照片大图 或者是全屏看视频
 */
public class LookPhotoActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.photo_iv)
    ImageView mPhotoIv;
    @BindView(R.id.jz_video)
    JzvdStd mJzVideo;


    public static void start(Context context, String path) {
        Intent intent = new Intent(context, LookPhotoActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_photo);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            String path = getIntent().getStringExtra("path");
            if (TranTools.isVideo(path)) {
                mPhotoIv.setVisibility(View.GONE);
                mJzVideo.setVisibility(View.VISIBLE);
                mJzVideo.setUp(path, "");
                PicUtils.loadVideoScreenshot(this, path, mJzVideo.thumbImageView, 0);  //获取视频第一帧显示
            } else {
                mJzVideo.setVisibility(View.GONE);
                mPhotoIv.setVisibility(View.VISIBLE);
                mImageLoaderHelper.displayMatchImage(this, path, mPhotoIv);
            }
        }
    }

    @Override
    public void initData() {
    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }

    private void initializeInjector() {
        DaggerLookPhotoComponent.builder().appComponent(getAppComponent())
                .lookPhotoModule(new LookPhotoModule(LookPhotoActivity.this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
