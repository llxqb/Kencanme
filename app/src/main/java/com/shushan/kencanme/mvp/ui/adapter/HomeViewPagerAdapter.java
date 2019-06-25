package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;
import com.shushan.kencanme.mvp.views.CircleImageView;

import java.util.List;

import cn.jzvd.JzvdStd;

/**
 * ClassName: ViewAdapter
 * Desciption: //homeFragment viewpager
 */
public class HomeViewPagerAdapter extends PagerAdapter {
    private List<HomeFragmentResponse.ListBean> mViewPagerResponseList;
    Context mContext;
    private CircleImageView mRecommendUserHeadIv;
    private TextView mRecommendUserName;
    private TextView mRecommendUserSexYear;
    private TextView mRecommendUserLocation;
    private TextView mActiveTime;
    private ImageView homeLikeIv;
    private ImageLoaderHelper mImageLoaderHelper;
    private HomeViewPagerListener mHomeViewPagerListener;
    private int currentPos;

    public HomeViewPagerAdapter(Context context, List<HomeFragmentResponse.ListBean> viewPagerResponseList, ImageLoaderHelper imageLoaderHelper, HomeViewPagerListener homeViewPagerListener) {
        mViewPagerResponseList = viewPagerResponseList;
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        mHomeViewPagerListener = homeViewPagerListener;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //用这个才能刷新，这里可以优化（后期）
        return POSITION_NONE;
//        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return mViewPagerResponseList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View viewGroup = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item_layout, null);
        HomeFragmentResponse.ListBean listBean = mViewPagerResponseList.get(position);
        ImageView mViewpagerItemIv = viewGroup.findViewById(R.id.viewpager_item_iv);
        mRecommendUserHeadIv = viewGroup.findViewById(R.id.recommend_user_head_iv);
        mRecommendUserName = viewGroup.findViewById(R.id.recommend_user_name);
        mRecommendUserSexYear = viewGroup.findViewById(R.id.recommend_user_sex_year);
        mRecommendUserLocation = viewGroup.findViewById(R.id.recommend_user_location);
        mActiveTime = viewGroup.findViewById(R.id.active_time);
        RelativeLayout mRecommendUserRl = viewGroup.findViewById(R.id.recommend_user_rl);
        JzvdStd mJzVideo = viewGroup.findViewById(R.id.jz_video);
        homeLikeIv = viewGroup.findViewById(R.id.home_like_iv);
        ImageView homeMessageIv = viewGroup.findViewById(R.id.home_message_iv);
        if (TranTools.isVideo(listBean.getCover())) {
            //视频
            mJzVideo.setVisibility(View.VISIBLE);
            mViewpagerItemIv.setVisibility(View.GONE);
            mJzVideo.setUp(listBean.getCover(), "");
            PicUtils.loadVideoScreenshot(mContext, listBean.getCover(), mJzVideo.thumbImageView, 0);
        } else {
            mJzVideo.setVisibility(View.GONE);
            mViewpagerItemIv.setVisibility(View.VISIBLE);
            mImageLoaderHelper.displayMatchImage(mContext, listBean.getCover(), mViewpagerItemIv, Constant.LOADING_BIG);
        }

        if (listBean.getIs_like() == 1) {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_like, homeLikeIv, R.mipmap.home_liked);
        }else {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_liked, homeLikeIv, R.mipmap.home_liked);
        }

        homeLikeIv.setOnClickListener(v -> {
            if (mHomeViewPagerListener != null) {
                //0为不喜欢  1喜欢
                if (listBean.getIs_like() == 0) {
                    currentPos = position;
//                    mImageLoaderHelper.displayImage(mContext, R.mipmap.home_like, homeLikeIv, R.mipmap.home_liked);
                    mHomeViewPagerListener.goLike(listBean.getUid());
                }
            }
        });
        homeMessageIv.setOnClickListener(v -> {
            if (mHomeViewPagerListener != null) {
                mHomeViewPagerListener.goChat(listBean.getRongyun_userid(),listBean.getNickname());
            }
        });
        mRecommendUserRl.setOnClickListener(v -> {
            if (mHomeViewPagerListener != null) {
                mHomeViewPagerListener.goRecommendUser();
            }
        });
        setUserData(listBean);
        container.addView(viewGroup);
        return viewGroup;
    }

    /**
     * 设置推荐人信息
     */
    private void setUserData(HomeFragmentResponse.ListBean listBean) {
        mRecommendUserName.setText(listBean.getNickname());
        mImageLoaderHelper.displayImage(mContext, listBean.getTrait(), mRecommendUserHeadIv, Constant.LOADING_SMALL);
        if (listBean.getSex() == 1) {
            //1男2女
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mRecommendUserSexYearValue = listBean.getAge() + " "+mContext.getResources().getString(R.string.HomeViewPagerAdapter_years);
        String mActiveTimeValue = mContext.getResources().getString(R.string.HomeViewPagerAdapter_active) + " "+listBean.getActive_time() + " "+mContext.getResources().getString(R.string.HomeViewPagerAdapter_minute_ago);
        mRecommendUserSexYear.setText(mRecommendUserSexYearValue);
        mRecommendUserLocation.setText(listBean.getCity());
        mActiveTime.setText(mActiveTimeValue);
        if (listBean.getIs_like() == 0) {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_liked, homeLikeIv, R.mipmap.home_liked);
        } else {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.home_like, homeLikeIv, R.mipmap.home_like);
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface HomeViewPagerListener {
        void goLike(int uId);

        void goChat(String  rongYunId,String nickName);

        void goRecommendUser();
    }


    public void setLikeImg() {
        mViewPagerResponseList.get(currentPos).setIs_like(1);
        notifyDataSetChanged();
    }
}
