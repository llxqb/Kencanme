package com.shushan.kencanme.app.mvp.ui.activity.setting;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.mvp.utils.SystemUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.app_version_name)
    TextView mVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.AboutUsActivity_title));
        if (BuildConfig.DEBUG) {
            mVersionName.setText("测试：" + SystemUtils.getVersionName(this));
        } else {
            mVersionName.setText(SystemUtils.getVersionName(this));
        }
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
