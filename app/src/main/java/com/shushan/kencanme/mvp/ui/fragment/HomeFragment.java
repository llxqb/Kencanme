package com.shushan.kencanme.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerHomeFragmentComponent;
import com.shushan.kencanme.di.modules.HomeFragmentModule;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.DialogBuyBean;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.entity.request.BuyExposureTimeRequest;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.ui.adapter.HomeViewPagerAdapter;
import com.shushan.kencanme.mvp.utils.DateUtil;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.dialog.BuyDialog;
import com.shushan.kencanme.mvp.views.dialog.UseExposureDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JzvdStd;

/**
 * HomeFragment
 * 首页
 */

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView, CommonDialog.CommonDialogListener, HomeViewPagerAdapter.HomeViewPagerListener,
        BuyDialog.BuyDialogListener, UseExposureDialog.UseExposureDialogListener {

    @BindView(R.id.home_viewpager)
    ViewPager homeViewpager;
    @BindView(R.id.use_exposure_iv)
    ImageView mUseExposureIv;
    @BindView(R.id.exposure_time_tv)
    TextView mExposureTimeTv;
    @BindView(R.id.use_exposuring_hint)
    TextView mUseExposuringHint;
    //当前发起更新的位置
    private int currentUpdatePos = 0;
    private int currentPagePos;

    private List<HomeFragmentResponse.ListBean> viewPagerResponseList = new ArrayList<>();
    private HomeViewPagerAdapter homeViewPagerAdapter;
    private LoginUser mLoginUser;
    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
        ButterKnife.bind(this, view);
        initializeInjector();
        initView();
        initData();
        return view;
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_HOME_INFO)) {
            requestHomeData();
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_HOME_INFO);
    }


    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        RxView.clicks(mUseExposureIv).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> useSuperExposure());
        homeViewPagerAdapter = new HomeViewPagerAdapter(getActivity(), viewPagerResponseList, mImageLoaderHelper, this);
        homeViewpager.setAdapter(homeViewPagerAdapter);
        homeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPagePos = position;
                if ((position + 1) % 4 == 0 && position > currentUpdatePos) {//回滑的时候不刷新
                    currentUpdatePos = position;
                    requestHomeData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //暂停播放
                JzvdStd.goOnPlayOnPause();
            }
        });
    }


    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        setData();
        requestHomeData();
    }

    private void requestHomeData() {
        HomeFragmentRequest homeFragmentRequest = new HomeFragmentRequest();
        homeFragmentRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestInfo(homeFragmentRequest);
    }

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }


    @SuppressLint("CheckResult")
    @Override
    public void getInfoSuccess(HomeFragmentResponse response) {
        LogUtils.d("response:" + new Gson().toJson(response));
        List<HomeFragmentResponse.ListBean> dataList = response.getList();
        viewPagerResponseList.addAll(dataList);
        homeViewPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void goLike(int uId) {
        if (mLoginUser != null && mLoginUser.today_like > 0) {
            LikeRequest likeRequest = new LikeRequest();
            likeRequest.token = mBuProcessor.getToken();
            likeRequest.likeid = uId;
            mPresenter.onRequestLike(likeRequest);
        } else {
            CommonDialog commonDialog = CommonDialog.newInstance();
            commonDialog.setListener(this);
            commonDialog.setContent("Today's favorite number has been used up. Open members can enjoy unlimited ~");
            commonDialog.setStyle(Constant.DIALOG_TWO);
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
        }
    }

    /**
     * 去聊天
     */
    @Override
    public void goChat(int uId) {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setListener(this);
        commonDialog.setContent("Open Super free Chat?");
        commonDialog.setStyle(Constant.DIALOG_THREE);
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    /**
     * 去推荐人详情
     */
    @Override
    public void goRecommendUser() {
        RecommendUserInfoActivity.start(getActivity(), viewPagerResponseList.get(currentPagePos).getUid());
    }

    /**
     * 使用超级曝光
     */
    private void useSuperExposure() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBuyExposureTimeList(tokenRequest);
    }

    @Override
    public void commonDialogBtnOkListener() {
        startActivitys(OpenVipActivity.class);
    }

    @Override
    public void getInfoFail(String errMsg) {
        showToast(errMsg);
    }

    @Override
    public void getLikeSuccess(String msg) {
        showToast(msg);
        homeViewPagerAdapter.setLikeImg();
        requestHomeUserInfo();
    }

    @Override
    public void getBuyExposureTimeList(DialogBuyBean dialogBuyBean) {
        //检查是否有超级曝光次数
        if (mLoginUser.exposure > 0) {
            UseExposureDialog useExposureDialog = UseExposureDialog.newInstance();
            useExposureDialog.setListener(this);
            useExposureDialog.setContentData(mLoginUser.beans, mLoginUser.exposure);
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), useExposureDialog, UseExposureDialog.TAG);
        } else {
            BuyDialog buyDialog = BuyDialog.newInstance();
            buyDialog.setListener(this);
            buyDialog.setBugData(dialogBuyBean, mLoginUser.beans);
            buyDialog.setContent("Matchmaker! 10 times more people will see you in 30 minutes!");
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), buyDialog, BuyDialog.TAG);
        }
    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {
    }

    /**
     * 重新查询，更新用户信息(首页)
     */
    private void requestHomeUserInfo() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);
    }

    @Override
    public void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
        LogUtils.e("userBean:" + new Gson().toJson(userBean));
        //把另外几项LoginUser加入进来
        mLoginUser.exposure = userBean.getExposure();
        mLoginUser.exposure_type = userBean.getExposure_type();
        mLoginUser.exposure_time = userBean.getExposure_time();
        mLoginUser.today_like = userBean.getToday_like();
        mLoginUser.today_chat = userBean.getToday_chat();
        mLoginUser.today_see_contact = userBean.getToday_see_contact();
        mBuProcessor.setLoginUser(mLoginUser);
        setData();
    }

    /**
     * 设置数据
     * 曝光和曝光时间
     */
    private void setData() {
        if (mLoginUser.exposure > 0) {
            mExposureTimeTv.setVisibility(View.VISIBLE);
            mExposureTimeTv.setText(String.valueOf(mLoginUser.exposure));
        } else {
            mExposureTimeTv.setVisibility(View.INVISIBLE);
        }

        if (mLoginUser.exposure_time > 0) {
            mUseExposuringHint.setVisibility(View.VISIBLE);
            String exposureValue = "Super exposure! Countdown: " + DateUtil.getDateToString(mLoginUser.exposure_time,"mm") + " min";
            mUseExposuringHint.setText(exposureValue);
        } else {
            mUseExposuringHint.setVisibility(View.INVISIBLE);
        }
    }

    //购买超级曝光
    @Override
    public void buyDialogBtnOkListener(int beansMoney) {
        BuyExposureTimeRequest buyExposureTimeRequest = new BuyExposureTimeRequest();
        buyExposureTimeRequest.token = mBuProcessor.getToken();
        buyExposureTimeRequest.beans = String.valueOf(beansMoney);
        mPresenter.onRequestBuyExposureTime(buyExposureTimeRequest);
    }

    /**
     * 购买超级曝光 成功
     */
    @Override
    public void getBuyExposureTime(String msg) {
        showToast(msg);
        requestHomeUserInfo();
    }

    /**
     * 使用超级曝光
     */
    @Override
    public void useExposureBtnOkListener() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestExposure(tokenRequest);
    }

    @Override
    public void exposureSuccess(String msg) {
        showToast(msg);
        //进行更新
        requestHomeUserInfo();
    }

    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }

}
