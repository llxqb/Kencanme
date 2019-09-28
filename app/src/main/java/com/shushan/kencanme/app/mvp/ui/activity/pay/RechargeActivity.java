package com.shushan.kencanme.app.mvp.ui.activity.pay;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahdi.sdk.payment.AhdiPay;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerRechargeComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.RechargeBeansModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.PayFinishAHDIRequest;
import com.shushan.kencanme.app.entity.request.PayFinishByUniPinRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.ReChargeBeansInfoRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderAHDIRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderAHDIResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderByUniPinResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.ReChargeBeansInfoResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.help.GooglePayHelper;
import com.shushan.kencanme.app.mvp.ui.activity.register.RechargeAgreementActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.RechargeAdapter;
import com.shushan.kencanme.app.mvp.utils.DataUtils;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.Purchase;
import com.shushan.kencanme.app.mvp.views.CommonDialog;
import com.shushan.kencanme.app.mvp.views.dialog.PayReportErrorDialog;
import com.shushan.kencanme.app.mvp.views.dialog.PaySelectDialog;

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
 * 三种支付方式：
 * 1、google支付
 * 2、Ahdi支付
 * 3、UniPin支付
 */
public class RechargeActivity extends BaseActivity implements RechargeControl.RechargeView, GooglePayHelper.BuyFinishListener, PaySelectDialog.payChoiceDialogListener,
        PayReportErrorDialog.PayReportDialogListener, CommonDialog.CommonDialogListener {

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
    //点击item
    ReChargeBeansInfoResponse.BeansinfoBean beansinfoBean;
    /**
     * 是否是打开了UniPin支付网页页面
     */
    private boolean openUniPinWeb = false;
    /**
     * 支付类型 1：Google   2:AHDI  3:Unipin
     */
    private int mPayType;
    /**
     * 上传fb sdk
     * 支付金额
     */
    private String payMoney;
    @Inject
    RechargeControl.PresenterRecharge mPresenter;
    private String errorInfo1;
    private String errorInfo2;

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
            beansinfoBean = (ReChargeBeansInfoResponse.BeansinfoBean) adapter.getItem(position);
            assert beansinfoBean != null;
            for (ReChargeBeansInfoResponse.BeansinfoBean bean : rechargeBeanList) {
                if (bean.isCheck) bean.isCheck = false;
            }
            beansinfoBean.isCheck = true;
            rechargeAdapter.notifyDataSetChanged();
            //1、选择支付方式的弹框
            showPayChooseDialog();
        });
    }

    @Override
    public void initData() {
        errorInfo1 = getResources().getString(R.string.PayReportErrorDialog_error_info1);
        errorInfo2 = getResources().getString(R.string.PayReportErrorDialog_error_info2);
        mCurrentHiBeansNum.setText(String.valueOf(mLoginUser.beans));
        ReChargeBeansInfoRequest reChargeBeansInfoRequest = new ReChargeBeansInfoRequest();
        reChargeBeansInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBeansInfo(reChargeBeansInfoRequest);
    }

    @OnClick({R.id.common_back, R.id.common_iv_right, R.id.recharge_agreement, R.id.contact_customer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.common_iv_right:
                contactCustomer();
                break;
            case R.id.recharge_agreement:
                startActivitys(RechargeAgreementActivity.class);
                break;
            case R.id.contact_customer:
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
        //启动客户服聊天界面。
        //         * @param context           应用上下文。
        //         * @param customerServiceId 要与之聊天的客服 Id。
        //         * @param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
        //         * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
        RongIM.getInstance().startCustomerServiceChat(this, ServerConstant.RY_CUSTOMER_ID, getResources().getString(R.string.online_customer), csInfo);
        mSharePreferenceUtil.setData("chatType", 1);//在线客服
    }

    /**
     * 获取充值Beans列表
     */
    @Override
    public void RechargeBeansInfoSuccess(ReChargeBeansInfoResponse reChargeBeansInfoResponse) {
        rechargeBeanList = reChargeBeansInfoResponse.getBeansinfo();
        rechargeAdapter.addData(rechargeBeanList);
    }

    /**
     * 选择三种支付方式弹框
     */
    private void showPayChooseDialog() {
        PaySelectDialog paySelectDialog = PaySelectDialog.newInstance();
        paySelectDialog.setListener(this);
        paySelectDialog.setMoney(beansinfoBean.getPrice(), beansinfoBean.getYn_price());
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), paySelectDialog, PaySelectDialog.TAG);
    }

    @Override
    public void payType(int payType) {
        mPayType = payType;
        payMoney = beansinfoBean.getPrice();
        switch (payType) {
            case 1:
                GooglePayChoose();
                break;
            case 2:
                AHDIPayChoose();
                break;
            case 3:
                UNiPinPayChoose();
                break;
        }
    }

    private void GooglePayChoose() {
        //2.创建订单 - google支付
        createOrderGoogle(String.valueOf(beansinfoBean.getB_id()), beansinfoBean.getPrice());
    }

    private void AHDIPayChoose() {
        //2.创建订单 - AHDI支付
        createOrderAHDI(String.valueOf(beansinfoBean.getB_id()), String.valueOf(beansinfoBean.getYn_price()));
    }

    private void UNiPinPayChoose() {
        //2.创建订单 - UniPin支付
        createOrderByUniPin(String.valueOf(beansinfoBean.getB_id()), String.valueOf(beansinfoBean.getYn_price()));
    }

    /**
     * 创建订单 google支付
     * type:1购买会员 2购买嗨豆
     * relation_id:对应购买 会员/嗨豆id
     */
    private void createOrderGoogle(String relation_id, String price) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.token = mBuProcessor.getToken();
        createOrderRequest.type = "2";
        createOrderRequest.relation_id = relation_id;
        createOrderRequest.money = price;
        createOrderRequest.from = Constant.FROM;
        mPresenter.onRequestCreateOrder(createOrderRequest);
    }

    /**
     * 创建订单成功--Google
     */
    @Override
    public void createOrderGoogleSuccess(CreateOrderResponse createOrderResponse) {
        //购买嗨豆
        mGooglePayHelper.queryGoods(DataUtils.uppercaseToLowercase(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //一定记得调用这个方法，才能调起Google回调
        if (iabHelper != null && requestCode == Constant.GOOGLE_PAY_REQ) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }

    private Purchase mPurchase;

    /**
     * 支付成功---Google
     */
    @Override
    public void buyFinishSuccess(Purchase purchase) {
        mPurchase = purchase;
        payFinishGoogleUpload(mPurchase);
    }

    /**
     * 支付成功上报--GOOGLE
     */
    private void payFinishGoogleUpload(Purchase purchase) {
        //上传数据到服务器
        PayFinishUploadRequest payFinishUploadRequest = new PayFinishUploadRequest();
        payFinishUploadRequest.order_no = purchase.getDeveloperPayload();
        payFinishUploadRequest.INAPP_DATA_SIGNATURE = purchase.getSignature();
        payFinishUploadRequest.INAPP_PURCHASE_DATA = purchase.getOriginalJson();
        mPresenter.onPayFinishUpload(payFinishUploadRequest);
    }

    /**
     * 支付失败或取消--Google
     */
    @Override
    public void buyFinishFail() {
    }


    @Override
    public void getPayFinishGoogleUploadSuccess() {
        //查询用户信息-->更新用户信息(我的-首页接口)
        requestHomeUserInfo();
        logAddPaymentInfoEvent(true);
    }

    /**
     * 支付成功但是上传失败--》重新上报
     */
    @Override
    public void getPayFinishGoogleUploadFail(String error) {
        reportPayDialog(errorInfo1);
    }

    @Override
    public void getPayFinishGoogleUploadThowable() {
        showToast(getResources().getString(R.string.text_check_internet));
        reportPayDialog(errorInfo2);
    }


    /**
     * 创建订单 AHDI订单
     */
    private void createOrderAHDI(String relation_id, String price) {
        RequestOrderAHDIRequest requestOrderAHDIRequest = new RequestOrderAHDIRequest();
        requestOrderAHDIRequest.token = mLoginUser.token;
        requestOrderAHDIRequest.type = "2";
        requestOrderAHDIRequest.relation_id = relation_id;
        requestOrderAHDIRequest.money = price;
        mPresenter.onRequestCreateOrderAHDI(requestOrderAHDIRequest);
    }

    private CreateOrderAHDIResponse mCreateOrderAHDIResponse;

    /**
     * 创建订单成功--AHDI
     */
    @Override
    public void createOrderAHDISuccess(CreateOrderAHDIResponse createOrderAHDIResponse) {
        //调用 SDK 的 startPay 方法发起支付
        AhdiPay.startPay(this, createOrderAHDIResponse.getAppid(), createOrderAHDIResponse.getApp_userid(), createOrderAHDIResponse.getToken(), (resultCode, signValue, resultInfo) -> {
            if (resultCode == AhdiPay.PAY_SUCCESS) {
                //支付成功，上传数据到服务器
                mCreateOrderAHDIResponse = createOrderAHDIResponse;
                payFinishAHDIUpload(mCreateOrderAHDIResponse);
            } else {
                showToast(getResources().getString(R.string.payment_fail));
            }
        });
    }

    /**
     * 支付成功上报--AHDI
     */
    private void payFinishAHDIUpload(CreateOrderAHDIResponse createOrderAHDIResponse) {
        PayFinishAHDIRequest payFinishAHDIRequest = new PayFinishAHDIRequest();
        payFinishAHDIRequest.token = mLoginUser.token;
        payFinishAHDIRequest.order_no = createOrderAHDIResponse.getOrder_no();
        mPresenter.onPayFinishAHDIUpload(payFinishAHDIRequest);
    }

    /**
     * AHDI上报 成功
     */
    @Override
    public void getPayFinishAHDIUploadSuccess() {
        //查询用户信息-->更新用户信息(我的-首页接口)
        requestHomeUserInfo();
        logAddPaymentInfoEvent(true);
    }

    /**
     * AHDI上报 失败
     */
    @Override
    public void getPayFinishAHDIUploadFail(String error) {
        reportPayDialog(errorInfo1);
    }

    @Override
    public void getPayFinishAHDIUploadThowable() {
        showToast(getResources().getString(R.string.text_check_internet));
        reportPayDialog(errorInfo2);
    }


    /**
     * 创建订单 UniPin订单
     */
    private void createOrderByUniPin(String relation_id, String price) {
        RequestOrderUniPinPayRequest requestOrderUniPinPayRequest = new RequestOrderUniPinPayRequest();
        requestOrderUniPinPayRequest.token = mLoginUser.token;
        requestOrderUniPinPayRequest.type = "2";
        requestOrderUniPinPayRequest.relation_id = relation_id;
        requestOrderUniPinPayRequest.money = price;
        mPresenter.onRequestCreateOrderByUniPin(requestOrderUniPinPayRequest);
    }


    CreateOrderByUniPinResponse mCreateOrderByUniPinResponse;

    /**
     * 创建订单成功--UniPin
     */
    @Override
    public void createOrderByUniPinSuccess(CreateOrderByUniPinResponse createOrderByUniPinResponse) {
//        Log.e("ddd", "createOrderByUniPinResponse:" + new Gson().toJson(createOrderByUniPinResponse));
        mCreateOrderByUniPinResponse = createOrderByUniPinResponse;
        openUniPinWeb = true;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(createOrderByUniPinResponse.getUrl());
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (openUniPinWeb) {
            payFinishUnipinUpload(true);
        }
    }

    /**
     * 支付成功上报--UniPin
     */
    private void payFinishUnipinUpload(boolean showLoading) {
        openUniPinWeb = false;
        if (showLoading) {
            showLoading(getResources().getString(R.string.loading));
        }
        //UniPin支付上报
        String orderId = mCreateOrderByUniPinResponse.getOrder_no();
        PayFinishByUniPinRequest payFinishByUniPinRequest = new PayFinishByUniPinRequest();
        payFinishByUniPinRequest.order_no = orderId;
        mPresenter.onPayFinishUploadByUniPin(payFinishByUniPinRequest);
    }

    /**
     * UniPin上报成功
     */
    @Override
    public void getPayFinishUploadByUniPinSuccess() {
        //查询用户信息-->更新用户信息(我的-首页接口)
        requestHomeUserInfo();
        logAddPaymentInfoEvent(true);
    }

    private int UnipinPayNum = 1;

    /**
     * UniPin上报失败
     */
    @Override
    public void getPayFinishUploadByUniPinFail(String error) {
        showToast(error);
        //不知道是取消还是上报异常操作 没有验证
        if (UnipinPayNum < 2) {
            UnipinPayNum++;
            delayTimeUnloadUnipin();
        }
    }

    @Override
    public void getPayFinishUploadByUniPinThowable() {
        showToast(getResources().getString(R.string.text_check_internet));
        reportPayDialog(errorInfo2);
    }

    private void delayTimeUnloadUnipin() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                payFinishUnipinUpload(false);
            }
        }, 1000);//3秒后执行Runnable中的run方法
    }

    /**
     * 重新上报dialog
     */
    private void reportPayDialog(String title) {
        PayReportErrorDialog payReportErrorDialog = PayReportErrorDialog.newInstance();
        payReportErrorDialog.setListener(this);
        payReportErrorDialog.setTitle(title);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payReportErrorDialog, PayReportErrorDialog.TAG);
    }

    @Override
    public void payReportBtnOkListener() {
        switch (mPayType) {
            case 1:
                if (mPurchase != null) {
                    payFinishGoogleUpload(mPurchase);
                }
                break;
            case 2:
                if (mCreateOrderAHDIResponse != null) {
                    payFinishAHDIUpload(mCreateOrderAHDIResponse);
                }
                break;
            case 3:
                payFinishUnipinUpload(true);
                break;
        }
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
        showToast(getResources().getString(R.string.success));
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
        mBuProcessor.setLoginUser(LoginUtils.upDateLoginUser(mLoginUser, userBean));
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

    /**
     * 记录facebook 支付成功后数据
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logAddPaymentInfoEvent(boolean success) {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD");
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "购买嗨豆");
        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, success ? 1 : 0);
        logger.logEvent(AppEventsConstants.EVENT_NAME_PURCHASED, Double.parseDouble(payMoney), params);
    }


    @Override
    public void onBackPressed() {
        showBackDialog();
    }

    private void showBackDialog() {
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.back_purchase_cancel), Constant.DIALOG_FIVE);
    }

    @Override
    public void commonDialogBtnOkListener() {
        super.onBackPressed();
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
