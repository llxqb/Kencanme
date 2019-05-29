package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ViewAdapter
 * Desciption: //homeFragment viewpager
 */
public class HomeViewPagerAdapter extends PagerAdapter {
    Context mContext;
    private List<ViewGroup> mViewPagerResponseList = new ArrayList<>();

    public HomeViewPagerAdapter(Context context, List<ViewGroup> viewPagerResponseList) {
        mContext = context;
        mViewPagerResponseList = viewPagerResponseList;
    }

    @Override
    public int getCount() {
        return mViewPagerResponseList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViewPagerResponseList.get(position);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item_layout, null);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewPagerResponseList.get(position));
    }
}
