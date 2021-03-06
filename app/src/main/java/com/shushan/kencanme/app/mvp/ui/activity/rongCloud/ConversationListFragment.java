package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerConversationListFragmentComponent;
import com.shushan.kencanme.app.di.modules.ConversationListFragmentModule;
import com.shushan.kencanme.app.di.modules.MainModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseFragment;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.response.SystemMsgNewResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.loveMe.LoveMePeopleActivity;
import com.shushan.kencanme.app.mvp.ui.activity.vip.OpenVipActivity;
import com.shushan.kencanme.app.mvp.utils.AppUtils;
import com.shushan.kencanme.app.mvp.utils.DateUtil;
import com.shushan.kencanme.app.mvp.views.CircleImageView;
import com.shushan.kencanme.app.mvp.views.CommonDialog;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversitionlist_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        io.rong.imkit.fragment.ConversationListFragment mConversationListFragment = (io.rong.imkit.fragment.ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + Objects.requireNonNull(getActivity()).getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        assert mConversationListFragment != null;
        mConversationListFragment.setUri(uri);
        mConversationListFragment.onRestoreUI();
        initView();
        initData();
        return view;
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.UPDATE_MESSAGE_INFO)) {
            requestSystemMsgNew();
        } else if (intent.getAction() != null && intent.getAction().equals(ActivityConstant.PAY_SUCCESS_UPDATE_INFO)) {
            // 充值后更新
            mLoginUser = mBuProcessor.getLoginUser();
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_MESSAGE_INFO);
        mFilter.addAction(ActivityConstant.PAY_SUCCESS_UPDATE_INFO);
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
//        requestSystemMsgNew();
    }

    /**
     * 请求系统消息和最新匹配信息
     */
    private void requestSystemMsgNew() {
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
//                startActivitys(LoveMePeopleActivity.class);
                if (mLoginUser.userType == 3) {
//                    startActivitys(LoveMePeopleActivity.class);
                    LoveMePeopleActivity.start(getActivity(),"2");
                } else {
                    showSuperVipDialog();
                }
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
        DialogFactory.showOpenVipDialogFragment(getActivity(), this, getResources().getString(R.string.dialog_open_vip_chat_like));
    }

    @Override
    public void getSystemMsgNewInfoSuccess(SystemMsgNewResponse systemMsgNewResponse) {
        SystemMsgNewResponse.NewLikeBean likeBean = systemMsgNewResponse.getNew_like();
        SystemMsgNewResponse.NewMessageBean messageBean = systemMsgNewResponse.getNew_message();
//        LogUtils.e("LogInterceptor:" + "messageBean:" + new Gson().toJson(messageBean));
        if (likeBean != null && likeBean.getCount() != 0) {
            mNewPairingRl.setVisibility(View.VISIBLE);
            mLineView.setVisibility(View.VISIBLE);
            if (AppUtils.userType(mLoginUser.svip, mLoginUser.vip, mLoginUser.sex) == 3) {
                mImageLoaderHelper.displayImage(getActivity(), likeBean.getTrait(), mNewPairingIv, Constant.LOADING_AVATOR);
            } else {
                mImageLoaderHelper.displayGlassImage(getActivity(), likeBean.getTrait(), mNewPairingIv, Constant.LOADING_AVATOR);
            }
            mNewPairingNumTv.setText(String.valueOf(likeBean.getCount()));
            String mNewPairingTvValue = getResources().getString(R.string.ConversationListFragment_add) + " <font color = '#FF2D5B'>" + likeBean.getCount() + "</font> " + getResources().getString(R.string.ConversationListFragment_new_like_people_hint);
            mNewPairingTv.setText(Html.fromHtml(mNewPairingTvValue));
        } else {
            mNewPairingRl.setVisibility(View.GONE);
            mLineView.setVisibility(View.GONE);
        }
        if (messageBean != null) {
            if (!TextUtils.isEmpty(messageBean.getTitle())) {
                mSystemMsgTv.setText(messageBean.getTitle());
            }
            if (!TextUtils.isEmpty(messageBean.getDetail())) {
                mSystemMsgHintTv.setText(messageBean.getDetail());
            }
            if (messageBean.getCreate_time() != 0) {
                mSystemMsgTimeTv.setText(DateUtil.getStrTime(messageBean.getCreate_time(), "MM-dd"));
            }
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
