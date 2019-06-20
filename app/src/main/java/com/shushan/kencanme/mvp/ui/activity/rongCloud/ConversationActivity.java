package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.model.Conversation;

/**
 * 打开消息界面
 */
public class ConversationActivity extends BaseActivity implements CommonChoiceDialog.commonChoiceDialogListener{

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonRightIv;
    private String mTargetId;//对话对象id
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonRightIv.setVisibility(View.VISIBLE);
        if (getIntent() != null) {
            initIntent(getIntent());
        }
    }

    @Override
    public void initData() {

    }


    private void initIntent(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mCommonTitleTv.setText(intent.getData().getQueryParameter("title"));
        mConversationType = Conversation.ConversationType.valueOf("PRIVATE");
    }


    @OnClick({R.id.common_back, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
                commonChoiceDialog.setListener(this);
                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);
                break;
        }
    }

    @Override
    public void deleteUserListener() {

    }

    @Override
    public void blackUserListener() {

    }

    @Override
    public void reportUserListener() {

    }
}
