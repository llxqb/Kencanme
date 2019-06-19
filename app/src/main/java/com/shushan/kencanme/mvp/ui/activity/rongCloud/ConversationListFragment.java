package com.shushan.kencanme.mvp.ui.activity.rongCloud;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseFragment;

import io.rong.imlib.model.Conversation;

public class ConversationListFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conversitionlist_fragment, container, false);
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
        initializeInjector();

        return view;
    }

    @Override
    public void initView() {
//        设置会话界面的功能
//        RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE,"2019061002","我的聊天");
//        RongIM.getInstance().startConversationList(getActivity(),);
//        RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE , targetId, title);
//        String rongId = mSharePreferenceUtil.getData("rongId");
        //同步与服务器信息
//        RongIM.setUserInfoProvider(new CustomerUserInfoProvider("Kencanme6", mBuProcessor.getLoginUser()), true);

    }

    @Override
    public void initData() {


    }

    private void initializeInjector() {
//        DaggerConversationListFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent()).
//                mainModule(new MainModule((AppCompatActivity) getActivity())).build().inject(this);
//        DaggerConverFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
//                .mainModule(new MainModule((AppCompatActivity) getActivity()))
//                .mineFragmentModule(new MineFragmentModule(this))
//                .build().inject(this);
    }

}
