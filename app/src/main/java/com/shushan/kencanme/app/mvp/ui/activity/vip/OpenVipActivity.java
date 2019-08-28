package com.shushan.kencanme.app.mvp.ui.activity.vip;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahdi.sdk.payment.AhdiPay;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerOpenVipComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.OpenVipModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.entity.VipPrivilege;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.CreateOrderRequest;
import com.shushan.kencanme.app.entity.request.OpenVipRequest;
import com.shushan.kencanme.app.entity.request.PayFinishAHDIRequest;
import com.shushan.kencanme.app.entity.request.PayFinishByUniPinRequest;
import com.shushan.kencanme.app.entity.request.PayFinishUploadRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderAHDIRequest;
import com.shushan.kencanme.app.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.CreateOrderAHDIResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderByUniPinResponse;
import com.shushan.kencanme.app.entity.response.CreateOrderResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.OpenVipResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.help.GooglePayHelper;
import com.shushan.kencanme.app.mvp.ui.activity.register.MemberAgreementActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.OpenVipAdapter;
import com.shushan.kencanme.app.mvp.ui.adapter.VipPrivilegeAdapter;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.PayUtil;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.IabHelper;
import com.shushan.kencanme.app.mvp.utils.googlePayUtils.Purchase;
import com.shushan.kencanme.app.mvp.views.CircleImageView;
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
 * 购买/打开 会员
 */
