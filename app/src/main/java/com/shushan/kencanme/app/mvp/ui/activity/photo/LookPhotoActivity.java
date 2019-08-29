package com.shushan.kencanme.app.mvp.ui.activity.photo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerLookPhotoComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.LookPhotoModule;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.utils.TranTools;
import com.shushan.kencanme.app.mvp.views.NoFullScreenJzvdStd;
import com.shushan.kencanme.app.mvp.views.ResizableImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:查看照片大图 或者是全屏看视频
 */
public class LookPhotoActivity extends BaseActivity {
    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.photo_iv)
    ResizableImageView mPhotoIv;
    @BindView(R.id.jz_video)
    NoFullScreenJzvdStd mJzVideo;

    public static void start(Context context, String path) {
        Intent intent = new Intent(context, LookPhotoActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                PicUtils.loadVideoScreenshot(this, path, mJzVideo.thumbImageView, 0,true);  //获取视频第一帧显示
            } else {
                mJzVideo.setVisibility(View.GONE);
                mPhotoIv.setVisibility(View.VISIBLE);
                mImageLoaderHelper.displayImage(this, path, mPhotoIv, Constant.LOADING_BIG);
            }
        }
    }

    @Override
    public void initData() {
    }

    private void initializeInjector() {
        DaggerLookPhotoComponent.builder().appComponent(getAppComponent())
                .lookPhotoModule(new LookPhotoModule(LookPhotoActivity.this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    @OnClick({R.id.common_back, R.id.photo_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.photo_iv:
                finish();
                break;
        }
    }
}
