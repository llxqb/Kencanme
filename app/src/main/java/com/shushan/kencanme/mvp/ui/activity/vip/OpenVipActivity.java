package com.shushan.kencanme.mvp.ui.activity.vip;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerOpenVipComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.OpenVipModule;
import com.shushan.kencanme.entity.VipPrivilege;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.OpenVipRequest;
import com.shushan.kencanme.entity.response.OpenVipResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.mvp.ui.activity.register.AgreementActivity;
import com.shushan.kencanme.mvp.ui.adapter.OpenVipAdapter;
import com.shushan.kencanme.mvp.ui.adapter.VipPrivilegeAdapter;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 购买/打开 会员
 */
public class OpenVipActivity extends BaseActivity implements OpenVipControl.OpenVipView {

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
    List<OpenVipResponse.VipinfoBean> vipinfoBeanList = new ArrayList<>();
    OpenVipAdapter openVipAdapter;
    private int[] mVipPrivilegeImg = {R.mipmap.privilege_vip_logo, R.mipmap.rectangle, R.mipmap.private_letter, R.mipmap.vip_photo_open, R.mipmap.vip_video_watch,
            R.mipmap.unlimited_love, R.mipmap.search, R.mipmap.active_secret_chat, R.mipmap.make_friend};
    private String[] mVipPrivilegeName = {"VIP logo", "Hi-beans specials", "Free private letter", "VIP photo open", "VIP video watch",
            "Unlimited love", "Check out what I like", "Active secret chat", "Making friends in the same city"};

    //vip 特权
    private List<VipPrivilege> vipPrivilegeList = new ArrayList<>();
    @Inject
    OpenVipControl.PresenterOpenVip mPresenter;

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
        mTitleName.setText(getResources().getString(R.string.OpenVipActivity_title));
        mVipTypeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        openVipAdapter = new OpenVipAdapter(this, vipinfoBeanList);
        mVipTypeRecyclerView.setAdapter(openVipAdapter);
        mVipPrivilegesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        VipPrivilegeAdapter vipPrivilegeAdapter = new VipPrivilegeAdapter(this,vipPrivilegeList,mImageLoaderHelper);
        mVipPrivilegesRecyclerView.setAdapter(vipPrivilegeAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < mVipPrivilegeImg.length; i++) {
            VipPrivilege vipPrivilege = new VipPrivilege();
            vipPrivilege.pic = mVipPrivilegeImg[i];
            vipPrivilege.name = mVipPrivilegeName[i];
            vipPrivilegeList.add(vipPrivilege);
        }
        LoginUser loginUser = mBuProcessor.getLoginUser();
        OpenVipRequest openVipRequest = new OpenVipRequest();
        openVipRequest.token = mBuProcessor.getToken();
        mPresenter.openVipListRequest(openVipRequest);
        mImageLoaderHelper.displayImage(this, loginUser.trait, mAvator, R.mipmap.head_photo_loading);
        mUsername.setText(loginUser.nickname);
        if (loginUser.vip == 0) {
            mIsVipTv.setText("Not open Vip");
        } else {
            mIsVipTv.setText("Is Vip");
        }

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


    @Override
    public void OpenVipListSuccess(OpenVipResponse openVipResponse) {
        vipinfoBeanList = openVipResponse.getVipinfo();
        openVipAdapter.setNewData(vipinfoBeanList);
    }

    private void initializeInjector() {
        DaggerOpenVipComponent.builder().appComponent(getAppComponent())
                .openVipModule(new OpenVipModule(OpenVipActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
