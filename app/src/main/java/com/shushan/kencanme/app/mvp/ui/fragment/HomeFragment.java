package com.shushan.kencanme.app.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.jakewharton.rxbinding2.view.RxView;
import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerHomeFragmentComponent;
import com.shushan.kencanme.app.di.modules.HomeFragmentModule;
import com.shushan.kencanme.app.di.modules.MainModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.DialogBuyBean;
import com.shushan.kencanme.app.entity.base.BaseFragment;
import com.shushan.kencanme.app.entity.request.BuyExposureTimeRequest;
import com.shushan.kencanme.app.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.LikeResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.main.HomeFragmentControl;
import com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.app.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.HomeAdapter;
import com.shushan.kencanme.app.mvp.utils.AppUtils;
import com.shushan.kencanme.app.mvp.utils.ClickUtil;
import com.shushan.kencanme.app.mvp.utils.LogUtils;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.views.CommonDialog;
import com.shushan.kencanme.app.mvp.views.MyTimer;
import com.shushan.kencanme.app.mvp.views.dialog.BuyDialog;
import com.shushan.kencanme.app.mvp.views.dialog.MatchSuccessDialog;
import com.shushan.kencanme.app.mvp.views.dialog.UseExposureDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JzvdStd;
import io.rong.imkit.RongIM;

/**
 * HomeFragment
 * 首页
 */

