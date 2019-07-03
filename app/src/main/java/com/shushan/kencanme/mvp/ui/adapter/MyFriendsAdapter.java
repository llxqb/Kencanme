package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.views.CircleImageView;

import java.util.List;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class MyFriendsAdapter extends BaseQuickAdapter<MyFriendsResponse.ListBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public MyFriendsAdapter(Context context, @Nullable List<MyFriendsResponse.ListBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_my_friends, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyFriendsResponse.ListBean item) {
        helper.addOnClickListener(R.id.my_friends_rl).addOnClickListener(R.id.friends_avator_iv);
        CircleImageView imageView = helper.getView(R.id.friends_avator_iv);
        mImageLoaderHelper.displayImage(mContext, item.getTrait(), imageView, Constant.LOADING_AVATOR);
        helper.setText(R.id.friends_nick_tv, item.getNickname());
        helper.setText(R.id.friends_year_tv, item.getAge() + " "+mContext.getResources().getString(R.string.HomeViewPagerAdapter_years));
        if (item.getSex() == 1) {
            helper.setBackgroundRes(R.id.friends_year_tv, R.mipmap.home_gender_male);
        } else if (item.getSex() == 2) {
            helper.setBackgroundRes(R.id.friends_year_tv, R.mipmap.home_gender_female);
        }
    }

}
