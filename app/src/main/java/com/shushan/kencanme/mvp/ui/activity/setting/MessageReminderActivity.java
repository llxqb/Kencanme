package com.shushan.kencanme.mvp.ui.activity.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.utils.SharePreferenceUtil;

import javax.inject.Inject;

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
    private boolean showMessagePreview;
    private boolean shock;
    private boolean voice;

    @Inject
    protected SharePreferenceUtil mSharePreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_reminder);
        ButterKnife.bind(this);
        ((KencanmeApp) getApplication()).getAppComponent().inject(this);
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.MessageReminderActivity_title));
    }

    @Override
    public void initData() {
        showMessagePreview = mSharePreferenceUtil.getBooleanData("show_message_preview");
        shock = mSharePreferenceUtil.getBooleanData("shock");
        voice = mSharePreferenceUtil.getBooleanData("voice");
        if (showMessagePreview) {
            mMessagePreviewIv.setImageResource(R.mipmap.mine_install_open);
        } else {
            mMessagePreviewIv.setImageResource(R.mipmap.mine_install_close);
        }
        if (shock) {
            mShockIv.setImageResource(R.mipmap.mine_install_open);
        } else {
            mShockIv.setImageResource(R.mipmap.mine_install_close);
        }
        if (voice) {
            mVoiceIv.setImageResource(R.mipmap.mine_install_open);
        } else {
            mVoiceIv.setImageResource(R.mipmap.mine_install_close);
        }
    }

    @OnClick({R.id.common_back, R.id.message_preview_iv, R.id.shock_iv, R.id.voice_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.message_preview_iv:
                if (showMessagePreview) {
                    //关闭
                    mSharePreferenceUtil.setData("show_message_preview", false);
                    mMessagePreviewIv.setImageResource(R.mipmap.mine_install_close);
                } else {
                    //打开
                    mSharePreferenceUtil.setData("show_message_preview", true);
                    mMessagePreviewIv.setImageResource(R.mipmap.mine_install_open);
                }
                break;
            case R.id.shock_iv:
                if (shock) {
                    //关闭
                    mSharePreferenceUtil.setData("shock", false);
                    mShockIv.setImageResource(R.mipmap.mine_install_close);
                } else {
                    //打开
                    mSharePreferenceUtil.setData("shock", true);
                    mShockIv.setImageResource(R.mipmap.mine_install_open);
                }
                break;
            case R.id.voice_iv:
                if (voice) {
                    //关闭
                    mSharePreferenceUtil.setData("voice", false);
                    mVoiceIv.setImageResource(R.mipmap.mine_install_close);
                } else {
                    //打开
                    mSharePreferenceUtil.setData("voice", true);
                    mVoiceIv.setImageResource(R.mipmap.mine_install_open);
                }
                break;
        }
    }
}
