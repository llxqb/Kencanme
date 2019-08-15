package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.response.MyAlbumResponse;
import com.shushan.kencanme.app.help.ImageLoaderHelper;
import com.shushan.kencanme.app.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;
import com.shushan.kencanme.app.mvp.utils.PicUtils;
import com.shushan.kencanme.app.mvp.utils.TranTools;
import com.shushan.kencanme.app.mvp.views.CornerLabelView;
import com.shushan.kencanme.app.mvp.views.MyJzvdStd;

import java.util.List;

import cn.jzvd.Jzvd;

/**
 * Mime/RecommendUserInfoActivity  页面 MyAlbumAdapter
 */
public class AlbumAdapter extends BaseQuickAdapter<MyAlbumResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public AlbumAdapter(Context context, @Nullable List<MyAlbumResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_album, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);//播放填充满背景，不带黑色背景
    }


    @Override
    protected void convert(BaseViewHolder helper, MyAlbumResponse.DataBean item) {
        helper.addOnClickListener(R.id.photo_item_rl);
        ImageView imageView = helper.getView(R.id.photo_iv);
        MyJzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
        CornerLabelView mCornerLabelView = helper.getView(R.id.cornerLabelView);

        if (item != null) {
            if (!TextUtils.isEmpty(item.getAlbum_url())) {
                helper.setVisible(R.id.photo_hint_tv, true);
                if (TranTools.isVideo(item.getAlbum_url())) {
                    //video
                    helper.setVisible(R.id.album_jz_video, true);
                    helper.setVisible(R.id.photo_iv, false);
                    //是否显示审核状态角标
                    if (item.getState() == 0) {
                        mCornerLabelView.setVisibility(View.GONE);
                    } else if (item.getState() == 1) {
                        mCornerLabelView.setVisibility(View.VISIBLE);
                        mCornerLabelView.setText1(mContext.getResources().getString(R.string.video_review));
                        mCornerLabelView.setFillColor(mContext.getResources().getColor(R.color.app_color));
                    } else if (item.getState() == 2) {
                        mCornerLabelView.setVisibility(View.VISIBLE);
                        mCornerLabelView.setText1(mContext.getResources().getString(R.string.video_review_no_passed));
                        mCornerLabelView.setFillColor(mContext.getResources().getColor(R.color.red_color_btn));
                    }

                    switch (item.getAlbum_type()) {
                        case 1:
                            helper.setVisible(R.id.photo_hint_tv, false);
                            jzvdStd.setUp(item.getAlbum_url(), "");
                            PicUtils.loadVideoScreenshot(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, 0,true);  //获取视频第一帧显示
                            break;
                        case 2:
                            helper.setVisible(R.id.photo_hint_tv, true);
                            helper.setText(R.id.photo_hint_tv, mContext.getString(R.string.AlbumAdapter_vip_can_Video));
                            if (mContext instanceof RecommendUserInfoActivity) {
                                //设置毛玻璃效果
                                jzvdStd.setUp(item.getAlbum_url(), "");
                                mImageLoaderHelper.displayGlassImage(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, Constant.LOADING_SMALL);
                            } else {
                                //显示第一帧
                                jzvdStd.setUp(item.getAlbum_url(), "");
                                PicUtils.loadVideoScreenshot(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, 0,true);  //获取视频第一帧显示
                            }
                            break;
                        case 3:
                            helper.setVisible(R.id.photo_hint_tv, true);
                            helper.setText(R.id.photo_hint_tv, mContext.getString(R.string.AlbumAdapter_Beans_video));
                            if (mContext instanceof RecommendUserInfoActivity && item.getLookStatus() == 0) {
                                jzvdStd.setUp(item.getAlbum_url(), "");
                                mImageLoaderHelper.displayGlassImage(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, Constant.LOADING_SMALL);
                            } else {
                                jzvdStd.setUp(item.getAlbum_url(), "");
                                PicUtils.loadVideoScreenshot(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, 0,true);  //获取视频第一帧显示
                            }
                            break;
                    }
                } else {
                    //photo
                    mCornerLabelView.setVisibility(View.GONE);
                    helper.setVisible(R.id.album_jz_video, false);
                    helper.setVisible(R.id.photo_iv, true);
                    switch (item.getAlbum_type()) {
                        case 1:
                            helper.setVisible(R.id.photo_hint_tv, false);
                            mImageLoaderHelper.displayImage(mContext, item.getAlbum_url(), imageView, Constant.LOADING_SMALL);
                            break;
                        case 2:
                            helper.setVisible(R.id.photo_hint_tv, true);
                            helper.setText(R.id.photo_hint_tv, mContext.getString(R.string.AlbumAdapter_vip_can_view));
                            if (mContext instanceof RecommendUserInfoActivity) {
                                mImageLoaderHelper.displayGlassImage(mContext, item.getAlbum_url(), imageView, Constant.LOADING_SMALL);
                            } else {
                                mImageLoaderHelper.displayImage(mContext, item.getAlbum_url(), imageView, Constant.LOADING_SMALL);
                            }
                            break;
                        case 3:
                            helper.setVisible(R.id.photo_hint_tv, true);
                            helper.setText(R.id.photo_hint_tv, mContext.getString(R.string.AlbumAdapter_Beans_photo));
                            if (mContext instanceof RecommendUserInfoActivity && item.getLookStatus() == 0) {
                                mImageLoaderHelper.displayGlassImage(mContext, item.getAlbum_url(), imageView, Constant.LOADING_SMALL);
                            } else {
                                mImageLoaderHelper.displayImage(mContext, item.getAlbum_url(), imageView, Constant.LOADING_SMALL);
                            }
                            break;
                    }
                }
            } else {
                helper.setVisible(R.id.photo_delete, false);
                helper.setVisible(R.id.album_jz_video, false);
                helper.setVisible(R.id.photo_hint_tv, false);
                helper.setVisible(R.id.photo_iv, true);
                mImageLoaderHelper.displayImage(mContext, R.mipmap.report_add_photo, imageView, R.mipmap.report_add_photo);//加载第一张+图片
            }
        }
    }

}
