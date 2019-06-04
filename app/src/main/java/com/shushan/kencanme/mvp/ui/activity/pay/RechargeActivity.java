package com.shushan.kencanme.mvp.ui.activity.pay;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.response.RechargeBean;
import com.shushan.kencanme.mvp.ui.activity.register.AgreementActivity;
import com.shushan.kencanme.mvp.ui.adapter.RechargeAdapter;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 充值Activity
 */
public class RechargeActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonIvRight;
    @BindView(R.id.current_hi_beans_num)
    TextView mCurrentHiBeansNum;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recharge_agreement)
    TextView mRechargeAgreement;
    @BindView(R.id.contact_customer)
    TextView mContactCustomer;
    private List<RechargeBean> rechargeBeanList = new ArrayList<>();
    private RechargeAdapter rechargeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.RechargeActivity_title));
        mRechargeAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mRechargeAgreement.getPaint().setAntiAlias(true);//抗锯齿
        mContactCustomer.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mContactCustomer.getPaint().setAntiAlias(true);//抗锯齿

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        rechargeAdapter = new RechargeAdapter(this, rechargeBeanList);
        mRecyclerView.setAdapter(rechargeAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                RechargeBean rechargeBean = new RechargeBean();
                rechargeBean.HiBeanNum = 588;
                rechargeBean.desc = "First recharge gift of 100";
                rechargeBean.isRecommendLabel = false;
                rechargeBean.rpMoney = "Rp 588";
                rechargeBeanList.add(rechargeBean);
            } else {
                if (i % 2 == 0) {
                    RechargeBean rechargeBean = new RechargeBean();
                    rechargeBean.HiBeanNum = 2888;
                    rechargeBean.desc = "Give 100 / ";
                    rechargeBean.vipDesc = "VIP give 200";
                    rechargeBean.rpMoney = "Rp 1000";
                    rechargeBean.isRecommendLabel = false;
                    rechargeBeanList.add(rechargeBean);
                } else if (i % 2 == 1) {
                    RechargeBean rechargeBean = new RechargeBean();
                    rechargeBean.HiBeanNum = 7888;
                    rechargeBean.desc = "Give 300 / ";
                    rechargeBean.vipDesc = "VIP give 600";
                    rechargeBean.rpMoney = "Rp 5000";
                    rechargeBean.isRecommendLabel = true;
                    rechargeBeanList.add(rechargeBean);
                }
            }
        }
//        rechargeAdapter.addData(rechargeBeanList);
    }

    @OnClick({R.id.common_back, R.id.common_iv_right, R.id.recharge_agreement, R.id.contact_customer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
                break;
            case R.id.recharge_agreement:
                startActivitys(AgreementActivity.class);
                break;
            case R.id.contact_customer:
                break;
        }
    }

}
