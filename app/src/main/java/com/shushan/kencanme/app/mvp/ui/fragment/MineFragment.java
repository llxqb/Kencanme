package com.shushan.kencanme.app.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerMineFragmentComponent;
import com.shushan.kencanme.app.di.modules.MainModule;
import com.shushan.kencanme.app.di.modules.MineFragmentModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;
import com.shushan.kencanme.app.entity.base.BaseFragment;
import com.shushan.kencanme.app.entity.request.MyAlbumRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.ContactWay;
import com.shushan.kencanme.app.entity.response.MyAlbumResponse;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.loveMe.LoveMePeopleActivity;
import com.shushan.kencanme.app.mvp.ui.activity.pay.RechargeActivity;
import com.shushan.kencanme.app.mvp.ui.activity.personInfo.EditContactWayActivity;
import com.shushan.kencanme.app.mvp.ui.activity.personInfo.EditLabelActivity;
import com.shushan.kencanme.app.mvp.ui.activity.personInfo.EditMakeFriendsInfoActivity;
import com.shushan.kencanme.app.mvp.ui.activity.personInfo.EditPersonalInfoActivity;
import com.shushan.kencanme.app.mvp.ui.activity.photo.LookPhotoActivity;
import com.shushan.kencanme.app.mvp.ui.activity.photo.MyAlbumActivity;
import com.shushan.kencanme.app.mvp.ui.activity.photo.UploadPhotoActivity;
import com.shushan.kencanme.app.mvp.ui.activity.register.EarnBeansActivity;
import com.shushan.kencanme.app.mvp.ui.activity.setting.SettingActivity;
import com.shushan.kencanme.app.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.MimeContactWayAdapter;
import com.shushan.kencanme.app.mvp.ui.adapter.MineFragmentUserAlbumAdapter;
import com.shushan.kencanme.app.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.app.mvp.ui.fragment.mine.MineFragmentControl;
import com.shushan.kencanme.app.mvp.utils.AppUtils;
import com.shushan.kencanme.app.mvp.utils.DateUtil;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.utils.TranTools;
import com.shushan.kencanme.app.mvp.views.CircleImageView;
import com.shushan.kencanme.app.mvp.views.CommonDialog;
import com.shushan.kencanme.app.mvp.views.CornerLabelView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jzvd.JzvdStd;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.CSCustomServiceInfo;

/**
 * MineFragment
 * 我的
 */

