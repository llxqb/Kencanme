package com.shushan.kencanme.app.mvp.ui.activity.pay;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerRechargeComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.RechargeBeansModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.GooglePayHelper;
import com.shushan.kencanme.app.mvp.ui.activity.register.RechargeAgreementActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.RechargeAdapter;
import com.shushan.kencanme.app.mvp.utils.AppUtils;
import com.shushan.kencanme.app.mvp.utils.DataUtils;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.Purchase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;

/**
 * desc:购买嗨豆Activity
 */
public class RechargeActivity extends BaseActivity implements RechargeControl.RechargeView, GooglePayHelper.BuyFinishListener {

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
    private LoginUser mLoginUser;
    /**
     * google支付util类
     */
    private IabHelper iabHelper;

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
        mLoginUser = mBuProcessor.getLoginUser();
        //初始化google支付
        mGooglePayHelper = new GooglePayHelper(this, this);
        iabHelper = mGooglePayHelper.initGooglePay();
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
            createOrder(String.valueOf(beansinfoBean.getB_id()), beansinfoBean.getPrice());
        });
    }

    @Override
    public void initData() {
        mCurrentHiBeansNum.setText(String.valueOf(mLoginUser.beans));
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
                contactCustomer();
                break;
            case R.id.recharge_agreement:
                startActivitys(RechargeAgreementActivity.class);
                break;
            case R.id.contact_customer:
//                showToast("联系客服");
                contactCustomer();
                break;
        }
    }

    /**
     * 练习客服
     */
    private void contactCustomer() {
        //进入客服
        //首先需要构造使用客服者的用户信息
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName(mLoginUser.nickname).build();
        /**
         * 启动客户服聊天界面。
         * @param context           应用上下文。
         * @param customerServiceId 要与之聊天的客服 Id。
         * @param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
         * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
         */
        RongIM.getInstance().startCustomerServiceChat(this, ServerConstant.RY_CUSTOMER_ID, getResources().getString(R.string.online_customer), csInfo);
        mSharePreferenceUtil.setData("chatType", 1);//在线客服
    }

    /**
     * 创建订单
     * type:1购买会员 2购买嗨豆
     * relation_id:对应购买 会员/嗨豆id
     */
    private void createOrder(String relation_id, String price) {
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
        mGooglePayHelper.queryGoods(DataUtils.uppercaseToLowercase(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //一定记得调用这个方法，才能调起
        if (iabHelper != null && requestCode == Constant.GOOGLE_PAY_REQ) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 支付成功
     */
    @Override
    public void buyFinishSuccess(Purchase purchase) {
        showToast(new Gson().toJson(purchase));
        //上传数据到服务器

        //查询用户信息-->更新用户信息(我的-首页接口)
        requestHomeUserInfo();

    }

    /**
     * 支付失败或取消
     */
    @Override
    public void buyFinishFail() {
        showToast("支付取消");
    }

    /**
     * 查询我的-首页接口，更新用户信息(首页)
     */
    private void requestHomeUserInfo() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);
    }

    /**
     * 我的-首页接口  查询成功
     */
    @Override
    public void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
        mLoginUser.userType = AppUtils.userType(userBean.getSvip(), userBean.getVip(), userBean.getSex());
        mLoginUser.exposure = userBean.getExposure();
        mLoginUser.beans = userBean.getBeans();
        mLoginUser.exposure_type = userBean.getExposure_type();
        mLoginUser.exposure_time = userBean.getExposure_time();
        mLoginUser.today_like = userBean.getToday_like();
        mLoginUser.today_chat = userBean.getToday_chat();
        mLoginUser.today_see_contact = userBean.getToday_see_contact();
        mBuProcessor.setLoginUser(mLoginUser);
        //更新界面UI
        updateUi();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.PAY_SUCCESS_UPDATE_INFO));
    }

    /**
     * 支付成功后更新UI
     */
    private void updateUi() {
        mLoginUser = mBuProcessor.getLoginUser();
        mCurrentHiBeansNum.setText(String.valueOf(mLoginUser.beans));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGooglePayHelper != null) mGooglePayHelper.dispose();
    }


    private void initializeInjector() {
        DaggerRechargeComponent.builder().appComponent(getAppComponent())
                .rechargeBeansModule(new RechargeBeansModule(RechargeActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
