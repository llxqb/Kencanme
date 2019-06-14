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
import android.widget.TextView;

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
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.mvp.ui.adapter.HomeViewPagerAdapter;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.utils.TranTools;
import com.shushan.kencanme.mvp.views.CircleImageView;
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

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView, CommonDialog.CommonDialogListener {

    @BindView(R.id.home_viewpager)
    ViewPager homeViewpager;
    @BindView(R.id.home_message_iv)
    ImageView homeMessageIv;
    @BindView(R.id.home_like_iv)
    ImageView homeLikeIv;
    @BindView(R.id.use_exposure_iv)
    ImageView mUseExposureIv;
    //----viewPager 布局
    ImageView mViewpagerItemIv;
    CircleImageView mRecommendUserHeadIv;
    TextView mRecommendUserName;
    TextView mRecommendUserSexYear;
    TextView mRecommendUserLocation;
    TextView mActiveTime;
    RelativeLayout mRecommendUserRl;
    JzvdStd mJzVideo;
    //----viewPager 布局

    private List<ViewGroup> viewPagerResponseList = new ArrayList<>();
    private List<HomeFragmentResponse.ListBean> dataList = new ArrayList<>();
    private HomeViewPagerAdapter homeViewPagerAdapter;
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
        homeViewPagerAdapter = new HomeViewPagerAdapter(viewPagerResponseList);
        homeViewpager.setAdapter(homeViewPagerAdapter);

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


    @Override
    public void initData() {
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
        dataList = response.getList();
        for (HomeFragmentResponse.ListBean listBean : dataList) {
            ViewGroup viewGroup = (ViewGroup) View.inflate(getActivity(), R.layout.viewpager_item_layout, null);
            mViewpagerItemIv = viewGroup.findViewById(R.id.viewpager_item_iv);
            mRecommendUserHeadIv = viewGroup.findViewById(R.id.recommend_user_head_iv);
            mRecommendUserName = viewGroup.findViewById(R.id.recommend_user_name);
            mRecommendUserSexYear = viewGroup.findViewById(R.id.recommend_user_sex_year);
            mRecommendUserLocation = viewGroup.findViewById(R.id.recommend_user_location);
            mActiveTime = viewGroup.findViewById(R.id.active_time);
            mRecommendUserRl = viewGroup.findViewById(R.id.recommend_user_rl);
            mJzVideo = viewGroup.findViewById(R.id.jz_video);
            RxView.clicks(mRecommendUserRl).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> goRecommendUser());

            if (TranTools.isVideo(listBean.getCover())) {
                //视频
                mJzVideo.setVisibility(View.VISIBLE);
                mViewpagerItemIv.setVisibility(View.GONE);
                mJzVideo.setUp(listBean.getCover(), "");
                PicUtils.loadVideoScreenshot(getActivity(), listBean.getCover(), mJzVideo.thumbImageView, 0);
            } else {
                mJzVideo.setVisibility(View.GONE);
                mViewpagerItemIv.setVisibility(View.VISIBLE);
                mImageLoaderHelper.displayMatchImage(getActivity(), listBean.getCover(), mViewpagerItemIv,Constant.LOADING_BIG);
            }
            setUserData(listBean);
            viewPagerResponseList.add(viewGroup);
        }
        homeViewPagerAdapter.notifyDataSetChanged();

    }


    /**
     * 设置推荐人信息
     */
    private void setUserData(HomeFragmentResponse.ListBean listBean) {
        mRecommendUserName.setText(listBean.getNickname());
        mImageLoaderHelper.displayImage(getActivity(), listBean.getTrait(), mRecommendUserHeadIv,Constant.LOADING_SMALL);
        if (listBean.getSex() == 1) {
            //1男2女
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mRecommendUserSexYearValue = listBean.getAge() + " years";
        String mActiveTimeValue = "active "+listBean.getActive_time()+" minute ago";
        mRecommendUserSexYear.setText(mRecommendUserSexYearValue);
        mRecommendUserLocation.setText(listBean.getCity());
        mActiveTime.setText(mActiveTimeValue);
    }

    @Override
    public void commonDialogBtnOkListener() {

    }

    @Override
    public void getInfoFail(String errMsg) {
        showToast(errMsg);
    }


    /**
     * 去聊天
     */
    private void goChat() {
//        DialogFactory.showCommonDialog(getActivity(), "Open Super free Chat?", Constant.DIALOG_THREE);
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setListener(this);
        commonDialog.setContent("Open Super free Chat?");
        commonDialog.setStyle(Constant.DIALOG_THREE);
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    private void goLike() {
//        DialogFactory.showDialogContent(getActivity(), "are you wang open it?");
        BuyDialog buyDialog = BuyDialog.newInstance();
//        buyDialog.setContent(content);
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), buyDialog, CommonDialog.TAG);
    }

    /**
     * 使用超级曝光
     */
    private void useSuperExposure() {
        //检查是否有超级曝光次数
//        checkIsExposureTime();
        UseExposureDialog useExposureDialog = UseExposureDialog.newInstance();
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), useExposureDialog, CommonDialog.TAG);
    }

    /**
     * 去推荐人详情
     */
    private void goRecommendUser() {
        startActivitys(RecommendUserInfoActivity.class);
    }


    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }


}
