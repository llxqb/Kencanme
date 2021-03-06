package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerSystemMsgComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.SystemMsgModule;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.SystemMsgRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.SystemMsgResponse;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.adapter.SystemMsgAdapter;
import com.shushan.kencanme.app.mvp.views.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SystemMsgActivity extends BaseActivity implements SystemMsgControl.SystemMsgView, BaseQuickAdapter.RequestLoadMoreListener, CommonDialog.CommonDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonIvRight;
    @BindView(R.id.system_msg_recycler_view)
    RecyclerView mSystemMsgRecyclerView;
    int page = 1;
    int pageSize = 10;
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
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mImageLoaderHelper.displayImage(this, R.mipmap.system_message_clean, mCommonIvRight, R.mipmap.system_message_clean);
        mCommonIvRight.setVisibility(View.VISIBLE);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.no_message_layout, (ViewGroup) mSystemMsgRecyclerView.getParent(), false);
        mCommonTitleTv.setText(getResources().getString(R.string.SystemMsgActivity_title));
        mImageLoaderHelper.displayImage(this, R.mipmap.system_message_clean, mCommonIvRight, R.mipmap.system_message_clean);
        mSystemMsgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        systemMsgAdapter = new SystemMsgAdapter(systemMsgResponseList);
        systemMsgAdapter.setOnLoadMoreListener(SystemMsgActivity.this, mSystemMsgRecyclerView);
        mSystemMsgRecyclerView.setAdapter(systemMsgAdapter);

        mSystemMsgRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SystemMsgResponse.DataBean dataBean = (SystemMsgResponse.DataBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.msg_item_ll:
                        SystemMsgInfoActivity.start(SystemMsgActivity.this, dataBean);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        requestMsgList();
    }

    private void requestMsgList() {
        SystemMsgRequest systemMsgRequest = new SystemMsgRequest();
        systemMsgRequest.token = mBuProcessor.getToken();
        systemMsgRequest.page = String.valueOf(page);
        systemMsgRequest.pagesize = String.valueOf(pageSize);
        mPresenter.onRequestSystemMsgList(systemMsgRequest);
    }

    @OnClick({R.id.common_back, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
                showClearMsgDialog();
                break;
        }
    }

    private void showClearMsgDialog() {
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.SystemMsgActivity_clear_msg_hint), Constant.DIALOG_FIVE);
    }

    private void clearMsg() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestDeleteSystemMsg(tokenRequest);
    }

    @Override
    public void getSystemMsgSuccess(SystemMsgResponse systemMsgResponse) {
        systemMsgResponseList = systemMsgResponse.getData();
        if (page == 1) {
            if (systemMsgResponse.getData().size() > 0) {
                systemMsgAdapter.setNewData(systemMsgResponseList);
                systemMsgAdapter.loadMoreComplete();
            } else {
                systemMsgAdapter.setEmptyView(mEmptyView);
            }
        } else {
            systemMsgAdapter.addData(systemMsgResponseList);
            systemMsgAdapter.loadMoreComplete();
        }
    }

    /**
     * 清空系统消息成功
     */
    @Override
    public void getDeleteMsgSuccess() {
        systemMsgAdapter.setNewData(null);
        systemMsgAdapter.setEmptyView(mEmptyView);
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        if (page == 1 && systemMsgResponseList.size() < 10) {
            systemMsgAdapter.loadMoreEnd(true);
        } else {
            if (systemMsgResponseList.size() < pageSize) {
                systemMsgAdapter.loadMoreEnd();
            } else {
                page++;
                requestMsgList();
            }
        }
    }

    /**
     * 清空系统消息
     */
    @Override
    public void commonDialogBtnOkListener() {
        clearMsg();
    }

    private void initializeInjector() {
        DaggerSystemMsgComponent.builder().appComponent(getAppComponent())
                .systemMsgModule(new SystemMsgModule(SystemMsgActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
