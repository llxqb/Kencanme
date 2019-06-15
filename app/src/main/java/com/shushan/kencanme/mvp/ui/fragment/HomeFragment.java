package com.shushan.kencanme.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerHomeFragmentComponent;
import com.shushan.kencanme.di.modules.HomeFragmentModule;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.ui.adapter.HomeViewPagerAdapter;
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

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView, CommonDialog.CommonDialogListener, HomeViewPagerAdapter.HomeViewPagerListener, BuyDialog.CommonDialogListener {

    @BindView(R.id.home_viewpager)
    ViewPager homeViewpager;
    @BindView(R.id.use_exposure_iv)
    ImageView mUseExposureIv;
    //当前发起更新的位置
    private int currentUpdatePos = 0;

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
            commonDialog.setContent("Today's favorite number has been used up. Open membe\n" +
                    "-rs can enjoy unlimited ~");
            commonDialog.setStyle(Constant.DIALOG_TWO);
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
        }
    }

    /**
     * 去聊天
     */
    @Override
    public void goChat(int uId) {
//        DialogFactory.showCommonDialog(getActivity(), "Open Super free Chat?", Constant.DIALOG_THREE);
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
        startActivitys(RecommendUserInfoActivity.class);
    }

//    private void goLike(int uId, int isLike) {
//////        DialogFactory.showDialogContent(getActivity(), "are you wang open it?");
////        BuyDialog buyDialog = BuyDialog.newInstance();
//////        buyDialog.setContent(content);
////        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), buyDialog, CommonDialog.TAG);
//    }

    /**
     * 使用超级曝光
     */
    private void useSuperExposure() {
//        //检查是否有超级曝光次数
        if (mLoginUser.exposure > 0) {
            UseExposureDialog useExposureDialog = UseExposureDialog.newInstance();
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), useExposureDialog, CommonDialog.TAG);
        } else {
            BuyDialog buyDialog = BuyDialog.newInstance();
            buyDialog.setListener(this);
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), buyDialog, BuyDialog.TAG);
        }
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
        mLoginUser.today_like = mLoginUser.today_like - 1;
    }


    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }


    //购买超级曝光
    @Override
    public void buyDialogBtnOkListener() {

    }
}
