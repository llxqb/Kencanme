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
import com.shushan.kencanme.di.components.DaggerRechargeComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.RechargeBeansModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.CreateOrderRequest;
import com.shushan.kencanme.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.entity.response.CreateOrderResponse;
import com.shushan.kencanme.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.help.GooglePayHelper;
import com.shushan.kencanme.mvp.ui.activity.register.RechargeAgreementActivity;
import com.shushan.kencanme.mvp.ui.adapter.RechargeAdapter;
import com.shushan.kencanme.mvp.utils.DataUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:购买嗨豆Activity
 */
public class RechargeActivity extends BaseActivity implements RechargeControl.RechargeView,GooglePayHelper.BuyFinishListener {

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
    private List<ReChargeBeansInfoResponse.BeansinfoBean> rechargeBeanList = new ArrayList<>();
    private RechargeAdapter rechargeAdapter;
    private GooglePayHelper mGooglePayHelper;

    @Inject
    RechargeControl.PresenterRecharge mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        ButterKnife.bind(this);
        initializeInjector();
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }

    @Override
    public void initView() {
        //初始化google支付
        mGooglePayHelper = new GooglePayHelper(this,this);
        mGooglePayHelper.initGooglePay();
        mCommonTitleTv.setText(getResources().getString(R.string.RechargeActivity_title));
        mRechargeAgreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mRechargeAgreement.getPaint().setAntiAlias(true);//抗锯齿
        mContactCustomer.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mContactCustomer.getPaint().setAntiAlias(true);//抗锯齿
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        rechargeAdapter = new RechargeAdapter(this, rechargeBeanList);
        mRecyclerView.setAdapter(rechargeAdapter);

        rechargeAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ReChargeBeansInfoResponse.BeansinfoBean beansinfoBean = (ReChargeBeansInfoResponse.BeansinfoBean) adapter.getItem(position);
            assert beansinfoBean != null;
            for (ReChargeBeansInfoResponse.BeansinfoBean bean : rechargeBeanList) {
                if (bean.isCheck) bean.isCheck = false;
            }
            beansinfoBean.isCheck = true;
            rechargeAdapter.notifyDataSetChanged();
            //1.创建订单
            //购买嗨豆
            createOrder( String.valueOf(beansinfoBean.getB_id()), beansinfoBean.getPrice());
        });
    }

    @Override
    public void initData() {
        mCurrentHiBeansNum.setText(String.valueOf(mBuProcessor.getLoginUser().beans));
        ReChargeBeansInfoRequest reChargeBeansInfoRequest = new ReChargeBeansInfoRequest();
        reChargeBeansInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBeansInfo(reChargeBeansInfoRequest);
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
                startActivitys(RechargeAgreementActivity.class);
                break;
            case R.id.contact_customer:
//                showToast("联系客服");
                break;
        }
    }


    /**
     * 创建订单
     * type:1购买会员 2购买嗨豆
     * relation_id:对应购买 会员/嗨豆id
     */
    private void createOrder( String relation_id, String price) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.token = mBuProcessor.getToken();
        createOrderRequest.type = "2";
        createOrderRequest.relation_id = relation_id;
        createOrderRequest.money = price;
        createOrderRequest.from = "Android";
        mPresenter.onRequestCreateOrder(createOrderRequest);
    }


    @Override
    public void RechargeBeansInfoSuccess(ReChargeBeansInfoResponse reChargeBeansInfoResponse) {
        rechargeBeanList = reChargeBeansInfoResponse.getBeansinfo();
        rechargeAdapter.addData(rechargeBeanList);
    }

    /**
     * 创建订单成功
     */
    @Override
    public void createOrderSuccess(CreateOrderResponse createOrderResponse) {
        //2、进行支付
        mGooglePayHelper.buyGoods(DataUtils.uppercaseToLowercase(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no());
    }

    @Override
    public void buyFinishSuccess(String orderId) {
        showToast("支付成功");
    }

    @Override
    public void buyFinishFail() {
        showToast("支付取消");
    }
    private void initializeInjector() {
        DaggerRechargeComponent.builder().appComponent(getAppComponent())
                .rechargeBeansModule(new RechargeBeansModule(RechargeActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }



}
