package com.shushan.kencanme.mvp.ui.activity.photo;

import android.os.Bundle;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;

/**
 * desc:查看照片大图 或者是全屏看视频
 */
public class LookPhotoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_photo);
        initView();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}
