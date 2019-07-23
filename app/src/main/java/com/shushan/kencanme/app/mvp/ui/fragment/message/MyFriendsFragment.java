package com.shushan.kencanme.app.mvp.ui.fragment.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerMyFriendsFragmentComponent;
import com.shushan.kencanme.app.di.modules.MainModule;
import com.shushan.kencanme.app.di.modules.MyFriendsFragmentModule;
import com.shushan.kencanme.app.entity.base.BaseFragment;
import com.shushan.kencanme.app.entity.request.MyFriendsRequest;
import com.shushan.kencanme.app.entity.response.MyFriendsResponse;
import com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.MyFriendsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;

/**
 * MessageFragment2
 * 朋友列表
 */

public class MyFriendsFragment extends BaseFragment implements MyFriendsFragmentControl.MyFriendsView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLy;
    @BindView(R.id.my_friends_recycler_view)
    RecyclerView mMyFriendsRecyclerView;
    Unbinder unbinder;

    MyFriendsAdapter myFriendsAdapter = null;
    List<MyFriendsResponse.ListBean> listBeanList = new ArrayList<>();
    @Inject
    MyFriendsFragmentControl.MyFriendsFragmentPresenter mPresenter;
    private View mEmptyView;

    public static MyFriendsFragment newInstance() {
        return new MyFriendsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mSwipeLy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeLy.setOnRefreshListener(this);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.no_friends_layout, (ViewGroup) mMyFriendsRecyclerView.getParent(), false);
        mMyFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myFriendsAdapter = new MyFriendsAdapter(getActivity(), listBeanList, mImageLoaderHelper);
        mMyFriendsRecyclerView.setAdapter(myFriendsAdapter);
        myFriendsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            MyFriendsResponse.ListBean listBean = (MyFriendsResponse.ListBean) adapter.getItem(position);
            assert listBean != null;
            switch (view.getId()) {
                case R.id.my_friends_rl:
                    //跳到聊天
                    //启动单聊页面
                    RongIM.getInstance().startPrivateChat(Objects.requireNonNull(getActivity()), listBean.getRongyun_userid(), listBean.getNickname());
                    break;
                case R.id.friends_avator_iv:
                    //跳到好友详情
                    RecommendUserInfoActivity.start(getActivity(), listBean.getUid());
                    break;
            }
        });
    }

    @Override
    public void initData() {
        reqFriendList();
    }

    /**
     * 查找我的朋友列表
     */
    private void reqFriendList() {
        MyFriendsRequest myFriendsRequest = new MyFriendsRequest();
        myFriendsRequest.token = mBuProcessor.getToken();
        myFriendsRequest.type = "1";
        mPresenter.onRequestMyFriendList(myFriendsRequest);
    }

    @Override
    public void getMyFriendsListInfoSuccess(MyFriendsResponse myFriendsResponse) {
        //没做分页可以这样表示
        if (mSwipeLy.isRefreshing()) {
            mSwipeLy.setRefreshing(false);
        }
        if (myFriendsResponse.getList().size() == 0) {
            myFriendsAdapter.setEmptyView(mEmptyView);
        } else {
            myFriendsAdapter.setNewData(myFriendsResponse.getList());
        }
    }

    @Override
    public void onRefresh() {
        reqFriendList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initializeInjector() {
        DaggerMyFriendsFragmentComponent.builder().appComponent(((KencanmeApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .myFriendsFragmentModule(new MyFriendsFragmentModule(this))
                .build().inject(this);
    }


}
