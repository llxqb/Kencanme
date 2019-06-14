package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;

import java.util.List;

import cn.jzvd.JzvdStd;

/**
 * RecommendUserAlbumAdapter
 */
public class RecommendUserAlbumAdapter extends BaseQuickAdapter<RecommendUserInfoResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;

    private Context mContext;

    public RecommendUserAlbumAdapter(Context context, @Nullable List<RecommendUserInfoResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.album_item_layout, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, RecommendUserInfoResponse.DataBean item) {
        if (item != null) {
            ImageView imageView = helper.getView(R.id.album_item_iv);

            switch (item.picType) {
                case 1:
                    helper.setVisible(R.id.album_item_sock_iv, false);
                    helper.setVisible(R.id.album_item_sock_hint_tv, false);
                    if (!item.isPic) {
                        helper.setVisible(R.id.album_jz_video,true);
                        helper.setVisible(R.id.album_item_iv,false);
                        JzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
                        jzvdStd.setUp(item.picPath
                                , "好玩");
                        Glide.with(mContext).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(jzvdStd.thumbImageView);
                    }else {
                        helper.setVisible(R.id.album_item_iv,true);
                        helper.setVisible(R.id.album_jz_video,false);
                        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.picPath, imageView, 8,Constant.LOADING_SMALL);
                    }
                    break;
                case 2:
                    helper.setVisible(R.id.album_item_sock_iv, true);
                    helper.setVisible(R.id.album_item_sock_hint_tv, true);
                    helper.setText(R.id.album_item_sock_hint_tv, mContext.getString(R.string.album_vip_view));
                    if (!item.isPic) {
                        helper.setVisible(R.id.album_jz_video,true);
                        helper.setVisible(R.id.album_item_iv,false);
                        JzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
                        jzvdStd.setUp(item.picPath
                                , "好玩");
                        Glide.with(mContext).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(jzvdStd.thumbImageView);
                    }else {
                        helper.setVisible(R.id.album_item_iv,true);
                        helper.setVisible(R.id.album_jz_video,false);
                        mImageLoaderHelper.displayGlassImage(mContext, item.picPath, imageView, Constant.LOADING_SMALL);
                    }
                    break;
                case 3:
                    helper.setVisible(R.id.album_item_sock_iv, true);
                    helper.setVisible(R.id.album_item_sock_hint_tv, true);
                    helper.setText(R.id.album_item_sock_hint_tv, mContext.getString(R.string.album_hi_beans_view));
                    if (!item.isPic) {
                        helper.setVisible(R.id.album_jz_video,true);
                        helper.setVisible(R.id.album_item_iv,false);
                        JzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
                        jzvdStd.setUp(item.picPath
                                , "好玩");
                        Glide.with(mContext).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(jzvdStd.thumbImageView);
                    }else {
                        helper.setVisible(R.id.album_jz_video,false);
                        helper.setVisible(R.id.album_item_iv,true);
                        mImageLoaderHelper.displayGlassImage(mContext, item.picPath, imageView,Constant.LOADING_SMALL);
                    }
                    break;
            }


        }
    }
}