public class OpenVipActivity extends BaseActivity implements OpenVipControl.OpenVipView, GooglePayHelper.BuyFinishListener, PaySelectDialog.payChoiceDialogListener,
        PayReportErrorDialog.PayReportDialogListener, CommonDialog.CommonDialogListener {

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
    @BindView(R.id.test_tv)
    TextView mTestTv;
    private List<OpenVipResponse.VipinfoBean> vipinfoBeanList = new ArrayList<>();
    private OpenVipAdapter openVipAdapter;
    private int[] mVipPrivilegeImg = {R.mipmap.privilege_vip_logo, R.mipmap.rectangle, R.mipmap.private_letter, R.mipmap.vip_photo_open, R.mipmap.vip_video_watch,
            R.mipmap.unlimited_love, R.mipmap.search, R.mipmap.active_secret_chat, R.mipmap.make_friend};
    private String[] mVipPrivilegeName = {"Supremacy VIP Sign", "Hey Bean on sale", "Free private messages", "unlimited member photos ", "unlimited member video",
            "unlimited like", "Check for who is like me(Super VIP)", "Start secret chat", "Making friends in the city"};
    //vip 特权
    private List<VipPrivilege> vipPrivilegeList = new ArrayList<>();
    private GooglePayHelper mGooglePayHelper;
    private OpenVipResponse.VipinfoBean mVipinfoBean;
    /**
     * google支付util类
     */
    private IabHelper iabHelper;
    private LoginUser mLoginUser;
    /**
     * 是否是打开了UniPin支付网页页面
     */
    private boolean openUniPinWeb = false;
    /**
     * 支付类型 1：Google   2:AHDI  3:Unipin
     */
    private int mPayType;
    @Inject
    OpenVipControl.PresenterOpenVip mPresenter;
    private String errorInfo1;
    private String errorInfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_vip);
        ButterKnife.bind(this);
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(this, null);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void initView() {
        //初始化google支付
        mGooglePayHelper = new GooglePayHelper(this, this);
        iabHelper = mGooglePayHelper.initGooglePay();
        mTitleName.setText(getResources().getString(R.string.OpenVipActivity_title));
        mVipTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        openVipAdapter = new OpenVipAdapter(this, vipinfoBeanList);
        mVipTypeRecyclerView.setAdapter(openVipAdapter);
        mVipPrivilegesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        VipPrivilegeAdapter vipPrivilegeAdapter = new VipPrivilegeAdapter(this, vipPrivilegeList, mImageLoaderHelper);
        mVipPrivilegesRecyclerView.setAdapter(vipPrivilegeAdapter);
        openVipAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mVipinfoBean = (OpenVipResponse.VipinfoBean) adapter.getItem(position);
            for (OpenVipResponse.VipinfoBean bean : vipinfoBeanList) {
                bean.isCheck = false;
            }
            assert mVipinfoBean != null;
            mVipinfoBean.isCheck = true;
            openVipAdapter.notifyDataSetChanged();
            String moneyValue = getResources().getString(R.string.money) + " " + mVipinfoBean.getSpecial_price();
            mPayMoneyValue.setText(moneyValue);
        });
    }


    @Override
    public void initData() {
        errorInfo1 = getResources().getString(R.string.PayReportErrorDialog_error_info1);
        errorInfo2 = getResources().getString(R.string.PayReportErrorDialog_error_info2);
        mLoginUser = mBuProcessor.getLoginUser();
        reqVipListRequest();
        for (int i = 0; i < mVipPrivilegeImg.length; i++) {
            VipPrivilege vipPrivilege = new VipPrivilege();
            vipPrivilege.pic = mVipPrivilegeImg[i];
            vipPrivilege.name = mVipPrivilegeName[i];
            vipPrivilegeList.add(vipPrivilege);
        }
        mImageLoaderHelper.displayImage(this, mLoginUser.trait, mAvator, R.mipmap.head_photo_loading);
        mUsername.setText(mLoginUser.nickname);
        if (mLoginUser.svip == 1) {
            mIsVipTv.setText(getResources().getString(R.string.is_svip));
        } else {
            if (mLoginUser.vip == 1) {
                mIsVipTv.setText(getResources().getString(R.string.is_vip));
            } else {
                mIsVipTv.setText(getResources().getString(R.string.not_vip));
            }
        }
    }

    @OnClick({R.id.back, R.id.line_customer, R.id.open_vip_super_vip_rl, R.id.vip_agreement, R.id.go_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.line_customer:
                //在线客服
                contactCustomer();
                break;
            case R.id.open_vip_super_vip_rl:
                //成为超级VIP
                break;
            case R.id.vip_agreement:
                //打开协议
                startActivitys(MemberAgreementActivity.class);
                break;
            case R.id.go_to_pay:
                //去支付
                //1.创建订单
                //购买会员
                if (mVipinfoBean != null) {
                    //1、选择支付方式的弹框
                    showPayChooseDialog();
                }
                break;
        }
    }

    private void contactCustomer() {
        //进入客服
        //首先需要构造使用客服者的用户信息
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName(mLoginUser.nickname).build();
        RongIM.getInstance().startCustomerServiceChat(this, ServerConstant.RY_CUSTOMER_ID, getResources().getString(R.string.online_customer), csInfo);
        mSharePreferenceUtil.setData("chatType", 1);//在线客服
    }

    /**
     * 请求vip集合
     */
    private void reqVipListRequest() {
        OpenVipRequest openVipRequest = new OpenVipRequest();
        openVipRequest.token = mBuProcessor.getToken();
        mPresenter.openVipListRequest(openVipRequest);
    }


    @Override
    public void OpenVipListSuccess(OpenVipResponse openVipResponse) {
        mVipHintTv.setText(openVipResponse.getNotice().toString());
        if (!payFinishUpdate) {
            mVipHintTv.setText(openVipResponse.getNotice().toString());
            vipinfoBeanList = openVipResponse.getVipinfo();
            for (int i = 0; i < vipinfoBeanList.size(); i++) {
                OpenVipResponse.VipinfoBean vipinfoBean = vipinfoBeanList.get(i);
                if (i == 0) {
                    mVipinfoBean = vipinfoBean;
                    vipinfoBean.isCheck = true;
                    String moneyValue = getResources().getString(R.string.money) + " " + vipinfoBean.getSpecial_price();
                    mPayMoneyValue.setText(moneyValue);
                }
            }
            openVipAdapter.setNewData(vipinfoBeanList);
        } else {
            payFinishUpdate = false;
        }
    }

    /**
     * 选择三种支付方式弹框
     */
    private void showPayChooseDialog() {
        PaySelectDialog paySelectDialog = PaySelectDialog.newInstance();
        paySelectDialog.setListener(this);
        paySelectDialog.setMoney(mVipinfoBean.getSpecial_price(), mVipinfoBean.getYn_special_price());
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), paySelectDialog, PaySelectDialog.TAG);
    }

    @Override
    public void payType(int payType) {
        mPayType = payType;
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
        createOrderGoogle(String.valueOf(mVipinfoBean.getV_id()));
    }

    private void AHDIPayChoose() {
        //2.创建订单 - AHDI支付
        createOrderAHDI(String.valueOf(mVipinfoBean.getV_id()), String.valueOf(mVipinfoBean.getYn_special_price()));
    }

    private void UNiPinPayChoose() {
        //2.创建订单 - UniPin支付
        createOrderByUniPin(String.valueOf(mVipinfoBean.getV_id()), String.valueOf(mVipinfoBean.getYn_special_price()));
    }

    /**
     * 创建订单
     * type:1购买会员 2购买嗨豆
     * relation_id:对应购买 会员/嗨豆id
     */
    private void createOrderGoogle(String relation_id) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.token = mBuProcessor.getToken();
        createOrderRequest.type = "1";
        createOrderRequest.relation_id = relation_id;
        createOrderRequest.money = mVipinfoBean.getSpecial_price();
        createOrderRequest.from = Constant.FROM;
        mPresenter.onRequestCreateOrder(createOrderRequest);
    }

    /**
     * 创建订单成功--Google
     */
    @Override
    public void createOrderSuccess(CreateOrderResponse createOrderResponse) {
        //2、进行支付
        mGooglePayHelper.queryGoods(PayUtil.payGoodId(createOrderResponse.getProduct_id()), createOrderResponse.getOrder_no());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //一定记得调用这个方法，才能调起
        if (iabHelper != null && requestCode == Constant.GOOGLE_PAY_REQ) {
            iabHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }

    private Purchase mPurchase;

    /**
     * 支付成功 ---Google
     */
    @Override
    public void buyFinishSuccess(Purchase purchase) {
        //上传数据到服务器
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
        requestOrderAHDIRequest.type = "1";
        requestOrderAHDIRequest.relation_id = relation_id;
        requestOrderAHDIRequest.money = price;
        mPresenter.onRequestCreateOrderAHDI(requestOrderAHDIRequest);
    }

    private CreateOrderAHDIResponse mCreateOrderAHDIResponse;

    /**
     * 创建订单成功--AHDI pay
     */
    @Override
    public void createOrderAHDISuccess(CreateOrderAHDIResponse createOrderAHDIResponse) {
        //调用 SDK 的 startPay 方法发起支付
        AhdiPay.startPay(this, createOrderAHDIResponse.getAppid(), createOrderAHDIResponse.getApp_userid(), createOrderAHDIResponse.getToken(), (resultCode, signValue, resultInfo) -> {
            if (resultCode == AhdiPay.PAY_SUCCESS) {
                //支付成功，上传数据到服务器
                mCreateOrderAHDIResponse = createOrderAHDIResponse;
                payFinishAHDIUpload(createOrderAHDIResponse);
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
    }

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
        requestOrderUniPinPayRequest.type = "1";
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
    }

    private int UnipinPayNum = 1;

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
     * 重新上报dialog
     */
    private void reportPayDialog(String title) {
        PayReportErrorDialog payReportErrorDialog = PayReportErrorDialog.newInstance();
        payReportErrorDialog.setListener(this);
        payReportErrorDialog.setTitle(title);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), payReportErrorDialog, PayReportErrorDialog.TAG);
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
     * 支付成功后更新数据，不用更新list
     */
    boolean payFinishUpdate = false;

    /**
     * 支付成功后更新UI
     */
    private void updateUi() {
        mLoginUser = mBuProcessor.getLoginUser();
        if (mLoginUser.svip == 1) {
            mIsVipTv.setText(getResources().getString(R.string.is_svip));
        } else {
            if (mLoginUser.vip == 1) {
                mIsVipTv.setText(getResources().getString(R.string.is_vip));
            } else {
                mIsVipTv.setText(getResources().getString(R.string.not_vip));
            }
        }
        payFinishUpdate = true;
        reqVipListRequest();
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
        DaggerOpenVipComponent.builder().appComponent(getAppComponent())
                .openVipModule(new OpenVipModule(OpenVipActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
