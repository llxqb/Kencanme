package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.utils.PicUtils;
import com.shushan.kencanme.mvp.utils.TranTools;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Mime 页面 MyAlbumAdapter
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
        ImageView imageView = helper.getView(R.id.photo_iv);
        if (item != null) {
            if (!TextUtils.isEmpty(item.getAlbum_url())) {
                if (TranTools.isVideo(item.getAlbum_url())) {
                    helper.setVisible(R.id.album_jz_video, true);
                    helper.setVisible(R.id.photo_iv, false);
                    JzvdStd jzvdStd = helper.getView(R.id.album_jz_video);
                    jzvdStd.setUp(item.getAlbum_url(), "");
                    PicUtils.loadVideoScreenshot(mContext, item.getAlbum_url(), jzvdStd.thumbImageView, 0);  //获取视频第一帧显示
                } else {
                    helper.setVisible(R.id.album_jz_video, false);
                    helper.setVisible(R.id.photo_iv, true);
                    mImageLoaderHelper.displayImage(mContext, item.getAlbum_url(), imageView,Constant.LOADING_SMALL);
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
                helper.setVisible(R.id.photo_delete, false);
                helper.setVisible(R.id.album_jz_video, false);
                helper.setVisible(R.id.photo_iv, true);
                mImageLoaderHelper.displayImage(mContext, R.mipmap.report_add_photo, imageView, R.mipmap.report_add_photo);//加载第一张+图片
            }
        }
    }
}