package com.shushan.kencanme.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerHomeFragmentComponent;
import com.shushan.kencanme.di.modules.HomeFragmentModule;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.entity.Constant;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.RecommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.mvp.ui.adapter.HomeViewPagerAdapter;
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

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView {

    @BindView(R.id.home_viewpager)
    ViewPager homeViewpager;
    @BindView(R.id.home_message_iv)
    ImageView homeMessageIv;
    @BindView(R.id.home_like_iv)
    ImageView homeLikeIv;
    @BindView(R.id.use_exposure_iv)
    ImageView mUseExposureIv;
    @BindView(R.id.recommend_user_rl)
    RelativeLayout mRecommendUserRl;
    private List<ViewGroup> viewPagerResponseList = new ArrayList<>();

    @Inject
    HomeFragmentControl.homeFragmentPresenter mPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        RxView.clicks(homeMessageIv).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goChat());
        RxView.clicks(homeLikeIv).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goLike());
        RxView.clicks(mUseExposureIv).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> useSuperExposure());
        RxView.clicks(mRecommendUserRl).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goRecommendUser());

        homeViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //暂停播放
                JzvdStd.goOnPlayOnPause();
            }
        });
    }


    /**
     * 去聊天
     */
    private void goChat() {
        DialogFactory.showDialogContent(getActivity(), "Open Super free Chat?", Constant.DIALOG_TWO);
    }

    private void goLike() {
//        HomeFragmentRequest loginRequest = new HomeFragmentRequest();
//        loginRequest.deviceId = "868040033198091";
//        loginRequest.mobile = "18684923583";
//        loginRequest.password = "e10adc3949ba59abbe56e057f20f883e";
//        loginRequest.platform = "android";
//        mPresenter.onRequestHome(loginRequest);

//        DialogFactory.showDialogContent(getActivity(), "are you wang open it?");
        BuyDialog buyDialog = BuyDialog.newInstance();
//        buyDialog.setContent(content);
        DialogFactory.showDialogFragment(getActivity().getSupportFragmentManager(), buyDialog, CommonDialog.TAG);
    }

    /**
     * 使用超级曝光
     */
    private void useSuperExposure() {
        //检查是否有超级曝光次数
//        checkIsExposureTime();
        UseExposureDialog useExposureDialog = UseExposureDialog.newInstance();
        DialogFactory.showDialogFragment(getActivity().getSupportFragmentManager(), useExposureDialog, CommonDialog.TAG);
    }

    /**
     * 去推荐人详情
     */
    private void goRecommendUser() {
        startActivitys(RecommendUserInfoActivity.class);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        //home back
//        JzvdStd.goOnPlayOnResume();
//    }

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 2; i++) {
            ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.viewpager_item_layout, null);
            ImageView viewpagerItemIv = viewGroup.findViewById(R.id.viewpager_item_iv);
            JzvdStd jzvdStd = viewGroup.findViewById(R.id.jz_video);
            if (i == 0) {
                jzvdStd.setUp("http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
                        , "饺子闭眼睛");
                Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(jzvdStd.thumbImageView);
                jzvdStd.setVisibility(View.VISIBLE);
                viewpagerItemIv.setVisibility(View.GONE);
            } else {
                jzvdStd.setVisibility(View.GONE);
                viewpagerItemIv.setVisibility(View.VISIBLE);
                Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(viewpagerItemIv);
            }
            viewPagerResponseList.add(viewGroup);
        }
        HomeViewPagerAdapter adapter = new HomeViewPagerAdapter(getActivity(), viewPagerResponseList);
        homeViewpager.setAdapter(adapter);
    }


    @Override
    public void getInfoSuccess(HomeFragmentResponse response) {

    }

    @Override
    public void showErrMessage(Throwable e) {

    }

    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }
}
