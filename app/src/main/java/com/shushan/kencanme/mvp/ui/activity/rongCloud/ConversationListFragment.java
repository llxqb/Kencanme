package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerConversationListFragmentComponent;
import com.shushan.kencanme.di.modules.ConversationListFragmentModule;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.response.SystemMsgNewResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.loveMe.LoveMePeopleActivity;
import com.shushan.kencanme.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.mvp.utils.AppUtils;
import com.shushan.kencanme.mvp.utils.DateUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;
import com.shushan.kencanme.mvp.views.CommonDialog;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imlib.model.Conversation;

public class ConversationListFragment extends BaseFragment implements ConversationFragmentControl.ConversationView, CommonDialog.CommonDialogListener {

    @BindView(R.id.new_pairing_iv)
    CircleImageView mNewPairingIv;
    @BindView(R.id.new_pairing_tv)
    TextView mNewPairingTv;
    @BindView(R.id.new_pairing_num_tv)
    TextView mNewPairingNumTv;
    @BindView(R.id.new_pairing_rl)
    RelativeLayout mNewPairingRl;
    @BindView(R.id.line_view)
    View mLineView;
    @BindView(R.id.system_msg_tv)
    TextView mSystemMsgTv;
    @BindView(R.id.system_msg_hint_tv)
    TextView mSystemMsgHintTv;
    @BindView(R.id.system_msg_time_tv)
    TextView mSystemMsgTimeTv;
    @BindView(R.id.system_msg_rl)
    RelativeLayout mSystemMsgRl;
    Unbinder unbinder;
    LoginUser mLoginUser;
    @Inject
    ConversationFragmentControl.ConversationFragmentPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversitionlist_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        io.rong.imkit.fragment.ConversationListFragment fragment = (io.rong.imkit.fragment.ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
        initView();
        initData();
        return view;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        mLoginUser = mBuProcessor.getLoginUser();
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestSystemMsgNew(tokenRequest);
    }

    @OnClick({R.id.new_pairing_rl, R.id.system_msg_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.new_pairing_rl:
                //查看谁喜欢过我
                startActivitys(LoveMePeopleActivity.class);
//                if (AppUtils.userType(mLoginUser.svip, mLoginUser.vip, mLoginUser.sex) == 3) {
//                    //查看谁喜欢过我
//                    startActivitys(LoveMePeopleActivity.class);
//                } else {
//                    showSuperVipDialog();
//                }
                break;
            case R.id.system_msg_rl:
                startActivitys(SystemMsgActivity.class);
                break;
        }
    }

    /**
     * 提示开通超级vip
     */
    private void showSuperVipDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setListener(this);
        commonDialog.setContent("Buy Super VIP to see who likes you.");
        commonDialog.setStyle(Constant.DIALOG_TWO);
        DialogFactory.showDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    @Override
    public void getSystemMsgNewInfoSuccess(SystemMsgNewResponse systemMsgNewResponse) {
        Log.e("ddd", "systemMsgNewResponse:" + new Gson().toJson(systemMsgNewResponse));
        SystemMsgNewResponse.NewLikeBean likeBean = systemMsgNewResponse.getNew_like();
        SystemMsgNewResponse.NewMessageBean messageBean = systemMsgNewResponse.getNew_message();
        if (likeBean != null && likeBean.getCount() != 0) {
            if (AppUtils.userType(mLoginUser.svip, mLoginUser.vip, mLoginUser.sex) == 3) {
                mImageLoaderHelper.displayImage(getActivity(), likeBean.getTrait(), mNewPairingIv, Constant.LOADING_AVATOR);
            } else {
                mImageLoaderHelper.displayGlassImage(getActivity(), likeBean.getTrait(), mNewPairingIv, Constant.LOADING_AVATOR);
            }
            mNewPairingNumTv.setText(String.valueOf(likeBean.getCount()));
            String mNewPairingTvValue = "Add " + "<font color = '#FF2D5B'>" + likeBean.getCount() + "</font>" + " new people who like you";
            mNewPairingTv.setText(Html.fromHtml(mNewPairingTvValue));
        } else {
            mNewPairingRl.setVisibility(View.GONE);
            mLineView.setVisibility(View.GONE);
        }
        if (messageBean != null) {
            mSystemMsgTv.setText(messageBean.getTitle());
            mSystemMsgHintTv.setText(messageBean.getDetail());
            mSystemMsgTimeTv.setText(DateUtil.getStrTime(messageBean.getCreate_time(), "MM-dd"));
        }
    }

    @Override
    public void commonDialogBtnOkListener() {
        startActivitys(OpenVipActivity.class);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializeInjector() {
        DaggerConversationListFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .conversationListFragmentModule(new ConversationListFragmentModule(this))
                .build().inject(this);
    }


}
