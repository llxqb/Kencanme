package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerSystemMsgComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.SystemMsgModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.SystemMsgRequest;
import com.shushan.kencanme.entity.response.SystemMsgResponse;
import com.shushan.kencanme.mvp.ui.adapter.SystemMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemMsgActivity extends BaseActivity implements SystemMsgControl.SystemMsgView {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonIvRight;
    @BindView(R.id.system_msg_recycler_view)
    RecyclerView mSystemMsgRecyclerView;
    int page = 1;
    String pageSize = "10";
    private View mEmptyView;
    List<SystemMsgResponse.DataBean> systemMsgResponseList = new ArrayList<>();
    SystemMsgAdapter systemMsgAdapter;
    @Inject
    SystemMsgControl.PresenterSystemMsg mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.no_message_layout, (ViewGroup) mSystemMsgRecyclerView.getParent(), false);
        mCommonTitleTv.setText(getResources().getString(R.string.SystemMsgActivity_title));
        mImageLoaderHelper.displayImage(this, R.mipmap.system_message_clean, mCommonIvRight, R.mipmap.system_message_clean);
        mSystemMsgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        systemMsgAdapter = new SystemMsgAdapter(this, systemMsgResponseList, mImageLoaderHelper);
        mSystemMsgRecyclerView.setAdapter(systemMsgAdapter);
    }

    @Override
    public void initData() {
        SystemMsgRequest systemMsgRequest = new SystemMsgRequest();
        systemMsgRequest.token = mBuProcessor.getToken();
        systemMsgRequest.page = String.valueOf(page);
        systemMsgRequest.pagesize = pageSize;
        mPresenter.onRequestSystemMsgList(systemMsgRequest);
    }

    @OnClick({R.id.common_back, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
                break;
        }
    }

    @Override
    public void getSystemMsgSuccess(SystemMsgResponse systemMsgResponse) {
        if (page == 1) {
            if (systemMsgResponse.getData().size() > 0) {
                systemMsgAdapter.addData(systemMsgResponse.getData());
            } else {
                systemMsgAdapter.setEmptyView(mEmptyView);
            }
        } else {
            systemMsgAdapter.addData(systemMsgResponse.getData());
        }
    }

    private void initializeInjector() {
        DaggerSystemMsgComponent.builder().appComponent(getAppComponent())
                .systemMsgModule(new SystemMsgModule(SystemMsgActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
