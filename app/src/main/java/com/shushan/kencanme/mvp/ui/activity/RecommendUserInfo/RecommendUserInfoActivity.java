package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerRecommendUserInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.RecommendUserInfoModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.BlackUserRequest;
import com.shushan.kencanme.entity.request.DeleteUserRequest;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.LookAlbumByBeansRequest;
import com.shushan.kencanme.entity.request.LookContactTypeRequest;
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.request.RequestFreeChat;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.ContactWay;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.response.LikeResponse;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.pay.RechargeActivity;
import com.shushan.kencanme.mvp.ui.activity.photo.LookPhotoActivity;
import com.shushan.kencanme.mvp.ui.activity.register.EarnBeansActivity;
import com.shushan.kencanme.mvp.ui.activity.reportUser.ReportUserActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.ui.adapter.AlbumAdapter;
import com.shushan.kencanme.mvp.ui.adapter.MimeContactWayAdapter;
import com.shushan.kencanme.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.mvp.utils.AppUtils;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.MyTimer;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;
import com.shushan.kencanme.mvp.views.dialog.MatchSuccessDialog;
import com.shushan.kencanme.mvp.views.dialog.MessageUseBeansDialog;
import com.shushan.kencanme.mvp.views.dialog.RechargeBeansDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * 推荐用户资料详情
 */
