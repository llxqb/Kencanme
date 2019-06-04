package com.shushan.kencanme.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.mvp.ui.activity.Person_info.EditLabelActivity;
import com.shushan.kencanme.mvp.ui.activity.Person_info.EditMakeFriendsInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.Person_info.EditPersonalInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.pay.RechargeActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MineFragment
 * 我的
 */

public class MineFragment extends BaseFragment {

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
    @BindView(R.id.contact_way_tv)
    TextView mContactWayTv;
    @BindView(R.id.label_tv)
    TextView mLabelTv;
    @BindView(R.id.contact_recycler_view)
    RecyclerView mContactRecyclerView;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    Unbinder unbinder;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(getActivity(), null);
        initView();
        initData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mine_set_up, R.id.line_customer, R.id.edit_personal_info_fab, R.id.barn_hi_beans, R.id.recharge, R.id.vip_time_rl, R.id.album_tv,
            R.id.personal_info_tv, R.id.contact_way_tv, R.id.label_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_set_up:
                break;
            case R.id.line_customer:
                break;
            case R.id.edit_personal_info_fab:
                //编辑个人资料
                startActivitys(EditMakeFriendsInfoActivity.class);
                break;
            case R.id.barn_hi_beans:
                break;
            case R.id.recharge:
                //充值
                startActivitys(RechargeActivity.class);
                break;
            case R.id.vip_time_rl:
                startActivitys(OpenVipActivity.class);
                break;
            case R.id.album_tv:
                break;
            case R.id.personal_info_tv:
                //编辑个人信息
                startActivitys(EditPersonalInfoActivity.class);
                break;
            case R.id.contact_way_tv:
                break;
            case R.id.label_tv:
                startActivitys(EditLabelActivity.class);
                break;
        }
    }
}
