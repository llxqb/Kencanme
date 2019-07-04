package com.shushan.kencanme.mvp.ui.activity.loveMe;

import android.app.Dialog;
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
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.LikeRequest;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.request.RequestFreeChat;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.ui.adapter.LoveMeFriendsAdapter;
import com.shushan.kencanme.mvp.utils.AppUtils;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.MyTimer;
import com.shushan.kencanme.mvp.views.dialog.MatchSuccessDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * 查看喜欢我的人
 */
public class LoveMePeopleActivity extends BaseActivity implements LoveMePeopleControl.LoveMePeopleView, CommonDialog.CommonDialogListener, MatchSuccessDialog.MatchSuccessListener,
        MyTimer.MyTimeListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonIvRight;
    @BindView(R.id.love_me_recycler_view)
    RecyclerView mLoveMeRecyclerView;

    private LoveMeFriendsAdapter myFriendsAdapter;
    private List<MyFriendsResponse.ListBean> listBeanList = new ArrayList<>();
    private LoginUser mLoginUser;
    private MyFriendsResponse.ListBean listBean;
    private Dialog likeDialog;
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
                            likeDialog = DialogFactory.showLikeDialog(LoveMePeopleActivity.this);
                            likeDialog.show();
                            setmRemainTime();
                            if (AppUtils.isLimitLike(mLoginUser.userType, mLoginUser.today_like)) {
                                LikeRequest likeRequest = new LikeRequest();
                                likeRequest.token = mBuProcessor.getToken();
                                assert listBean != null;
                                likeRequest.likeid = listBean.getUid();
                                mPresenter.onRequestLike(likeRequest);
                            } else {
                                DialogFactory.showOpenVipDialog(LoveMePeopleActivity.this, getResources().getString(R.string.dialog_open_vip_like));
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
                onBackPressed();
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
        showMatchSuccesDialog();
    }

    /**
     * 显示匹配成功弹框
     */
    private void showMatchSuccesDialog() {
        MatchSuccessDialog matchSuccessDialog = MatchSuccessDialog.newInstance();
        matchSuccessDialog.setListener(this);
        matchSuccessDialog.setContent(mLoginUser.nickname,mLoginUser.trait,listBean.getNickname(),listBean.getTrait());
        DialogFactory.showDialogFragment(this.getSupportFragmentManager(), matchSuccessDialog, MatchSuccessDialog.TAG);
    }

    @Override
    public void startChatBtnListener() {
        //查看喜欢我的人都是超级VIP  没进行弹框提示   记录了统计今日密聊次数
        if (AppUtils.isLimitMsg(mLoginUser.userType, mLoginUser.today_chat)) {
            RequestFreeChat requestFreeChat = new RequestFreeChat();
            requestFreeChat.token = mBuProcessor.getToken();
            requestFreeChat.secret_id = String.valueOf(listBean.getUid());
            mPresenter.onRequestChatNum(requestFreeChat);
        }
//        else {
//            DialogFactory.showOpenVipDialog(this, this, getResources().getString(R.string.dialog_open_vip_chat));
//        }
    }

    @Override
    public void chatNumSuccess() {
        //启动单聊页面
        RongIM.getInstance().startPrivateChat(this, listBean.getRongyun_userid(), listBean.getNickname());
        finish();
    }

    @Override
    public void commonDialogBtnOkListener() {
        startActivitys(OpenVipActivity.class);
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

    @Override
    public void onBackPressed() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_MESSAGE_INFO));
        super.onBackPressed();
    }

    private void initializeInjector() {
        DaggerLoveMePeopleComponent.builder().appComponent(getAppComponent())
                .loveMePeopleModule(new LoveMePeopleModule(LoveMePeopleActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
