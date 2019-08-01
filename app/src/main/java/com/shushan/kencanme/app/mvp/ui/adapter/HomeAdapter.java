package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.app.help.ImageLoaderHelper;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.utils.TranTools;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class HomeAdapter extends BaseQuickAdapter<HomeFragmentResponse.ListBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;
    List<HomeFragmentResponse.ListBean> mData;

    public HomeAdapter(Context context, @Nullable List<HomeFragmentResponse.ListBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.viewpager_item_layout, data);
        mData = data;
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);//播放填充满背景，不带黑色背景
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List payloads) {
//        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            String payload = payloads.get(0).toString();
            ImageView homeLikeIv = holder.getView(R.id.home_like_iv);
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_like, homeLikeIv, R.mipmap.home_like);
        }
    }


    @Override
    protected void convert(BaseViewHolder helper, HomeFragmentResponse.ListBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.home_like_iv).addOnClickListener(R.id.home_message_iv).addOnClickListener(R.id.recommend_user_rl).addOnClickListener(R.id.home_item_rl);
        JzvdStd mJzVideo = helper.getView(R.id.jz_video);
        ImageView mViewpagerItemIv = helper.getView(R.id.viewpager_item_iv);
        ImageView homeLikeIv = helper.getView(R.id.home_like_iv);
        ImageView mRecommendUserHeadIv = helper.getView(R.id.recommend_user_head_iv);
        if (TranTools.isVideo(item.getCover())) {
            //视频
            helper.setVisible(R.id.jz_video, true);
            helper.setVisible(R.id.viewpager_item_iv, false);
            mJzVideo.setUp(item.getCover(), "");
            PicUtils.loadVideoScreenshot(mContext, item.getCover(), mJzVideo.thumbImageView, 0);
        } else {
            mJzVideo.setVisibility(View.GONE);
            helper.setVisible(R.id.viewpager_item_iv, true);
            mImageLoaderHelper.displayMatchImage(mContext, item.getCover(), mViewpagerItemIv, Constant.LOADING_BIG);
        }

        helper.setText(R.id.recommend_user_name, item.getNickname());
        mImageLoaderHelper.displayImage(mContext, item.getTrait(), mRecommendUserHeadIv, Constant.LOADING_SMALL);
        if (item.getSex() == 1) {
            //1男2女
            helper.setBackgroundRes(R.id.recommend_user_sex_year, R.mipmap.message_gender_male);
        } else {
            helper.setBackgroundRes(R.id.recommend_user_sex_year, R.mipmap.message_gender_female);
        }
        String mRecommendUserSexYearValue = item.getAge() + " " + mContext.getResources().getString(R.string.HomeViewPagerAdapter_years);

        if (item.getActive_time() > 0) {
            helper.setVisible(R.id.active_time, true);
            String mActiveTimeValue = mContext.getResources().getString(R.string.HomeViewPagerAdapter_active) + " " + item.getActive_time() + " " + mContext.getResources().getString(R.string.HomeViewPagerAdapter_minute_ago);
            helper.setText(R.id.active_time, mActiveTimeValue);
        } else {
            helper.setVisible(R.id.active_time, false);
        }

        helper.setText(R.id.recommend_user_sex_year, mRecommendUserSexYearValue)
                .setText(R.id.recommend_user_location, item.getCity());
        if (item.getRelation() == 0) {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_liked, homeLikeIv, R.mipmap.home_liked);
        } else {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_like, homeLikeIv, R.mipmap.home_like);
        }

    }

}
