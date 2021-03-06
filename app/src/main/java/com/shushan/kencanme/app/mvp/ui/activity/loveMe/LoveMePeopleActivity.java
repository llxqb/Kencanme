package com.shushan.kencanme.app.mvp.ui.activity.loveMe;

import android.app.Dialog;
import android.content.Context;
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
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerLoveMePeopleComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.LoveMePeopleModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.LikeRequest;
import com.shushan.kencanme.app.entity.request.MyFriendsRequest;
import com.shushan.kencanme.app.entity.request.RequestFreeChat;
import com.shushan.kencanme.app.entity.response.MyFriendsResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.LoveMeFriendsAdapter;
import com.shushan.kencanme.app.mvp.utils.AppUtils;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.views.CommonDialog;
import com.shushan.kencanme.app.mvp.views.dialog.MatchSuccessDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * 查看喜欢我的人
 */
public class LoveMePeopleActivity extends BaseActivity implements LoveMePeopleControl.LoveMePeopleView, CommonDialog.CommonDialogListener, MatchSuccessDialog.MatchSuccessListener {

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
    private String reqType;
    @Inject
    LoveMePeopleControl.PresenterLoveMePeople mPresenter;

    public static void start(Context context, String reqType) {
        Intent intent = new Intent(context, LoveMePeopleActivity.class);
        intent.putExtra("reqType", reqType);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_me_people);
        StatusBarUtil.setTransparentForImageView(this, null);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.PAY_SUCCESS_UPDATE_INFO)) {
            //充值后更新
            mLoginUser = mBuProcessor.getLoginUser();
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.PAY_SUCCESS_UPDATE_INFO);
    }

    @Override
    public void initView() {
        mLoginUser = mBuProcessor.getLoginUser();
        mCommonTitleTv.setText(getResources().getString(R.string.LoveMePeopleActivity_title));
        mCommonIvRight.setVisibility(View.INVISIBLE);
        mLoveMeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myFriendsAdapter = new LoveMeFriendsAdapter(this, listBeanList, mImageLoaderHelper);
        mLoveMeRecyclerView.setAdapter(myFriendsAdapter);
        mLoveMeRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                listBean = (MyFriendsResponse.ListBean) adapter.getItem(position);
                if (view.getId() == R.id.like_iv) {
                    if (!listBean.isLike) {
                        likeDialog = DialogFactory.showLikeDialog(LoveMePeopleActivity.this);
                        likeDialog.show();
                        setLikeRemainTime();
                        //能进来就是超级vip,超级VIP可以不用判断了
//                            if (AppUtils.isLimitLike(mLoginUser.userType, mLoginUser.today_like)) {
//                            } else {
//                                DialogFactory.showOpenVipDialog(LoveMePeopleActivity.this, getResources().getString(R.string.dialog_open_vip_like));
//                            }
                        LikeRequest likeRequest = new LikeRequest();
                        likeRequest.token = mBuProcessor.getToken();
                        assert listBean != null;
                        likeRequest.likeid = listBean.getUid();
                        mPresenter.onRequestLike(likeRequest);
                    }
                }
            }
        });
    }

    TimerTask task; //将原任务从队列中移除

    /**
     * 设置喜欢动画倒计时
     */
    private void setLikeRemainTime() {
        task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    likeDialog.dismiss();
                    if (task != null) {
                        task.cancel();  //将原任务从队列中移除
                    }
                });
            }
        };
        new Timer().schedule(task, 600);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            reqType = getIntent().getStringExtra("reqType");
        }
        MyFriendsRequest myFriendsRequest = new MyFriendsRequest();
        myFriendsRequest.token = mBuProcessor.getToken();
        myFriendsRequest.type = reqType;//1好友 2最近喜欢  3所有喜欢你的人
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
        matchSuccessDialog.setContent(mLoginUser.nickname, mLoginUser.trait, listBean.getNickname(), listBean.getTrait());
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
