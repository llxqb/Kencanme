package com.shushan.kencanme.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseFragment;

/**
 * HomeFragment
 * 首页
 */

public class MessageFragment extends BaseFragment {


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }



}
