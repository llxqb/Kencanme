package com.shushan.kencanme.help;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
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
    public void displayImage(Context context, Object path, ImageView imageView,int loadPic) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(loadPic)
                .skipMemoryCache(true)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }


    @Override
    public void displayImage(Context context, Object path, ImageView imageView, int res,int loadPic) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(loadPic)
                .skipMemoryCache(true)
                .error(res)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayCircularImage(Context context, Object path, ImageView imageView,int loadPic) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(TranTools.dip2px(context, 8)))//设置圆角大小
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(loadPic)
                .skipMemoryCache(true)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    @Override
    public void displayRoundedCornerImage(Context context, Object path, ImageView imageView, Integer size,int loadPic) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new RoundedCorners(TranTools.dip2px(context, size)))//设置圆角大小
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(loadPic)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }

    //设置圆角毛玻璃效果
    public void displayGlassImage(Context context, Object path, ImageView imageView,int loadPic) {
        RequestOptions options = RequestOptions
                .bitmapTransform(new MultiTransformation<>(new BlurTransformation(30, 3)
//                        new RoundedCorners(TranTools.dip2px(context, 8))
                        )
                )// radius 越大越模糊
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(loadPic)
                .placeholder(loadPic)
                .dontAnimate();
        Glide.with(context).load(path).apply(options).into(imageView);
    }


    //设置背景图片
    public void displayBackgroundImage(Context context, Object path, View imageView,int loadPic) {
        Glide.with(context).asBitmap().load(path)//签到整体 背景
                .into(new SimpleTarget<Bitmap>(180, 180) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(resource);
                        imageView.setBackground(drawable);    //设置背景
                    }
                });
    }

    /**
     * 设置自适应图片
     */
    @Override
    public void displayMatchImage(Context context, Object path, ImageView imageView,int loadPic) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.loading_big)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.loading_big)
                .dontAnimate();

        RequestListener requestListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                float scale = (float) vw / (float) resource.getIntrinsicWidth();
                int vh = Math.round(resource.getIntrinsicHeight() * scale);
                params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                imageView.setLayoutParams(params);
                return false;
            }
        };
        Glide.with(context).load(path).listener(requestListener)
                .apply(options).into(imageView);
    }


}
