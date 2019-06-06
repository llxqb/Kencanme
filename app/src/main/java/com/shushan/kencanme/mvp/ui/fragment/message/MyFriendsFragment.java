package com.shushan.kencanme.mvp.ui.fragment.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseFragment;

/**
 * MessageFragment2
 * 朋友列表
 */

public class MyFriendsFragment extends BaseFragment {


    public static MyFriendsFragment newInstance() {
        return new MyFriendsFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }

}
