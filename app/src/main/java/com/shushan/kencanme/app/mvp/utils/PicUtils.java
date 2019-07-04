package com.shushan.kencanme.app.mvp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.shushan.kencanme.app.R;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class PicUtils {

    /**
     * 图片转Base64字符串
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }


    /**
     *  * 图片压缩方法一
     *
     * 计算 bitmap大小，如果超过64kb，则进行压缩
     * @param bitmap
     * @return
     */
    public static Bitmap ImageCompressL(Bitmap bitmap) {
        double targetwidth = Math.sqrt(64.00 * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }



    /**
     *   context 上下文
     *   uri 视频地址
     *   imageView 设置image
     *   frameTimeMicros 获取某一时间帧
     */
    @SuppressLint("CheckResult")
    public static void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros) {
        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.placeholder(R.mipmap.loading_middle);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }

            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        RequestListener requestListener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
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
        Glide.with(context).load(uri).listener(requestListener).apply(requestOptions).into(imageView);
    }


    //retrofit上传文件
    private void file_img(String path) {
//        Retrofit retrofitUpload = new Retrofit.Builder()
//                .baseUrl(ServerConstant.DISPATCH_SERVICE)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//
//        PersonalInfoApi service = retrofitUpload.create(PersonalInfoApi.class);
//        File file = new File(path);
//        //设置Content-Type:application/octet-stream
//        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//        //设置Content-Disposition:form-data; name="photo"; filename="xuezhiqian.png"
//        MultipartBody.Part photo = MultipartBody.Part.createFormData("video", file.getName(), photoRequestBody);
//        //添加参数用户名和密码，并且是文本类型
//        Call<ResponseData> loadCall = service.uploadVideoRequest(photo);
//        loadCall.enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                Log.e("APP", response.body().resultCode+"");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

}
