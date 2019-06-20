package com.shushan.kencanme.mvp.ui.fragment.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerMyFriendsFragmentComponent;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.di.modules.MyFriendsFragmentModule;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.mvp.ui.adapter.MyFriendsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MessageFragment2
 * 朋友列表
 */

public class MyFriendsFragment extends BaseFragment implements MyFriendsFragmentControl.MyFriendsView {

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.no_friends_layout, (ViewGroup) mMyFriendsRecyclerView.getParent(), false);
        mMyFriendsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myFriendsAdapter = new MyFriendsAdapter(getActivity(), listBeanList, mImageLoaderHelper);
        mMyFriendsRecyclerView.setAdapter(myFriendsAdapter);
    }

    @Override
    public void initData() {
        MyFriendsRequest myFriendsRequest = new MyFriendsRequest();
        myFriendsRequest.token = mBuProcessor.getToken();
        myFriendsRequest.type = "1";
        mPresenter.onRequestMyFriendList(myFriendsRequest);
    }

    @Override
    public void getMyFriendsListInfoSuccess(MyFriendsResponse myFriendsResponse) {

        //没做分页可以这样表示
        if (myFriendsResponse.getList().size() == 0) {
            myFriendsAdapter.setEmptyView(mEmptyView);
        } else {
            myFriendsAdapter.setNewData(myFriendsResponse.getList());
        }
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
