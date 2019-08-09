package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import pl.droidsonroids.gif.GifImageView;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class HomeAdapter extends BaseQuickAdapter<HomeFragmentResponse.ListBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;
    private List<HomeFragmentResponse.ListBean> mData;

    public HomeAdapter(Context context, @Nullable List<HomeFragmentResponse.ListBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.viewpager_item_layout, data);
        mData = data;
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);//播放填充满背景，不带黑色背景
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List payloads) {
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
        GifImageView gifImageView = helper.getView(R.id.app_loading);
        helper.setVisible(R.id.app_loading, true);
        helper.setVisible(R.id.jz_video, false);
        helper.setVisible(R.id.viewpager_item_iv, false);

        String coverValue = item.getCover();

        if (TranTools.isVideo(coverValue)) {
            //视频
            helper.setVisible(R.id.jz_video, true);
            helper.setVisible(R.id.viewpager_item_iv, false);
            mJzVideo.setUp(coverValue, "");
            PicUtils.loadVideoScreenshot(mContext, coverValue, mJzVideo.thumbImageView, 0, false);
        } else {
            mJzVideo.setVisibility(View.GONE);
            helper.setVisible(R.id.viewpager_item_iv, true);
            mImageLoaderHelper.displayMatchImage(mContext, coverValue, mViewpagerItemIv, Constant.LOADING_BIG);
        }


        Glide.with(mContext)
                .load(coverValue)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        assert e != null;
//                        ToastUtil.showToast(mContext, mContext.getResources().getString(R.string.image_fail));
                        gifImageView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        gifImageView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(mViewpagerItemIv);


        String nickName = item.getNickname().replace("\n", "");
        helper.setText(R.id.recommend_user_name, nickName);
        mImageLoaderHelper.displayImage(mContext, item.getTrait(), mRecommendUserHeadIv, Constant.LOADING_SMALL);
        if (item.getSex() == 1) {
            //1男2女
            helper.setBackgroundRes(R.id.recommend_user_sex_year, R.mipmap.message_gender_male);
        } else {
            helper.setBackgroundRes(R.id.recommend_user_sex_year, R.mipmap.message_gender_female);
        }
        String mRecommendUserSexYearValue = item.getAge() + " " + mContext.getResources().getString(R.string.HomeViewPagerAdapter_years);

        if (item.getActive_time() > 0 && item.getActive_time() <= 30) {
            helper.setVisible(R.id.active_time, true);
            String mActiveTimeValue = mContext.getResources().getString(R.string.HomeViewPagerAdapter_active) + " " + item.getActive_time() + " " + mContext.getResources().getString(R.string.HomeViewPagerAdapter_minute_ago);
            helper.setText(R.id.active_time, mActiveTimeValue);
        } else {
            helper.setVisible(R.id.active_time, false);
        }

        helper.setText(R.id.recommend_user_sex_year, mRecommendUserSexYearValue)
                .setText(R.id.recommend_user_location, item.getCity().replace("\n", ""));
        if (item.getRelation() == 0) {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_liked, homeLikeIv, R.mipmap.home_liked);
        } else {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_like, homeLikeIv, R.mipmap.home_like);
        }

    }

}
