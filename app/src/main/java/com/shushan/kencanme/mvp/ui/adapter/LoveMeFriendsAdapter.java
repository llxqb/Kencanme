package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.utils.DateUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;

import java.util.List;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class LoveMeFriendsAdapter extends BaseQuickAdapter<MyFriendsResponse.ListBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public LoveMeFriendsAdapter(Context context, @Nullable List<MyFriendsResponse.ListBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_love_me_friends, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyFriendsResponse.ListBean item) {
        helper.addOnClickListener(R.id.like_iv);
        CircleImageView imageView = helper.getView(R.id.friends_avator_iv);
        ImageView likeIv = helper.getView(R.id.like_iv);
        mImageLoaderHelper.displayImage(mContext, item.getTrait(), imageView, Constant.LOADING_AVATOR);
        helper.setText(R.id.friends_nick_tv, item.getNickname());
        helper.setText(R.id.friends_year_tv, item.getAge() + mContext.getResources().getString(R.string.HomeViewPagerAdapter_years));
        if (item.getSex() == 1) {
            helper.setBackgroundRes(R.id.friends_year_tv, R.mipmap.home_gender_male);
        } else if (item.getSex() == 2) {
            helper.setBackgroundRes(R.id.friends_year_tv, R.mipmap.home_gender_female);
        }
        helper.setText(R.id.love_me_time, DateUtil.getStrTime(item.getCreate_time(), "MM-dd  HH:mm") + mContext.getResources().getString(R.string.LoveMeFriendsAdapter_like_you));

        if (item.isLike) {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.message_like, likeIv, R.mipmap.message_like);
            helper.setText(R.id.like_tv, mContext.getResources().getString(R.string.LoveMeFriendsAdapter_like));
            helper.setTextColor(R.id.like_tv, mContext.getResources().getColor(R.color.red_color_btn));
        } else {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.message_liked, likeIv, R.mipmap.message_liked);
            helper.setText(R.id.like_tv, mContext.getResources().getString(R.string.LoveMeFriendsAdapter_liked));
            helper.setTextColor(R.id.like_tv, mContext.getResources().getColor(R.color.color_80));
        }

    }

}
