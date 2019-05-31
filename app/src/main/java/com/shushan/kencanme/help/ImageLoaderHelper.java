package com.shushan.kencanme.help;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.shushan.kencanme.R;
import com.shushan.kencanme.mvp.utils.TranTools;

import javax.inject.Inject;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by li.liu on 2017/7/10.
 * ImageLoaderHelper
 */

public class ImageLoaderHelper extends GlideLoader {

    @Inject
    public ImageLoaderHelper(final Context ctx) {
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
//                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }


    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int res) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .error(res)
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(TranTools.dip2px(context, 8)))//设置圆角大小
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.mipmap.ic_launcher)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView, Integer size) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(TranTools.dip2px(context, size)))//设置圆角大小
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    //设置圆角毛玻璃效果
    public void displayGlassImage(Context context, Object path, ImageView imageView) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new MultiTransformation<>(new BlurTransformation(30, 3)
//                        new RoundedCorners(TranTools.dip2px(context, 8))
                        )
                )// radius 越大越模糊
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayMatchImage(Context context, Object path, ImageView imageView) {

//        Glide.with(context)
//                .load((String) path)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if (imageView == null) {
//                            return false;
//                        }
//                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                        }
//                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
//                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
//                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
//                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
//                        imageView.setLayoutParams(params);
//                        return false;
//                    }
//                })
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(imageView);
    }
}
