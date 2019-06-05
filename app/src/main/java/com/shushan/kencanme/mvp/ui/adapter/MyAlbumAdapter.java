package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.PhotoBean;
import com.shushan.kencanme.help.ImageLoaderHelper;

import java.util.List;

import cn.jzvd.JzvdStd;

/**
 * MyAlbumAdapter
 */
public class MyAlbumAdapter extends BaseQuickAdapter<PhotoBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    Context mContext;

    public MyAlbumAdapter(Context context, @Nullable List<PhotoBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_my_album, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, PhotoBean item) {
        helper.addOnClickListener(R.id.photo_delete).addOnClickListener( R.id.photo_item_rl);
        ImageView imageView = helper.getView(R.id.photo_iv);
        if (helper.getPosition() == 0 && item == null) {
            helper.setVisible(R.id.photo_delete, false);
        } else {
            helper.setVisible(R.id.photo_delete, true);
        }

        if (item != null) {
//            mImageLoaderHelper.displayImage(mContext, item.picPath, imageView);   没有impl类 不能传mImageLoaderHelper
            if (item.isPic) {
                helper.setVisible(R.id.album_jz_video, false);
                helper.setVisible(R.id.photo_iv, true);
                Glide.with(mContext).load(item.picPath).into(imageView);
            } else {
                helper.setVisible(R.id.album_jz_video, true);
                helper.setVisible(R.id.photo_iv, false);
                JzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
                jzvdStd.setUp(item.picPath
                        , "好玩");
                Glide.with(mContext).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(jzvdStd.thumbImageView);
            }

            switch (item.picType) {
                case 0:
                    helper.setVisible(R.id.photo_hint_tv, false);
                    break;
                case 1:
                    helper.setVisible(R.id.photo_hint_tv, true);
                    helper.setText(R.id.photo_hint_tv, "VIP can look");
                    break;
                case 2:
                    helper.setVisible(R.id.photo_hint_tv, true);
                    helper.setText(R.id.photo_hint_tv, "Hi-Beans can look");
                    break;
            }
        }
    }
}
