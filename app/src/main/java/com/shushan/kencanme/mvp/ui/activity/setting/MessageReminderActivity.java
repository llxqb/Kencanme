package com.shushan.kencanme.mvp.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:消息提醒
 */
public class MessageReminderActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.message_preview_iv)
    ImageView mMessagePreviewIv;
    @BindView(R.id.shock_iv)
    ImageView mShockIv;
    @BindView(R.id.voice_iv)
    ImageView mVoiceIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_reminder);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.MessageReminderActivity_title));
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.message_preview_iv, R.id.shock_iv, R.id.voice_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.message_preview_iv:
                break;
            case R.id.shock_iv:
                break;
            case R.id.voice_iv:
                break;
        }
    }
}
