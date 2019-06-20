package com.shushan.kencanme.mvp.ui.activity.loveMe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerLoveMePeopleComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.LoveMePeopleModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.LoveMeFriendsAdapter;
import com.shushan.kencanme.mvp.utils.AppUtils;
import com.shushan.kencanme.mvp.views.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看喜欢我的人
 */
public class LoveMePeopleActivity extends BaseActivity implements LoveMePeopleControl.LoveMePeopleView, CommonDialog.CommonDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonIvRight;
    @BindView(R.id.love_me_recycler_view)
    RecyclerView mLoveMeRecyclerView;

    private LoveMeFriendsAdapter myFriendsAdapter;
    List<MyFriendsResponse.ListBean> listBeanList = new ArrayList<>();
    LoginUser mLoginUser;
    @Inject
    LoveMePeopleControl.PresenterLoveMePeople mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_me_people);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    MyFriendsResponse.ListBean listBean;

    @Override
    public void initView() {
        mLoginUser = mBuProcessor.getLoginUser();
        mCommonTitleTv.setText(getResources().getString(R.string.LoveMePeopleActivity_title));
        mCommonIvRight.setVisibility(View.VISIBLE);
        mLoveMeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myFriendsAdapter = new LoveMeFriendsAdapter(this, listBeanList, mImageLoaderHelper);
        mLoveMeRecyclerView.setAdapter(myFriendsAdapter);
        mLoveMeRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                listBean = (MyFriendsResponse.ListBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.like_iv:
                        if (!listBean.isLike) {
                            if (AppUtils.isLimitLike(AppUtils.userType(mLoginUser.svip, mLoginUser.vip, mLoginUser.sex), mLoginUser.today_like)) {
                                LikeRequest likeRequest = new LikeRequest();
                                likeRequest.token = mBuProcessor.getToken();
                                assert listBean != null;
                                likeRequest.likeid = listBean.getUid();
                                mPresenter.onRequestLike(likeRequest);
                            } else {
                                CommonDialog commonDialog = CommonDialog.newInstance();
                                commonDialog.setListener(LoveMePeopleActivity.this);
                                commonDialog.setContent("Today's favorite number has been used up. Open members can enjoy unlimited ~");
                                commonDialog.setStyle(Constant.DIALOG_TWO);
                                DialogFactory.showDialogFragment(getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
                            }
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        MyFriendsRequest myFriendsRequest = new MyFriendsRequest();
        myFriendsRequest.token = mBuProcessor.getToken();
        myFriendsRequest.type = "2";//1好友 2喜欢
        mPresenter.onRequestMyFriendList(myFriendsRequest);
    }

    @OnClick({R.id.common_back, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
//                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
//                commonChoiceDialog.setListener(this);
//                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);
                break;
        }
    }

    @Override
    public void getLoveMePeopleInfoSuccess(MyFriendsResponse myFriendsResponse) {
        myFriendsAdapter.setNewData(myFriendsResponse.getList());
    }

    @Override
    public void getLikeSuccess(String msg) {
        showToast(msg);
        listBean.isLike = true;
        myFriendsAdapter.notifyDataSetChanged();
        //更新用户数据
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_HOME_INFO));
    }

    @Override
    public void commonDialogBtnOkListener() {

    }


    private void initializeInjector() {
        DaggerLoveMePeopleComponent.builder().appComponent(getAppComponent())
                .loveMePeopleModule(new LoveMePeopleModule(LoveMePeopleActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);

    }


}
