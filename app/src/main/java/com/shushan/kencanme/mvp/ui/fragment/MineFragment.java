package com.shushan.kencanme.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerMineFragmentComponent;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.di.modules.MineFragmentModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.entity.request.MyAlbumRequest;
import com.shushan.kencanme.entity.response.ContactWay;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.mvp.ui.activity.pay.RechargeActivity;
import com.shushan.kencanme.mvp.ui.activity.personInfo.EditContactWayActivity;
import com.shushan.kencanme.mvp.ui.activity.personInfo.EditLabelActivity;
import com.shushan.kencanme.mvp.ui.activity.personInfo.EditMakeFriendsInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.personInfo.EditPersonalInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.photo.LookPhotoActivity;
import com.shushan.kencanme.mvp.ui.activity.photo.MyAlbumActivity;
import com.shushan.kencanme.mvp.ui.activity.photo.UploadPhotoActivity;
import com.shushan.kencanme.mvp.ui.activity.setting.SettingActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.ui.adapter.AlbumAdapter;
import com.shushan.kencanme.mvp.ui.adapter.MimeContactWayAdapter;
import com.shushan.kencanme.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.mvp.ui.fragment.mine.MineFragmentControl;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MineFragment
 * 我的
 */

public class MineFragment extends BaseFragment implements MineFragmentControl.MineView {
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
    @BindView(R.id.personal_info_ll)
    LinearLayout mPersonalInfoLl;
    @BindView(R.id.avator)
    CircleImageView mAvator;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.sex_year_tv)
    TextView mSexYearTv;
    @BindView(R.id.svip_icon)
    ImageView mSVipIcon;
    @BindView(R.id.desc_tv)
    TextView mDescTv;
    //我的照片
    private List<MyAlbumResponse.DataBean> photoBeanList = new ArrayList<>();
    List<ContactWay> contactWayList;//联系方式集合
    List<String> labelList;//联系方式集合
    private AlbumAdapter mAlbumAdapter;
    private MimeContactWayAdapter mimeContactWayAdapter;
    private RecommendUserLabelAdapter recommendUserLabelAdapter;

    @Inject
    MineFragmentControl.mineFragmentPresenter mPresenter;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            setUserInfo();
        } else if (intent.getAction() != null && (intent.getAction().equals(ActivityConstant.UPDATE_MY_ALBUM) || intent.getAction().equals(ActivityConstant.UPDATE_MY_ALBUM_FROM_MYALBUM))) {
            //更新我的相册
            MyAlbumRequest myAlbumRequest = new MyAlbumRequest();
            myAlbumRequest.token = mBuProcessor.getToken();
            mPresenter.onRequestMyAlbum(myAlbumRequest);
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_USER_INFO);
        mFilter.addAction(ActivityConstant.UPDATE_MY_ALBUM);
        mFilter.addAction(ActivityConstant.UPDATE_MY_ALBUM_FROM_MYALBUM);
    }


    @Override
    public void initView() {
        mAlbumRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mAlbumAdapter = new AlbumAdapter(getActivity(), photoBeanList, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(mAlbumAdapter);
        mAlbumRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyAlbumResponse.DataBean dataBean = (MyAlbumResponse.DataBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.photo_item_rl:
                        if (position == 0) {
                            if (mAlbumAdapter.getItemCount() >= 13) {
                                showToast(getResources().getString(R.string.album_max_num));
                            } else {
                                startActivitys(UploadPhotoActivity.class);//上传图片  这里都是新增图片
                            }
                        } else {
                            assert dataBean != null;
                            LookPhotoActivity.start(getActivity(), dataBean.getAlbum_url());//查看大图
                        }
                        break;
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
        setUserInfo();
        MyAlbumRequest myAlbumRequest = new MyAlbumRequest();
        myAlbumRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMyAlbum(myAlbumRequest);
    }

    /**
     * 设置用户信息
     */
    private void setUserInfo() {
        //contactWay
        String contactWayString = mBuProcessor.getLoginUser().contact; //转换联系方式为list
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
        String labelString = mBuProcessor.getLoginUser().label; //转换联系方式为list
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

        LoginUser mLoginUser = mBuProcessor.getLoginUser();
        mImageLoaderHelper.displayImage(getActivity(), mLoginUser.trait, mAvator, Constant.LOADING_AVATOR);
        mUsername.setText(mLoginUser.nickname);
        if (mLoginUser.sex == 1) {
            mSexYearTv.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mSexYearTv.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mSexYearTvValue = mLoginUser.age + getResources().getString(R.string.HomeViewPagerAdapter_years);
        mSexYearTv.setText(mSexYearTvValue);
        //svip不为0显示图标
        if (mLoginUser.svip == 0) {
            mSVipIcon.setVisibility(View.GONE);
            //不是svip 判断是否是vip
            if (mLoginUser.vip == 1) {
                mVipTimeTv.setText(mLoginUser.vip_time);
            } else {
                mVipTimeTv.setText(getResources().getString(R.string.MineFragment_Become_VIP));
            }
        } else {
            mSVipIcon.setVisibility(View.VISIBLE);
            mVipTimeTv.setText(getResources().getString(R.string.MineFragment_hello_VIP));
        }
        mDescTv.setText(mLoginUser.declaration);
        mImageLoaderHelper.displayBackgroundImage(getActivity(), mLoginUser.cover, mPersonalInfoLl, Constant.LOADING_MIDDLE);
        mHiBeansNumTv.setText(String.valueOf(mLoginUser.beans));
        //Personal Information
        mUserLocation.setText(mLoginUser.city);
        String mUserHeightValue = !TextUtils.isEmpty(mLoginUser.height) ? "Height: " + mLoginUser.height + "cm" : "Height: ";
        String mUserWeightValue = !TextUtils.isEmpty(mLoginUser.weight) ? "Weight: " + mLoginUser.weight + "kg" : "Weight: ";
        String mUserChestValue = "Chest:" + mLoginUser.bust;
        String mUserBirthdayValue = "Birthday:" + mLoginUser.birthday;
        String mUserProfessionalValue = "Professional:" + mLoginUser.occupation;
        mUserHeight.setText(mUserHeightValue);
        mUserWeight.setText(mUserWeightValue);
        mUserChest.setText(mUserChestValue);
        mUserBirthday.setText(mUserBirthdayValue);
        mUserProfessional.setText(mUserProfessionalValue);
    }


    @OnClick({R.id.mine_set_up, R.id.line_customer, R.id.edit_personal_info_fab, R.id.barn_hi_beans, R.id.recharge, R.id.vip_time_rl, R.id.album_tv,
            R.id.personal_info_tv, R.id.contact_way_tv, R.id.label_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_set_up:
                startActivitys(SettingActivity.class);
                break;
            case R.id.line_customer:
                break;
            case R.id.edit_personal_info_fab:
                //编辑个人资料
                startActivitys(EditMakeFriendsInfoActivity.class);
                break;
            case R.id.barn_hi_beans:
                showToast("waiting development...");
                break;
            case R.id.recharge:
                //充值
                startActivitys(RechargeActivity.class);
                break;
            case R.id.vip_time_rl:
                startActivitys(OpenVipActivity.class);
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

    @Override
    public void getMyAlbumSuccess(MyAlbumResponse response) {
        photoBeanList = response.getData();
        MyAlbumResponse.DataBean dataBean = new MyAlbumResponse.DataBean();
        dataBean.setAlbum_url("");
        photoBeanList.add(0, dataBean); //设置RecyclerView第一张图片为默认图片
        mAlbumAdapter.setNewData(photoBeanList);
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
