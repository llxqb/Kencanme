package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;

import java.util.List;

import cn.jzvd.JzvdStd;

/**
 * MyAlbumAdapter
 */
public class MyAlbumAdapter extends BaseQuickAdapter<MyAlbumResponse.DataBean, BaseViewHolder> {
    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public MyAlbumAdapter(Context context, @Nullable List<MyAlbumResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_my_album, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyAlbumResponse.DataBean item) {
        helper.addOnClickListener(R.id.photo_delete).addOnClickListener(R.id.photo_item_rl);
        ImageView imageView = helper.getView(R.id.photo_iv);
        if (item == null) return;
        if (!TextUtils.isEmpty(item.getAlbum_url())) {
            if (helper.getPosition() == 0) {
                helper.setVisible(R.id.photo_delete, false);
            } else {
                helper.setVisible(R.id.photo_delete, true);
            }

            if (TranTools.isVideo(item.getAlbum_url())) {
                helper.setVisible(R.id.album_jz_video, true);
                helper.setVisible(R.id.photo_iv, false);
                JzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
                jzvdStd.setUp(item.getAlbum_url(), "");
                PicUtils.loadVideoScreenshot(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, 0);  //获取视频第一帧显示
            } else {
                helper.setVisible(R.id.album_jz_video, false);
                helper.setVisible(R.id.photo_iv, true);
                mImageLoaderHelper.displayImage(mContext, item.getAlbum_url(), imageView);
            }

            switch (item.getAlbum_type()) {
                case 1:
                    helper.setVisible(R.id.photo_hint_tv, false);
                    break;
                case 2:
                    helper.setVisible(R.id.photo_hint_tv, true);
                    helper.setText(R.id.photo_hint_tv, "VIP can look");
                    break;
                case 3:
                    helper.setVisible(R.id.photo_hint_tv, true);
                    helper.setText(R.id.photo_hint_tv, "Hi-Beans can look");
                    break;
            }
        } else {
            helper.setVisible(R.id.album_jz_video, false);
            helper.setVisible(R.id.photo_delete, false);
            helper.setVisible(R.id.photo_iv, true);
            //加载第一张+图片
            mImageLoaderHelper.displayImage(mContext, R.mipmap.report_add_photo, imageView);
        }
    }
}
