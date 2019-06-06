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
 * 消息
 */

public class MessageFragment2 extends BaseFragment {


    public static MessageFragment2 newInstance() {
        return new MessageFragment2();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message2, container, false);
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
