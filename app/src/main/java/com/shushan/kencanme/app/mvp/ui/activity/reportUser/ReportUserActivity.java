package com.shushan.kencanme.app.mvp.ui.activity.reportUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerReportUserComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.ReportUserModule;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.ReportUserListResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.mvp.ui.adapter.ReportUserAdapter;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:举报用户
 */
public class ReportUserActivity extends BaseActivity implements ReportUserControl.ReportUserView {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private ReportUserAdapter mReportUserAdapter;
    private String uid;
    private List<ReportUserListResponse.DataBean> mList = new ArrayList<>();
    LoginUser mLoginUser;
    @Inject
    ReportUserControl.PresenterReportUser mPresenter;

    public static void start(Context context, String uid) {
        Intent intent = new Intent(context, ReportUserActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_user);
        ButterKnife.bind(this);
        initializeInjector();
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mLoginUser = mBuProcessor.getLoginUser();
        mTitleName.setText(getResources().getString(R.string.ReportUserActivity_title));
        if (getIntent() != null) {
            uid = getIntent().getStringExtra("uid");
        }
    }

    @Override
    public void initData() {
        onRequestReportUser();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReportUserAdapter = new ReportUserAdapter(mList);
        mRecyclerView.setAdapter(mReportUserAdapter);

        mReportUserAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ReportUserListResponse.DataBean dataBean = (ReportUserListResponse.DataBean) adapter.getItem(position);
                DataFraudActivity.start(ReportUserActivity.this, String.valueOf(dataBean.getId()), dataBean.getReason(), uid);
            }
        });
    }

    private void onRequestReportUser() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mLoginUser.token;
        mPresenter.onRequestReportUserList(tokenRequest);
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
//            case R.id.data_fraud:
//                //头像资料作假
//                DataFraudActivity.start(this, getResources().getString(R.string.DataFraudActivity_head_portraits),uid,"");
//                break;
//            case R.id.advertising_and_marketing:
//                //广告、营销
//                DataFraudActivity.start(this,  getResources().getString(R.string.ReportUserActivity_advertising),uid);
//                break;
//            case R.id.fraud:
//                //诈骗、托儿
//                DataFraudActivity.start(this,  getResources().getString(R.string.ReportUserActivity_Fraud),uid);
//                break;
//            case R.id.pornography_and_vulgarity:
//                //色情低俗
//                DataFraudActivity.start(this,  getResources().getString(R.string.ReportUserActivity_Pornography),uid);
//                break;
//            case R.id.language_uncivilized:
//                //语言不文明
//                DataFraudActivity.start(this,  getResources().getString(R.string.ReportUserActivity_Malicious),uid);
//                break;
//            case R.id.other:
//                //其他
//                DataFraudActivity.start(this,  getResources().getString(R.string.ReportUserActivity_Other),uid);
//                break;
        }
    }

    @Override
    public void reportUserListSuccess(ReportUserListResponse reportUserListResponse) {
        mList = reportUserListResponse.getData();
        mReportUserAdapter.setNewData(mList);
    }

    @Override
    public void reportUserSuccess(String msg) {
    }

    @Override
    public void uploadImageSuccess(String picPath) {
    }

    private void initializeInjector() {
        DaggerReportUserComponent.builder().appComponent(getAppComponent())
                .reportUserModule(new ReportUserModule(ReportUserActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
