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
import android.widget.RelativeLayout;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseFragment;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.ConversationListFragment;
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
    @BindView(R.id.tabLayout_rl)
    RelativeLayout mTabLayoutRl;
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
        mTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    mTabLayoutRl.setBackgroundResource(R.mipmap.message_top_left);

                } else {
                    mTabLayoutRl.setBackgroundResource(R.mipmap.message_top_right);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void initData() {

    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private final String[] titles = {getResources().getString(R.string.MessageFragment_message), getResources().getString(R.string.MessageFragment_My_friend)};
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            ConversationListFragment conversationListFragment = new ConversationListFragment();
            MyFriendsFragment myFriendsFragment = new MyFriendsFragment();
            fragments.add(conversationListFragment);
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