public class RecommendUserInfoActivity extends BaseActivity implements RecommendUserInfoControl.RecommendUserInfoView, CommonChoiceDialog.commonChoiceDialogListener,
        CommonDialog.CommonDialogListener, RechargeBeansDialog.RechargeDialogListener, MessageUseBeansDialog.MessageUseBeansDialogListener, MyTimer.MyTimeListener, MatchSuccessDialog.MatchSuccessListener {
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.head_icon)
    CircleImageView mHeadIcon;
    @BindView(R.id.cover_iv)
    ImageView mCoverIv;
    @BindView(R.id.recommend_username)
    TextView mRecommendUsername;
    @BindView(R.id.recommend_user_sex_year)
    TextView mRecommendUserSexYear;
    @BindView(R.id.recommend_active_time)
    TextView mRecommendActiveTime;
    @BindView(R.id.recommend_desc)
    TextView mRecommendDesc;
    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    @BindView(R.id.photo_album_tv)
    TextView mPhotoAlbumTv;
    @BindView(R.id.recommend_user_location)
    TextView mRecommendUserLocation;
    @BindView(R.id.recommend_user_height)
    TextView mRecommendUserHeight;
    @BindView(R.id.recommend_user_weight)
    TextView mRecommendUserWeight;
    @BindView(R.id.recommend_user_chest)
    TextView mRecommendUserChest;
    @BindView(R.id.recommend_user_birthday)
    TextView mRecommendUserBirthday;
    @BindView(R.id.recommend_user_professional)
    TextView mRecommendUserProfessional;
    @BindView(R.id.contact_way_recycler_view)
    RecyclerView mContactWayRecyclerView;
    @BindView(R.id.look_over_tv)
    TextView mLookOverTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.recommend_like_iv)
    ImageView mRecommendLikeIv;
    @BindView(R.id.recommend_like_tv)
    TextView mRecommendLikeTv;
    @BindView(R.id.recommend_chat_iv)
    ImageView mRecommendChatIv;
    @BindView(R.id.contact_rl)
    RelativeLayout mContactRl;
    @BindView(R.id.label_tv)
    TextView mLabelTv;
    private List<MyAlbumResponse.DataBean> albumInfoLists = new ArrayList<>();
    private List<ContactWay> contactWayList = new ArrayList<>();
    private AlbumAdapter albumAdapter;
    private MimeContactWayAdapter contactWayAdapter;
    private RecommendUserLabelAdapter recommendUserLabelAdapter;
    private int mUid;
    /**
     * 1 加入黑名单  2 删除好友  3 喜欢好友
     * 4 免费beans使用提示  5 打开VIP
     */
    private int commonDialogOperationType;
    /**
     * 1 使用嗨豆查看相册
     * 2 使用嗨豆查看联系方式
     */
    private MyAlbumResponse.DataBean mAlbumBean;
    private int useBeansType;
    private List<String> labelList = new ArrayList<>();
    private RecommendUserInfoResponse recommendUserInfoResponse;
    private LoginUser mLoginUser;
    private Dialog likeDialog;
    @Inject
    RecommendUserInfoControl.PresenterRecommendUserInfo mPresenter;


    public static void start(Context context, int uid) {
        Intent intent = new Intent(context, RecommendUserInfoActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_RECHARGE_INFO)) {
            //TODO 更新
            mLoginUser = mBuProcessor.getLoginUser();
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_RECHARGE_INFO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_user_info);
        ButterKnife.bind(this);
        initializeInjector();
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }


    @Override
    public void initView() {
        initAdapter();
        mAlbumRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                mAlbumBean = albumAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.photo_item_rl:
                        assert mAlbumBean != null;
                        if (mAlbumBean.getAlbum_type() == 1 || mAlbumBean.getState() == 1) {
                            LookPhotoActivity.start(RecommendUserInfoActivity.this, mAlbumBean.getAlbum_url());
                        } else if (mAlbumBean.getAlbum_type() == 2) {
                            if (mLoginUser.userType == 2 || mLoginUser.userType == 3 || mLoginUser.userType == 5) {
                                LookPhotoActivity.start(RecommendUserInfoActivity.this, mAlbumBean.getAlbum_url());
                            } else {
                                showOpenVipDialog(getResources().getString(R.string.dialog_open_vip_album));
                            }
                        } else if (mAlbumBean.getAlbum_type() == 3) {
                            //使用hi-bean查看
                            if (mAlbumBean.getCost() > mLoginUser.beans) {
                                //去充值
                                showRechargeBeansDialog();
                            } else {
                                //去使用
                                useBeansType = 1;
                                showUseBeansDialog(mAlbumBean.getCost());
                            }
                        }
                        break;
                }
            }
        });
    }

    private void initAdapter() {
        //图片adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mAlbumRecyclerView.setLayoutManager(gridLayoutManager);
        albumAdapter = new AlbumAdapter(this, albumInfoLists, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(albumAdapter);
        //联系方式adapter
        mContactWayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactWayAdapter = new MimeContactWayAdapter(contactWayList);
        mContactWayRecyclerView.setAdapter(contactWayAdapter);
        //label adapter
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 3);
        mLabelRecyclerView.setLayoutManager(gridLayoutManager2);
        recommendUserLabelAdapter = new RecommendUserLabelAdapter(this, labelList);
        mLabelRecyclerView.setAdapter(recommendUserLabelAdapter);
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        if (getIntent() != null) {
            mUid = getIntent().getIntExtra("uid", 0);
            //查询用户详细信息
            RecommendUserInfoRequest recommendUserInfoRequest = new RecommendUserInfoRequest();
            recommendUserInfoRequest.token = mBuProcessor.getToken();
            recommendUserInfoRequest.uid = String.valueOf(mUid);
            mPresenter.onRequestRecommendUserInfo(recommendUserInfoRequest);
        }
    }

    @OnClick({R.id.back_iv, R.id.more_iv, R.id.look_over_tv, R.id.recommend_like_iv, R.id.recommend_chat_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_iv:
                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
                commonChoiceDialog.setListener(this);
                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);
                break;
            case R.id.look_over_tv:
                int useBeansNum = AppUtils.lookContactType(mLoginUser.userType, mLoginUser.today_see_contact);
                if (useBeansNum == 0) {
                    //免费花费嗨豆弹框
                    showFreeBeansDialog();
                } else {
                    if (useBeansNum > mLoginUser.beans) {
                        showRechargeBeansDialog();
                    } else {
                        useBeansType = 2;
                        showUseBeansDialog(useBeansNum);
                    }
                }
                break;
            case R.id.recommend_like_iv:
                //0没有关系1喜欢2互相喜欢（好友）3黑名单  喜欢了不可以取消喜欢
                if (recommendUserInfoResponse.getRelation() == 0) {
                    likeIv();
                }
                break;
            case R.id.recommend_chat_iv:
                goChat();
                break;
        }
    }

    /**
     * 设置页面数据
     */
    private void setUserData(RecommendUserInfoResponse recommendUserInfoResponse) {
        mImageLoaderHelper.displayMatchImage(this, recommendUserInfoResponse.getCover(), mCoverIv, Constant.LOADING_MIDDLE);
        mImageLoaderHelper.displayImage(this, recommendUserInfoResponse.getTrait(), mHeadIcon, Constant.LOADING_AVATOR);
        mRecommendUsername.setText(recommendUserInfoResponse.getNickname());
        if (recommendUserInfoResponse.getSex() == 1) {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mSexYearTvValue = recommendUserInfoResponse.getAge() + " years";
        if (recommendUserInfoResponse.getActive_time() > 0) {
            mRecommendActiveTime.setVisibility(View.VISIBLE);
            String activeTimeValue = "active " + recommendUserInfoResponse.getActive_time() + " min age";
            mRecommendActiveTime.setText(activeTimeValue);
        } else {
            mRecommendActiveTime.setVisibility(View.INVISIBLE);
        }
        mRecommendUserSexYear.setText(mSexYearTvValue);
        mRecommendDesc.setText(recommendUserInfoResponse.getDeclaration());
        //相册信息
        if (recommendUserInfoResponse.getAlbum().size() == 0)
            mPhotoAlbumTv.setVisibility(View.GONE);
        //个人信息
        mRecommendUserLocation.setText(recommendUserInfoResponse.getCity());
        String mUserHeightValue = recommendUserInfoResponse.getHeight() != 0 ? "Height: " + recommendUserInfoResponse.getHeight() + "cm" : "Height: ";
        String mUserWeightValue = !TextUtils.isEmpty(recommendUserInfoResponse.getWeight()) ? "Weight: " + recommendUserInfoResponse.getWeight() + "kg" : "Weight: ";
        String mUserChestValue = "Chest:" + recommendUserInfoResponse.getBust();
        String mUserBirthdayValue = "Birthday:" + recommendUserInfoResponse.getBirthday();
        String mUserProfessionalValue = "Professional:" + recommendUserInfoResponse.getOccupation();
        mRecommendUserHeight.setText(mUserHeightValue);
        mRecommendUserWeight.setText(mUserWeightValue);
        mRecommendUserChest.setText(mUserChestValue);
        mRecommendUserBirthday.setText(mUserBirthdayValue);
        mRecommendUserProfessional.setText(mUserProfessionalValue);
        //1 喜欢2互相喜欢（好友）3黑名单
        if (recommendUserInfoResponse.getRelation() == 1 || recommendUserInfoResponse.getRelation() == 2) {
            likedBg();
        } else {
            unlikedBg();
        }

    }

    private void likedBg() {
        mImageLoaderHelper.displayImage(this, R.mipmap.home_like, mRecommendLikeIv, R.mipmap.home_liked);
        mRecommendLikeTv.setTextColor(getResources().getColor(R.color.red_color_btn));
    }

    private void unlikedBg() {
        mImageLoaderHelper.displayImage(this, R.mipmap.home_liked, mRecommendLikeIv, R.mipmap.home_liked);
        mRecommendLikeTv.setTextColor(getResources().getColor(R.color.five_text_color));
    }

    /**
     * 显示免费查看联系方式
     */
    private void showFreeBeansDialog() {
        commonDialogOperationType = 4;
        DialogFactory.showCommonDialog(this, "Are you sure look contact", Constant.DIALOG_FOUR);
    }

    /**
     * 显示去充值嗨豆
     */
    private void showRechargeBeansDialog() {
        DialogFactory.showRechargeBeansDialog(this);
    }

    /**
     * 显示使用嗨豆
     */
    private void showUseBeansDialog(int beansNum) {
        DialogFactory.showUseBeansDialog(this, getResources().getString(R.string.dialog_use_beans_contact), beansNum);
    }

    /**
     * Go喜欢
     */
    private void likeIv() {
        likeDialog = DialogFactory.showLikeDialog(this);
        likeDialog.show();
        setmRemainTime();
        if (AppUtils.isLimitLike(mLoginUser.userType, mLoginUser.today_like)) {
            LikeRequest likeRequest = new LikeRequest();
            likeRequest.token = mBuProcessor.getToken();
            likeRequest.likeid = mUid;
            mPresenter.onRequestLike(likeRequest);
        } else {
            commonDialogOperationType = 3;
            DialogFactory.showOpenVipDialog(this, getResources().getString(R.string.dialog_open_vip_like));
        }
    }


    /**
     * 去聊天
     */
    private void goChat() {
        if (recommendUserInfoResponse.getRelation() == 2) {
            //启动单聊页面
            RongIM.getInstance().startPrivateChat(this, recommendUserInfoResponse.getRongyun_userid(), recommendUserInfoResponse.getNickname());
        } else {
            if (AppUtils.isLimitMsg(mLoginUser.userType, mLoginUser.today_chat)) {
                //启动单聊页面
                RequestFreeChat requestFreeChat = new RequestFreeChat();
                requestFreeChat.token = mBuProcessor.getToken();
                requestFreeChat.secret_id = String.valueOf(recommendUserInfoResponse.getUid());
                mPresenter.onRequestChatNum(requestFreeChat);
            } else {
                showOpenVipDialog(getResources().getString(R.string.dialog_open_vip_chat));
            }
        }
    }

    private void showOpenVipDialog(String title) {
        commonDialogOperationType = 5;
        DialogFactory.showOpenVipDialog(this, title);
    }

    @Override
    public void getLikeSuccess(LikeResponse likeResponse) {
        if (likeResponse.getState() == 1) {
            //相互喜欢
            showMatchSuccesDialog();
        }
        likedBg();
        //更新用户数据
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_HOME_INFO));
    }

    /**
     * 显示匹配成功弹框
     */
    private void showMatchSuccesDialog() {
        MatchSuccessDialog matchSuccessDialog = MatchSuccessDialog.newInstance();
        matchSuccessDialog.setListener(this);
        matchSuccessDialog.setContent(mLoginUser.nickname,mLoginUser.trait,recommendUserInfoResponse.getNickname(),recommendUserInfoResponse.getTrait());
        DialogFactory.showDialogFragment(getSupportFragmentManager(), matchSuccessDialog, MatchSuccessDialog.TAG);
    }

    /**
     * 开始聊天
     */
    @Override
    public void startChatBtnListener() {
        //启动单聊页面
        RongIM.getInstance().startPrivateChat(this, recommendUserInfoResponse.getRongyun_userid(), recommendUserInfoResponse.getNickname());
    }


    @Override
    public void getRecommendUserInfoSuccess(RecommendUserInfoResponse response) {
        recommendUserInfoResponse = response;
        LogUtils.e("response:" + new Gson().toJson(response));
        setUserData(response);
        for (RecommendUserInfoResponse.AlbumBean albumBean : response.getAlbum()) {
            MyAlbumResponse.DataBean dataBean = new MyAlbumResponse.DataBean();
            dataBean.setId(albumBean.getId());
            dataBean.setAlbum_url(albumBean.getAlbum_url());
            dataBean.setAlbum_type(albumBean.getAlbum_type());
            dataBean.setCost(albumBean.getCost());
            dataBean.setState(albumBean.getState());
            albumInfoLists.add(dataBean);
        }
        albumAdapter.setNewData(albumInfoLists);
        contactWayList = response.getContact();
        //是否查看过联系方式
        if (recommendUserInfoResponse.getIs_see_contact() == 1) {
            mLookOverTv.setVisibility(View.GONE);
            for (ContactWay contactWay : contactWayList) {
                contactWay.isShow = true;
            }
        } else {
            for (ContactWay contactWay : contactWayList) {
                contactWay.isShow = false;
            }
        }
        if (contactWayList.size() > 0) {
            contactWayAdapter.setNewData(contactWayList);
            mContactRl.setVisibility(View.VISIBLE);
        } else {
            mContactRl.setVisibility(View.GONE);
        }
        labelList = response.getLabel();
        if (labelList != null && labelList.size() > 0) {
            recommendUserLabelAdapter.setNewData(labelList);
            mLabelTv.setVisibility(View.VISIBLE);
        } else {
            mLabelTv.setVisibility(View.GONE);
        }
    }

    @Override
    public void blackUserListener() {
        commonDialogOperationType = 1;
        //显示加入黑名单弹框
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.dialog_add_black_friend), Constant.DIALOG_THREE);
    }

    @Override
    public void deleteUserListener() {
        commonDialogOperationType = 2;
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.dialog_delete_friend), Constant.DIALOG_THREE);
    }

    @Override
    public void reportUserListener() {
        //去举报用户界面
        ReportUserActivity.start(this, String.valueOf(mUid));
    }

    @Override
    public void commonDialogBtnOkListener() {
        if (commonDialogOperationType == 1) {
            BlackUserRequest blackUserRequest = new BlackUserRequest();
            blackUserRequest.token = mBuProcessor.getToken();
            blackUserRequest.uid = recommendUserInfoResponse.getUid();
            mPresenter.onRequestBlackUser(blackUserRequest);
        } else if (commonDialogOperationType == 2) {
            if (recommendUserInfoResponse.getRelation() == 2) {//好友
                DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
                deleteUserRequest.token = mBuProcessor.getToken();
                deleteUserRequest.del_user_id = recommendUserInfoResponse.getUid();
                mPresenter.onRequestDeleteUser(deleteUserRequest);
            } else {
                showToast(getResources().getString(R.string.RecommendUserInfoActivity_no_friends));
            }
        } else if (commonDialogOperationType == 3) {
            startActivitys(OpenVipActivity.class);
        } else if (commonDialogOperationType == 4) {
            lookContactTypeRequest(1);
        } else if (commonDialogOperationType == 5) {
            startActivitys(OpenVipActivity.class);
        }
    }

    @Override
    public void getBlackUserSuccess(String msg) {
        showToast(msg);
    }

    @Override
    public void getDeleteUserSuccess(String msg) {
        showToast(msg);
    }

    MyTimer myTimer;

    /**
     * 设置一分钟倒计时
     */
    private void setmRemainTime() {
        myTimer = MyTimer.getInstance(1000, 1000, this);
        myTimer.setListener(this);
        myTimer.cancel();
        myTimer.start();
    }

    /**
     * 一分钟倒计时结束
     */
    @Override
    public void onFinish() {
        if (myTimer != null) {//显示CommonDialog时取消myTimer计时
            myTimer.cancel();
            myTimer = null;
        }
        if (likeDialog.isShowing()) {
            likeDialog.dismiss();
        }
    }


    /**
     * 赚嗨豆
     */
    @Override
    public void earnBeansDialogBtnListener() {
        startActivitys(EarnBeansActivity.class);
    }

    /**
     * 去充值嗨豆
     */
    @Override
    public void rechargeBeansDialogBtnListener() {
        startActivitys(RechargeActivity.class);
    }

    /**
     * 使用beans 查看相册和联系方式
     */
    @Override
    public void messageUseBeansDialogBtnOkListener() {
        if (useBeansType == 1) { //相册
            lookAlbumByBeansRequest();
        } else if (useBeansType == 2) { //联系方式
            lookContactTypeRequest(2);
        }
    }

    /**
     * 查看联系方式请求
     * 嗨豆/VIP
     * type:1使用vip次数2使用嗨豆
     */
    private void lookContactTypeRequest(int type) {
        LookContactTypeRequest lookContactTypeRequest = new LookContactTypeRequest();
        lookContactTypeRequest.token = mBuProcessor.getToken();
        lookContactTypeRequest.uid = String.valueOf(mUid);
        lookContactTypeRequest.type = String.valueOf(type);
        mPresenter.onRequestContact(lookContactTypeRequest);
    }

    /**
     * 嗨豆查看相册
     */
    private void lookAlbumByBeansRequest() {
        LookAlbumByBeansRequest lookAlbumByBeansRequest = new LookAlbumByBeansRequest();
        lookAlbumByBeansRequest.token = mBuProcessor.getToken();
        lookAlbumByBeansRequest.beans = String.valueOf(mAlbumBean.getCost());
        lookAlbumByBeansRequest.album_id = String.valueOf(mAlbumBean.getId());
        lookAlbumByBeansRequest.see_id = String.valueOf(mUid);
        mPresenter.onRequestAlbumByBeans(lookAlbumByBeansRequest);
    }

    /**
     * 查看联系方式成功
     */
    @Override
    public void getContactSuccess(String msg) {
        for (ContactWay contactWay : contactWayList) {
            contactWay.isShow = true;
        }
        contactWayAdapter.notifyDataSetChanged();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_HOME_INFO));
        requestHomeUserInfo();
    }

    /**
     * 查看相册成功
     */
    @Override
    public void getAlbumByBeansSuccess(String msg) {
        mAlbumBean.setState(1);
        albumAdapter.notifyDataSetChanged();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_HOME_INFO));
        requestHomeUserInfo();
    }

    /**
     * 更新个人信息
     */
    private void requestHomeUserInfo() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);
    }

    /**
     * 查看用户信息（首页）成功
     */
    @Override
    public void getHomeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
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
    }

    /**
     * 密聊
     */
    @Override
    public void chatNumSuccess() {
        //进行更新
        requestHomeUserInfo();
        //启动单聊页面
        RongIM.getInstance().startPrivateChat(this, recommendUserInfoResponse.getRongyun_userid(), recommendUserInfoResponse.getNickname());
    }


    private void initializeInjector() {
        DaggerRecommendUserInfoComponent.builder().appComponent(getAppComponent())
                .recommendUserInfoModule(new RecommendUserInfoModule(RecommendUserInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