public class HomeFragment extends BaseFragment implements HomeFragmentControl.HomeView, CommonDialog.CommonDialogListener, BuyDialog.BuyDialogListener, UseExposureDialog.UseExposureDialogListener,
        MyTimer.MyTimeListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, MatchSuccessDialog.MatchSuccessListener {

    @BindView(R.id.home_recycler_view)
    RecyclerView homeRecyclerView;
    @BindView(R.id.use_exposure_iv)
    ImageView mUseExposureIv;
    @BindView(R.id.exposure_time_tv)
    TextView mExposureTimeTv;
    @BindView(R.id.use_exposuring_hint)
    TextView mUseExposuringHint;
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLy;

    private List<HomeFragmentResponse.ListBean> viewPagerResponseList = new ArrayList<>();
    private HomeAdapter mHomeAdapter;
    private LoginUser mLoginUser;
    private HomeFragmentResponse.ListBean listBean;
    //喜欢动画
    private Dialog likeDialog;
    private boolean likeRemainTime;
    private int clickPos;
    /**
     * 曝光剩余时间
     */
    private int mRemainTime = 0;
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
            requestHomeUserInfo();
        } else if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_HOME_LIST_INFO)) {
            mSwipeLy.setRefreshing(true);
            requestHomeData();
        } else if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_HOME_LIKE_INFO)) {
            listBean.setRelation(1);
            mHomeAdapter.notifyItemChanged(clickPos);
            requestHomeUserInfo();
        }else if(intent.getAction() != null && intent.getAction().equals(ActivityConstant.PAY_SUCCESS_UPDATE_INFO)){
            //TODO 充值后更新
            mLoginUser = mBuProcessor.getLoginUser();
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_HOME_INFO);
        mFilter.addAction(ActivityConstant.UPDATE_HOME_LIST_INFO);
        mFilter.addAction(ActivityConstant.UPDATE_HOME_LIKE_INFO);
        mFilter.addAction(ActivityConstant.PAY_SUCCESS_UPDATE_INFO);
    }


    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        mSwipeLy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeLy.setOnRefreshListener(this);
        RxView.clicks(mUseExposureIv).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> useSuperExposure());
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mHomeAdapter = new HomeAdapter(getActivity(), viewPagerResponseList, mImageLoaderHelper);
        mHomeAdapter.setOnLoadMoreListener(this, homeRecyclerView);
        homeRecyclerView.setAdapter(mHomeAdapter);
        //设置一次滑动一个item（LinearSnapHelper ）  不能快速滑动（PagerSnapHelper）
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(homeRecyclerView);
        //如果当前页面不可见，暂停播放
        homeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();//获取LayoutManager
                if (manager instanceof LinearLayoutManager) {
                    //第一个可见的位置
                    int firstPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();
                    if (firstPosition > 0) {
                        JzvdStd.goOnPlayOnPause();
                    }
                }
            }
        });
        homeRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                listBean = (HomeFragmentResponse.ListBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.home_item_rl:
                        if (ClickUtil.isDoubleClick(getActivity(), view)) {//双击
                            clickPos = position;
                            if (listBean.getIs_like() != 1) {
                                goLike(listBean.getUid());
                            } else {
                                showToast(getResources().getString(R.string.already_like));
                            }
                        }
                        break;
                    case R.id.recommend_user_rl:
                        assert listBean != null;
                        goRecommendUser(listBean.getUid());
                        break;
                    case R.id.home_like_iv:
                        assert listBean != null;
                        if (listBean.getIs_like() != 1) {
                            goLike(listBean.getUid());
                        } else {
                            showToast(getResources().getString(R.string.already_like));
                        }
                        break;
                    case R.id.home_message_iv:
                        goChat();
                        break;
                }
            }
        });
    }


    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        requestHomeUserInfo();
        requestHomeData();
    }

    /**
     * 重新查询，更新用户信息(首页)
     */
    private void requestHomeUserInfo() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);
    }

    /**
     * 请求list数据
     */
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

    /**
     * 去喜欢
     */
    public void goLike(int uId) {
        likeDialog = DialogFactory.showLikeDialog(getActivity());
        likeDialog.show();
        likeRemainTime = true;
        setmRemainTime(1000);
        if (AppUtils.isLimitLike(mLoginUser.userType, mLoginUser.today_like)) {
            LikeRequest likeRequest = new LikeRequest();
            likeRequest.token = mBuProcessor.getToken();
            likeRequest.likeid = uId;
            mPresenter.onRequestLike(likeRequest);
        } else {
            DialogFactory.showOpenVipDialogFragment(getActivity(), this, getResources().getString(R.string.dialog_open_vip_like));
        }
    }

    /**
     * 去聊天
     */
    public void goChat() {
        if (listBean.getRelation() == 2) {//好友
            //启动单聊页面
            RongIM.getInstance().startPrivateChat(Objects.requireNonNull(getActivity()), listBean.getRongyun_userid(), listBean.getNickname());
        } else {//非好友
            if (AppUtils.isLimitMsg(mLoginUser.userType, mLoginUser.today_chat)) {
                //先走统计接口  记录免费聊天次数
                RequestFreeChat requestFreeChat = new RequestFreeChat();
                requestFreeChat.token = mBuProcessor.getToken();
                requestFreeChat.secret_id = String.valueOf(listBean.getUid());
                mPresenter.onRequestChatNum(requestFreeChat);
            } else {
                DialogFactory.showOpenVipDialogFragment(getActivity(), this, getResources().getString(R.string.dialog_open_vip_chat));
            }
        }
    }

    /**
     * 去推荐人详情
     */
    public void goRecommendUser(int uid) {
        RecommendUserInfoActivity.start(getActivity(), uid);
    }

    /**
     * 1、使用超级曝光
     * 先获取曝光列表
     */
    private void useSuperExposure() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBuyExposureTimeList(tokenRequest);
    }

    /**
     * 开始聊天
     */
    @Override
    public void startChatBtnListener() {
        //启动单聊页面
        RongIM.getInstance().startPrivateChat(Objects.requireNonNull(getActivity()), listBean.getRongyun_userid(), listBean.getNickname());
    }

    /**
     * 刷新
     */
    @Override
    public void onRefresh() {
        requestHomeData();
    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMoreRequested() {
        requestHomeData();
    }

    /**
     * 去喜欢/去聊天  没有免费使用次数了提示打开vip
     */
    @Override
    public void commonDialogBtnOkListener() {
        startActivitys(OpenVipActivity.class);
    }

    @Override
    public void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
//        LogUtils.e("userBean:" + new Gson().toJson(userBean));
        mLoginUser.userType = AppUtils.userType(userBean.getSvip(), userBean.getVip(), userBean.getSex());
        //把另外几项LoginUser加入进来
        mLoginUser.exposure = userBean.getExposure();
        mLoginUser.beans = userBean.getBeans();
        mLoginUser.exposure_type = userBean.getExposure_type();
        mLoginUser.exposure_time = userBean.getExposure_time();
        mLoginUser.today_like = userBean.getToday_like();
        mLoginUser.today_chat = userBean.getToday_chat();
        mLoginUser.today_see_contact = userBean.getToday_see_contact();
        mBuProcessor.setLoginUser(mLoginUser);
        setExposureData(userBean.getNow_time());
    }

    /**
     * 列表数据
     */
    @Override
    public void getInfoSuccess(HomeFragmentResponse response) {
        LogUtils.d("response:" + new Gson().toJson(response));
        List<HomeFragmentResponse.ListBean> dataList = response.getList();
        if (mSwipeLy.isRefreshing()) {
            mSwipeLy.setRefreshing(false);
            mHomeAdapter.setNewData(dataList);
        } else {
            mHomeAdapter.addData(dataList);
            mHomeAdapter.loadMoreComplete();
        }
    }


    @Override
    public void getLikeSuccess(LikeResponse likeResponse) {
        if (likeResponse.getState() == 1) {
            //相互喜欢
            showMatchSuccesDialog();
        }
        listBean.setRelation(1);
        mHomeAdapter.notifyItemChanged(clickPos);
        requestHomeUserInfo();
    }


    /**
     * 显示匹配成功弹框
     */
    private void showMatchSuccesDialog() {
        MatchSuccessDialog matchSuccessDialog = MatchSuccessDialog.newInstance();
        matchSuccessDialog.setListener(this);
        matchSuccessDialog.setContent(mLoginUser.nickname, mLoginUser.trait, listBean.getNickname(), listBean.getTrait());
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), matchSuccessDialog, MatchSuccessDialog.TAG);
    }

    /**
     * 设置数据
     * 曝光和曝光时间
     */
    private void setExposureData(int currentTime) {
        if (mLoginUser.exposure > 0) {
            mExposureTimeTv.setVisibility(View.VISIBLE);
            mExposureTimeTv.setText(String.valueOf(mLoginUser.exposure));
        } else {
            mExposureTimeTv.setVisibility(View.INVISIBLE);
        }
        if (mLoginUser.exposure_time > 0) {
            mUseExposuringHint.setVisibility(View.VISIBLE);
            int remainTime = AppUtils.exposureRemainTime(currentTime, mLoginUser.exposure_time);
            mRemainTime = remainTime / 60 + 1;
            String exposureValue = getResources().getString(R.string.HomeFragment_Super_exposure_hint) + remainTime / 60 + " min";
            mUseExposuringHint.setText(exposureValue);
            setmRemainTime(60000);
        } else {
            mUseExposuringHint.setVisibility(View.INVISIBLE);
        }
    }

    MyTimer myTimer;

    /**
     * 设置一分钟倒计时
     */
    private void setmRemainTime(int totalTime) {
        myTimer = MyTimer.getInstance(totalTime, 1000, getActivity());
        myTimer.setListener(this);
        myTimer.cancel();
        myTimer.start();
    }

    /**
     * 一分钟倒计时结束
     */
    @Override
    public void onFinish() {
        if (likeRemainTime) {//喜欢动画倒计时
            likeRemainTime = false;
            if (myTimer != null) {//显示CommonDialog时取消myTimer计时
                myTimer.cancel();
                myTimer = null;
            }
            if (likeDialog.isShowing()) {
                likeDialog.dismiss();
            }
        } else {//超级曝光倒计时
            mRemainTime--;
            if (myTimer != null) {//显示CommonDialog时取消myTimer计时
                myTimer.cancel();
                myTimer = null;
            }
            if (mRemainTime > 0) {
                setmRemainTime(60000);
                String exposureValue = getResources().getString(R.string.HomeFragment_Super_exposure_hint) + mRemainTime + " min";
                mUseExposuringHint.setText(exposureValue);
            } else {
                mUseExposuringHint.setVisibility(View.INVISIBLE);
            }
//        requestHomeUserInfo();   不用走接口了
        }
    }

    @Override
    public void getBuyExposureTimeList(DialogBuyBean dialogBuyBean) {
        //2、检查是否有超级曝光次数
        if (mLoginUser.exposure > 0) {
            UseExposureDialog useExposureDialog = UseExposureDialog.newInstance();
            useExposureDialog.setListener(this);
            useExposureDialog.setContentData(mLoginUser.beans, mLoginUser.exposure);
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), useExposureDialog, UseExposureDialog.TAG);
        } else {
            BuyDialog buyDialog = BuyDialog.newInstance();
            buyDialog.setListener(this);
            buyDialog.setBugData(dialogBuyBean, mLoginUser.beans);
            buyDialog.setContent(getResources().getString(R.string.HomeFragment_Super_exposure_content_hint));
            DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), buyDialog, BuyDialog.TAG);
        }
    }

    //3、购买超级曝光
    @Override
    public void buyDialogBtnOkListener(int beansMoney) {
        BuyExposureTimeRequest buyExposureTimeRequest = new BuyExposureTimeRequest();
        buyExposureTimeRequest.token = mBuProcessor.getToken();
        buyExposureTimeRequest.beans = String.valueOf(beansMoney);
        mPresenter.onRequestBuyExposureTime(buyExposureTimeRequest);
    }

    /**
     * 4、购买超级曝光 成功
     */
    @Override
    public void getBuyExposureTime(String msg) {
        showToast(msg);
        requestHomeUserInfo();
    }

    /**
     * 5、使用超级曝光
     */
    @Override
    public void useExposureBtnOkListener() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestExposure(tokenRequest);
    }

    /**
     * 6、曝光成功
     */
    @Override
    public void exposureSuccess(String msg) {
        showToast(msg);
        //进行更新
        requestHomeUserInfo();
    }

    /**
     * 密聊
     */
    @Override
    public void chatNumSuccess() {
        //进行更新
        requestHomeUserInfo();
        //启动单聊页面
        RongIM.getInstance().startPrivateChat(Objects.requireNonNull(getActivity()), listBean.getRongyun_userid(), listBean.getNickname());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myTimer != null) {//显示CommonDialog时取消myTimer计时
            myTimer.cancel();
            myTimer = null;
        }
    }

    private void initializeInjector() {
        DaggerHomeFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .homeFragmentModule(new HomeFragmentModule(this))
                .build().inject(this);
    }


}