public class MineFragment extends BaseFragment implements MineFragmentControl.MineView, CommonDialog.CommonDialogListener {
    @BindView(R.id.mine_set_up)
    ImageView mMineSetUp;
    @BindView(R.id.line_customer)
    ImageView mLineCustomer;
    @BindView(R.id.edit_personal_info_fab)
    FloatingActionButton mEditPersonalInfoFab;
    @BindView(R.id.hi_beans_num_tv)
    TextView mHiBeansNumTv;
    @BindView(R.id.barn_hi_beans)
    TextView mBarnHiBeans;
    @BindView(R.id.recharge)
    TextView mRecharge;
    @BindView(R.id.vip_time_hint)
    TextView mVipTimeHint;
    @BindView(R.id.vip_time_tv)
    TextView mVipTimeTv;
    @BindView(R.id.vip_time_rl)
    RelativeLayout mVipTimeRl;
    @BindView(R.id.album_tv)
    TextView mAlbumTv;
    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    @BindView(R.id.personal_info_tv)
    TextView mPersonalInfoTv;
    @BindView(R.id.cover_iv)
    ImageView mCoverIv;
    @BindView(R.id.jz_video)
    JzvdStd mJzvdStd;
    @BindView(R.id.cornerLabelView)
    CornerLabelView mCornerLabelView;
    @BindView(R.id.user_location)
    TextView mUserLocation;
    @BindView(R.id.user_height)
    TextView mUserHeight;
    @BindView(R.id.user_weight)
    TextView mUserWeight;
    @BindView(R.id.user_chest)
    TextView mUserChest;
    @BindView(R.id.user_birthday)
    TextView mUserBirthday;
    @BindView(R.id.user_professional)
    TextView mUserProfessional;
    @BindView(R.id.contact_way_hint_tv)
    TextView mContactWayHintTv;
    @BindView(R.id.label_hint_tv)
    TextView mLabelHintTv;
    @BindView(R.id.contact_way_tv)
    TextView mContactWayTv;
    @BindView(R.id.label_tv)
    TextView mLabelTv;
    @BindView(R.id.contact_recycler_view)
    RecyclerView mContactRecyclerView;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    Unbinder unbinder;
    //个人资料
    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.vip_logo_iv)
    ImageView mVipLogoIv;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.sex_year_tv)
    TextView mSexYearTv;
    @BindView(R.id.svip_icon)
    ImageView mSVipIcon;
    @BindView(R.id.desc_tv)
    TextView mDescTv;
    @BindView(R.id.new_pairing_hint_tv)
    TextView mNewPairingHintTv;
    @BindView(R.id.new_pairing_iv)
    CircleImageView mNewPairingIv;
    @BindView(R.id.new_pairing_num_tv)
    TextView mNewPairingNumTv;
    @BindView(R.id.new_pairing_tv)
    TextView mNewPairingTv;
    @BindView(R.id.new_pairing_rl)
    RelativeLayout mNewPairingRl;
    @BindView(R.id.new_pairing_line_view)
    View mNewPairingLineView;
    @BindView(R.id.total_love_you_num_tv)
    TextView mTotalLoveYouNumTv;
    //我的照片
    private List<MyAlbumResponse.DataBean> photoBeanList = new ArrayList<>();
    List<ContactWay> contactWayList;//联系方式集合
    List<String> labelList;//联系方式集合
    private MineFragmentUserAlbumAdapter mAlbumAdapter;
    private MimeContactWayAdapter mimeContactWayAdapter;
    private RecommendUserLabelAdapter recommendUserLabelAdapter;
    private LoginUser mLoginUser;

    @Inject
    MineFragmentControl.mineFragmentPresenter mPresenter;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            //查询我的，之前查询我的接口全部可以删除掉
            requestPersonalInfo();
        } else {
            JzvdStd.goOnPlayOnPause();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(getActivity(), null);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        initView();
        initData();
        return view;
    }


    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_USER_INFO)) {
            //查询-我的-接口
            requestPersonalInfo();
        } else if (intent.getAction() != null && (intent.getAction().equals(ActivityConstant.UPDATE_MY_ALBUM) || intent.getAction().equals(ActivityConstant.UPDATE_MY_ALBUM_FROM_MYALBUM))) {
            //更新我的相册
            MyAlbumRequest myAlbumRequest = new MyAlbumRequest();
            myAlbumRequest.token = mBuProcessor.getToken();
            mPresenter.onRequestMyAlbum(myAlbumRequest);
        } else if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.PAY_SUCCESS_UPDATE_INFO)) {
            // 充值后更新
            mLoginUser = mBuProcessor.getLoginUser();
            setUserInfo();
        }
        super.onReceivePro(context, intent);
    }


    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_USER_INFO);
        mFilter.addAction(ActivityConstant.UPDATE_MY_ALBUM);
        mFilter.addAction(ActivityConstant.UPDATE_MY_ALBUM_FROM_MYALBUM);
        mFilter.addAction(ActivityConstant.PAY_SUCCESS_UPDATE_INFO);
    }

    @Override
    public void onPause() {
        super.onPause();
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void initView() {
        mLoginUser = mBuProcessor.getLoginUser();
        mAlbumRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAlbumAdapter = new MineFragmentUserAlbumAdapter(getActivity(), photoBeanList, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(mAlbumAdapter);
        mAlbumRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyAlbumResponse.DataBean dataBean = (MyAlbumResponse.DataBean) adapter.getItem(position);
                if (view.getId() == R.id.photo_item_rl) {
                    if (position == 0) {
                        if (mAlbumAdapter.getItemCount() >= 13) {
                            showToast(getResources().getString(R.string.album_num_max_12));
                        } else {
                            startActivitys(UploadPhotoActivity.class);//上传图片  这里都是新增图片
                        }
                    } else {
                        assert dataBean != null;
                        LookPhotoActivity.start(getActivity(), dataBean.getAlbum_url());//查看大图
                    }
                }
            }
        });
        mContactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mimeContactWayAdapter = new MimeContactWayAdapter(contactWayList);
        mContactRecyclerView.setAdapter(mimeContactWayAdapter);
        mLabelRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recommendUserLabelAdapter = new RecommendUserLabelAdapter(getActivity(), labelList);
        mLabelRecyclerView.setAdapter(recommendUserLabelAdapter);
    }

    @Override
    public void initData() {
        MyAlbumRequest myAlbumRequest = new MyAlbumRequest();
        myAlbumRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMyAlbum(myAlbumRequest);
    }

    /**
     * 查询 我的
     * 更新用户数据，这个能够查询用户上传的视频状态
     */
    private void requestPersonalInfo() {
        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest();
        personalInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestPersonalInfo(personalInfoRequest);
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo() {
        //contactWay
        String contactWayString = mLoginUser.contact; //转换联系方式为list
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ContactWay>>() {
        }.getType();
        if (!TextUtils.isEmpty(contactWayString)) {
            contactWayList = gson.fromJson(contactWayString, listType);
        }
        mimeContactWayAdapter.setNewData(contactWayList);
        if (contactWayList != null && contactWayList.size() == 0) {
            mContactWayHintTv.setVisibility(View.VISIBLE);
        } else {
            mContactWayHintTv.setVisibility(View.GONE);
        }
        //label
        String labelString = mLoginUser.label; //转换标签方式为list
        Gson gson2 = new Gson();
        Type labelListType = new TypeToken<List<String>>() {
        }.getType();
        if (!TextUtils.isEmpty(labelString)) {
            labelList = gson2.fromJson(labelString, labelListType);
        }
        recommendUserLabelAdapter.setNewData(labelList);
        if (labelList != null && labelList.size() == 0) {
            mLabelHintTv.setVisibility(View.VISIBLE);
        } else {
            mLabelHintTv.setVisibility(View.GONE);
        }
        if (TranTools.isVideo(mLoginUser.cover)) {
            if (mLoginUser.state == 0) {
                mCornerLabelView.setVisibility(View.GONE);
            } else if (mLoginUser.state == 1) {
                mCornerLabelView.setVisibility(View.VISIBLE);
                mCornerLabelView.setText1(getResources().getString(R.string.video_review));
                mCornerLabelView.setFillColor(getResources().getColor(R.color.app_color));
            } else if (mLoginUser.state == 2) {
                mCornerLabelView.setVisibility(View.VISIBLE);
                mCornerLabelView.setText1(getResources().getString(R.string.video_review_no_passed));
                mCornerLabelView.setFillColor(getResources().getColor(R.color.red_color_btn));
            }
            mCoverIv.setVisibility(View.GONE);
            mJzvdStd.setVisibility(View.VISIBLE);
            mJzvdStd.setUp(mLoginUser.cover, "");
            PicUtils.loadVideoScreenshot(getActivity(), mLoginUser.cover, mJzvdStd.thumbImageView, 0, true);
        } else {
            mCoverIv.setVisibility(View.VISIBLE);
            mJzvdStd.setVisibility(View.GONE);
            mImageLoaderHelper.displayImage(getActivity(), mLoginUser.cover, mCoverIv, Constant.LOADING_MIDDLE);
        }
        mImageLoaderHelper.displayImage(getActivity(), mLoginUser.trait, mAvatar, Constant.LOADING_AVATOR);

        mUsername.setText(mLoginUser.nickname);
        if (mLoginUser.sex == 1) {
            mSexYearTv.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mSexYearTv.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mSexYearTvValue = mLoginUser.age + " " + getResources().getString(R.string.HomeViewPagerAdapter_years);
        mSexYearTv.setText(mSexYearTvValue);
        //svip不为0显示图标
        if (mLoginUser.svip == 0) {
            mSVipIcon.setVisibility(View.GONE);
            //不是svip 判断是否是vip
            if (mLoginUser.vip == 1) {
                String vipTimeValue = getResources().getString(R.string.MineFragment_vip_time) + DateUtil.getStrTime(mLoginUser.vip_time, "yyyy/MM/dd");
                mVipTimeTv.setText(vipTimeValue);
                mAvatar.setBorderColor(getResources().getColor(R.color.vip_bg_color));
                mVipLogoIv.setVisibility(View.VISIBLE);
            } else {
                mVipTimeTv.setText(getResources().getString(R.string.MineFragment_Become_VIP));
                mVipLogoIv.setVisibility(View.INVISIBLE);
                mAvatar.setBorderColor(getResources().getColor(R.color.white));
            }
        } else {
            mSVipIcon.setVisibility(View.VISIBLE);
            mVipLogoIv.setVisibility(View.VISIBLE);
            mAvatar.setBorderColor(getResources().getColor(R.color.vip_bg_color));
            mVipTimeTv.setText(getResources().getString(R.string.MineFragment_hello_VIP));
        }
        mDescTv.setText(mLoginUser.declaration);
        mHiBeansNumTv.setText(String.valueOf(mLoginUser.beans));
        //Personal Information
        mUserLocation.setText(mLoginUser.city);
        String mUserHeightValue = !TextUtils.isEmpty(mLoginUser.height) ? getResources().getString(R.string.Height) + mLoginUser.height + "cm" : getResources().getString(R.string.Height);
        String mUserWeightValue = !TextUtils.isEmpty(mLoginUser.weight) ? getResources().getString(R.string.Weight) + mLoginUser.weight + "kg" : getResources().getString(R.string.Weight);
        String mUserChestValue = getResources().getString(R.string.Chest) + mLoginUser.bust;
        if (!TextUtils.isEmpty(mLoginUser.birthday) && !mLoginUser.birthday.contains("/")) {
            String mUserBirthdayValue = getResources().getString(R.string.Birthday) + DateUtil.getStrTime(Long.parseLong(mLoginUser.birthday), "yyyy/MM/dd");
            mUserBirthday.setText(mUserBirthdayValue);
        }
        String mUserProfessionalValue = getResources().getString(R.string.Professional) + mLoginUser.occupation;
        mUserHeight.setText(mUserHeightValue);
        mUserWeight.setText(mUserWeightValue);
        mUserChest.setText(mUserChestValue);
        mUserProfessional.setText(mUserProfessionalValue);

        //显示最新匹配 布局
        if (mLoginUser.newLikeState != 0) {
            mNewPairingRl.setVisibility(View.VISIBLE);
            mTotalLoveYouNumTv.setVisibility(View.GONE);
            if (AppUtils.userType(mLoginUser.svip, mLoginUser.vip, mLoginUser.sex) == 3) {
                mImageLoaderHelper.displayImage(getActivity(), mLoginUser.newLikeTrait, mNewPairingIv, Constant.LOADING_AVATOR);
            } else {
                mImageLoaderHelper.displayGlassImage(getActivity(), mLoginUser.newLikeTrait, mNewPairingIv, Constant.LOADING_AVATOR);
            }
            mNewPairingNumTv.setText(String.valueOf(mLoginUser.newLikeCount));
            String mNewPairingTvValue = getResources().getString(R.string.ConversationListFragment_add) + " <font color = '#FF2D5B'>" + mLoginUser.newLikeCount + "</font> " + getResources().getString(R.string.ConversationListFragment_new_like_people_hint);
            mNewPairingTv.setText(Html.fromHtml(mNewPairingTvValue));
        } else {
            mNewPairingRl.setVisibility(View.GONE);
            mTotalLoveYouNumTv.setVisibility(View.VISIBLE);
            String loveYouNumValue = getResources().getString(R.string.MineFragment_total) + " <font color = '#FF2D5B'>" + mLoginUser.newLikeCount + "</font> " + getResources().getString(R.string.MineFragment_love_you_people_num);
            mTotalLoveYouNumTv.setText(Html.fromHtml(loveYouNumValue));
        }
    }


    @OnClick({R.id.mine_set_up, R.id.line_customer, R.id.edit_personal_info_fab, R.id.barn_hi_beans, R.id.recharge, R.id.vip_time_rl, R.id.new_pairing_rl,
            R.id.total_love_you_num_tv, R.id.album_tv, R.id.personal_info_tv, R.id.contact_way_tv, R.id.label_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_set_up:
                startActivitys(SettingActivity.class);
                break;
            case R.id.line_customer:
                contactCustomer();
                break;
            case R.id.edit_personal_info_fab:
                //编辑个人资料
                startActivitys(EditMakeFriendsInfoActivity.class);
                break;
            case R.id.barn_hi_beans:
                startActivitys(EarnBeansActivity.class);
                break;
            case R.id.recharge:
                //充值
                startActivitys(RechargeActivity.class);
                break;
            case R.id.vip_time_rl:
                startActivitys(OpenVipActivity.class);
                break;
            case R.id.new_pairing_rl:
                //新配对
                if (mLoginUser.userType == 3) {
                    LoveMePeopleActivity.start(getActivity(),"2");
                } else {
                    showSuperVipDialog();
                }
                break;
            case R.id.total_love_you_num_tv:
                //所有喜欢你的人
                if (mLoginUser.userType == 3) {
                    LoveMePeopleActivity.start(getActivity(),"3");
                } else {
                    showSuperVipDialog();
                }
                break;
            case R.id.album_tv:
                MyAlbumActivity.start(getActivity(), photoBeanList);
                break;
            case R.id.personal_info_tv:
                //编辑个人信息
                startActivitys(EditPersonalInfoActivity.class);
                break;
            case R.id.contact_way_tv:
                EditContactWayActivity.start(getActivity(), (ArrayList<ContactWay>) contactWayList);
                break;
            case R.id.label_tv:
                startActivitys(EditLabelActivity.class);
                break;
        }
    }

    /**
     * 提示开通超级vip
     */
    private void showSuperVipDialog() {
        DialogFactory.showOpenVipDialogFragment(getActivity(), this, getResources().getString(R.string.dialog_open_vip_chat_like));
    }

    @Override
    public void commonDialogBtnOkListener() {
        startActivitys(OpenVipActivity.class);
    }

    /**
     * 启动客户服聊天界面。
     * param customerServiceId 要与之聊天的客服 Id。
     * param title             聊天的标题，如果传入空值，则默认显示与之聊天的客服名称。
     * param customServiceInfo 当前使用客服者的用户信息。{@link CSCustomServiceInfo}
     */
    private void contactCustomer() {
        //进入客服
        //首先需要构造使用客服者的用户信息
        CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
        CSCustomServiceInfo csInfo = csBuilder.nickName(mLoginUser.nickname).build();
        RongIM.getInstance().startCustomerServiceChat(Objects.requireNonNull(getActivity()), ServerConstant.RY_CUSTOMER_ID, getResources().getString(R.string.online_customer), csInfo);
        mSharePreferenceUtil.setData("chatType", 1);//在线客服
    }

    @Override
    public void getMyAlbumSuccess(MyAlbumResponse response) {
        photoBeanList = response.getData();
        MyAlbumResponse.DataBean dataBean = new MyAlbumResponse.DataBean();
        dataBean.setAlbum_url("");
        photoBeanList.add(0, dataBean); //设置RecyclerView第一张图片为默认图片
        mAlbumAdapter.setNewData(photoBeanList);
    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {
        //保存用户信息
        mBuProcessor.setLoginUser(LoginUtils.tranLoginUser(response));
        mLoginUser = mBuProcessor.getLoginUser();
        setUserInfo();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializeInjector() {
        DaggerMineFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .mineFragmentModule(new MineFragmentModule(this))
                .build().inject(this);
    }


}
