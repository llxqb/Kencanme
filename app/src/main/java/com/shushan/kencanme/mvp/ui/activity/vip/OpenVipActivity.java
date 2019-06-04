package com.shushan.kencanme.mvp.ui.activity.vip;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.ui.activity.register.AgreementActivity;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买/打开 会员
 */
public class OpenVipActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.line_customer)
    ImageView mLineCustomer;
    @BindView(R.id.avator)
    CircleImageView mAvator;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.is_vip_tv)
    TextView mIsVipTv;
    @BindView(R.id.vip_hint_tv)
    TextView mVipHintTv;
    @BindView(R.id.current_price)
    TextView mCurrentPrice;
    @BindView(R.id.original_price)
    TextView mOriginalPrice;
    @BindView(R.id.open_vip_super_vip_rl)
    RelativeLayout mOpenVipSuperVipRl;
    @BindView(R.id.vip_type_recycler_view)
    RecyclerView mVipTypeRecyclerView;
    @BindView(R.id.vip_agreement)
    TextView mVipAgreement;
    @BindView(R.id.vip_privileges_recycler_view)
    RecyclerView mVipPrivilegesRecyclerView;
    @BindView(R.id.pay_money_value)
    TextView mPayMoneyValue;
    @BindView(R.id.go_to_pay)
    TextView mGoToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vip);
        ButterKnife.bind(this);
        //设置有图片状态栏
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

    @OnClick({R.id.back, R.id.line_customer, R.id.open_vip_super_vip_rl, R.id.vip_agreement, R.id.go_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.line_customer:
                //在线客服
                break;
            case R.id.open_vip_super_vip_rl:
                //成为超级VIP
                break;
            case R.id.vip_agreement:
                //打开协议
                startActivitys(AgreementActivity.class);
                break;
            case R.id.go_to_pay:
                //去支付
                break;
        }
    }
}
