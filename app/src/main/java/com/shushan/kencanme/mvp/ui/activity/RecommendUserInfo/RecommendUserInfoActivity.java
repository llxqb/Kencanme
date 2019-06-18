package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;

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
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.ContactWay;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.reportUser.ReportUserActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.ui.adapter.AlbumAdapter;
import com.shushan.kencanme.mvp.ui.adapter.MimeContactWayAdapter;
import com.shushan.kencanme.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.MyJzvdStd;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;
import com.shushan.kencanme.mvp.views.dialog.UseBeansDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推荐用户资料详情
 */
public class RecommendUserInfoActivity extends BaseActivity implements RecommendUserInfoControl.RecommendUserInfoView, CommonChoiceDialog.commonChoiceDialogListener,
        CommonDialog.CommonDialogListener, MyJzvdStd.MyjzvdListener, UseBeansDialog.UseBeansDialogListener {
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.head_icon)
    CircleImageView mHeadIcon;
    @BindView(R.id.recommend_user_head_bg)
    LinearLayout mRecommendUserHeadBg;
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
    private List<MyAlbumResponse.DataBean> albumInfoLists = new ArrayList<>();
    private List<ContactWay> contactWayList = new ArrayList<>();
    private AlbumAdapter albumAdapter;
    private MimeContactWayAdapter contactWayAdapter;
    private RecommendUserLabelAdapter recommendUserLabelAdapter;
    int mUid;
    /**
     * 1 加入黑名单  2 删除好友  3 喜欢好友
     */
    int operationType;
    @Inject
    RecommendUserInfoControl.PresenterRecommendUserInfo mPresenter;
    List<String> labelList = new ArrayList<>();
    private RecommendUserInfoResponse recommendUserInfoResponse;
    private LoginUser mLoginUser;

    public static void start(Context context, int uid) {
        Intent intent = new Intent(context, RecommendUserInfoActivity.class);
        intent.putExtra("uid", uid);
        context.startActivity(intent);
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
        albumAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyAlbumResponse.DataBean bean = albumAdapter.getItem(position);

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
                if (recommendUserInfoResponse.getIs_see_contact() != 1) {
                    showContactWay();
                }
                break;
            case R.id.recommend_like_iv:
                likeIv();
                break;
            case R.id.recommend_chat_iv:
                break;
        }
    }

    /**
     * 设置页面数据
     */
    private void setUserData(RecommendUserInfoResponse recommendUserInfoResponse) {
        mImageLoaderHelper.displayBackgroundImage(this, recommendUserInfoResponse.getCover(), mRecommendUserHeadBg, Constant.LOADING_MIDDLE);
        mImageLoaderHelper.displayImage(this, recommendUserInfoResponse.getTrait(), mHeadIcon, Constant.LOADING_AVATOR);
        mRecommendUsername.setText(recommendUserInfoResponse.getNickname());
        if (recommendUserInfoResponse.getSex() == 1) {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mSexYearTvValue = recommendUserInfoResponse.getAge() + " years";
        String activeTimeValue = "active " + recommendUserInfoResponse.getActive_time() + " min age";
        mRecommendUserSexYear.setText(mSexYearTvValue);
        mRecommendActiveTime.setText(activeTimeValue);
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

        //是否查看过联系方式
        if (recommendUserInfoResponse.getIs_see_contact() == 1) {
            for (ContactWay contactWay : contactWayList) {
                contactWay.isShow = true;
            }
            contactWayAdapter.notifyDataSetChanged();
        }else {
            for (ContactWay contactWay : contactWayList) {
                contactWay.isShow = true;
            }
            contactWayAdapter.notifyDataSetChanged();
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
     * Go喜欢
     */
    private void likeIv() {
        operationType = 3;
        //TODO 判断用户类型
//        if(AppUtils.userType(mLoginUser.svip,mLoginUser.vip,mLoginUser.sex)==2){
//        }
        if (mLoginUser != null && mLoginUser.today_like < 20) {
            LikeRequest likeRequest = new LikeRequest();
            likeRequest.token = mBuProcessor.getToken();
            likeRequest.likeid = mUid;
            mPresenter.onRequestLike(likeRequest);
        } else {
            CommonDialog commonDialog = CommonDialog.newInstance();
            commonDialog.setListener(this);
            commonDialog.setContent("Today's favorite number has been used up. Open members can enjoy unlimited ~");
            commonDialog.setStyle(Constant.DIALOG_TWO);
            DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
        }
    }

    @Override
    public void getLikeSuccess(String msg) {
        showToast(msg);
        likedBg();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_HOME_INFO));
//        requestHomeUserInfo();
        //更新用户数据
    }

    private void showContactWay() {
        UseBeansDialog useBeansDialog = UseBeansDialog.newInstance();
        useBeansDialog.setTitle("Look over type?");
        useBeansDialog.setListener(this);
        useBeansDialog.setContent("become vip","20 hi-beans");
        DialogFactory.showDialogFragment((this).getSupportFragmentManager(), useBeansDialog, UseBeansDialog.TAG);
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
            albumInfoLists.add(dataBean);
        }
        albumAdapter.setNewData(albumInfoLists);
        contactWayList = response.getContact();
        contactWayAdapter.setNewData(contactWayList);
        labelList = response.getLabel();
        recommendUserLabelAdapter.setNewData(labelList);
    }

    @Override
    public void blackUserListener() {
        operationType = 1;
        //显示加入黑名单弹框
        DialogFactory.showCommonDialog(this, "Determine to blacklist the user? After joining the blacklist, the user will no longer be pushed for you.?", Constant.DIALOG_THREE);
    }

    @Override
    public void deleteUserListener() {
        operationType = 2;
        DialogFactory.showCommonDialog(this, "Are you sure you will delete this friend? Please think clearly.", Constant.DIALOG_THREE);
    }

    @Override
    public void reportUserListener() {
        //去举报用户界面
        startActivitys(ReportUserActivity.class);
    }

    @Override
    public void commonDialogBtnOkListener() {
        if (operationType == 1) {
            BlackUserRequest blackUserRequest = new BlackUserRequest();
            blackUserRequest.token = mBuProcessor.getToken();
            blackUserRequest.uid = recommendUserInfoResponse.getUid();
            mPresenter.onRequestBlackUser(blackUserRequest);
        } else if (operationType == 2) {
            if (recommendUserInfoResponse.getRelation() == 2) {//好友
                DeleteUserRequest deleteUserRequest = new DeleteUserRequest();
                deleteUserRequest.token = mBuProcessor.getToken();
                deleteUserRequest.del_user_id = recommendUserInfoResponse.getUid();
                mPresenter.onRequestDeleteUser(deleteUserRequest);
            } else {
                showToast("你们不是好友关系");
            }
        } else if (operationType == 3) {
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


    @Override
    public void jzvdClickListener(int clickPos) {
        // VIp can view
        showToast("1111");
    }

    @Override
    public void useBeansDialogLeftListener() {

    }

    @Override
    public void useBeansDialogRightListener() {
        for (ContactWay contactWay : contactWayList) {
            contactWay.isShow = true;
        }
        contactWayAdapter.notifyDataSetChanged();
    }

    private void initializeInjector() {
        DaggerRecommendUserInfoComponent.builder().appComponent(getAppComponent())
                .recommendUserInfoModule(new RecommendUserInfoModule(RecommendUserInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
