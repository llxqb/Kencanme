package com.shushan.kencanme.mvp.ui.activity.reportUser;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 举报用户
 */
public class ReportUserActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.data_fraud)
    TextView mDataFraud;
    @BindView(R.id.advertising_and_marketing)
    TextView mAdvertisingAndMarketing;
    @BindView(R.id.fraud)
    TextView mFraud;
    @BindView(R.id.pornography_and_vulgarity)
    TextView mPornographyAndVulgarity;
    @BindView(R.id.language_uncivilized)
    TextView mLanguageUncivilized;
    @BindView(R.id.other)
    TextView mOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.back, R.id.data_fraud, R.id.advertising_and_marketing, R.id.fraud, R.id.pornography_and_vulgarity, R.id.language_uncivilized, R.id.other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.data_fraud:
                //头像资料作假
                startActivitys(DataFraudActivity.class);
                break;
            case R.id.advertising_and_marketing:
                //广告、营销
                break;
            case R.id.fraud:
                //诈骗、托儿
                break;
            case R.id.pornography_and_vulgarity:
                //色情低俗
                break;
            case R.id.language_uncivilized:
                //语言不文明
                break;
            case R.id.other:
                //其他
                break;
        }
    }
}
