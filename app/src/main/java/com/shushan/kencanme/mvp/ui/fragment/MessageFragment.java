package com.shushan.kencanme.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.mvp.ui.fragment.message.MessageFragment2;
import com.shushan.kencanme.mvp.ui.fragment.message.MyFriendsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MessageFragment
 * 消息
 */

public class MessageFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.message_viewpager)
    ViewPager mMessageViewpager;
    Unbinder unbinder;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mMessageViewpager.setOffscreenPageLimit(2);
        mMessageViewpager.setAdapter(new MyPageAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mMessageViewpager);
    }

    @Override
    public void initData() {

    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private final String[] titles = {"Message", "My friend"};
        private List<Fragment> fragments = new ArrayList<Fragment>();

         MyPageAdapter(FragmentManager fm) {
            super(fm);
            MessageFragment2 messageFragment2 = new MessageFragment2();
            MyFriendsFragment myFriendsFragment = new MyFriendsFragment();
            fragments.add(messageFragment2);
            fragments.add(myFriendsFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
