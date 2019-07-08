package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.response.SystemMsgResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统消息详情
 */
public class SystemMsgInfoActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.system_msg_title)
    TextView mSystemMsgTitle;
    @BindView(R.id.system_msg_content)
    TextView mSystemMsgContent;

    public static void start(Context context, SystemMsgResponse.DataBean dataBean) {
        Intent intent = new Intent(context, SystemMsgInfoActivity.class);
        intent.putExtra("dataBean", dataBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg_info);
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.SystemMsgActivity_title));
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            SystemMsgResponse.DataBean mDataBean = getIntent().getParcelableExtra("dataBean");
            mSystemMsgTitle.setText(mDataBean.getTitle());
            mSystemMsgContent.setText(mDataBean.getDetail());
        }
    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
