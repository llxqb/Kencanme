package com.shushan.kencanme.app.help;

import android.content.Context;
import android.view.View;

/**
 * Created by li.liu on 2017/7/10.
 * GlideInterface
 */

public interface GlideInterface<T extends View> {
    void displayImage(Context context, Object path, T imageView,int loadPic);

    void displayImage(Context context, Object path, T imageView, int res,int loadPic);

    void displayCircularImage(Context context, Object path, T imageView,int loadPic);

    void displayRoundedCornerImage(Context context, Object path, T imageView, Integer size,int loadPic);

    void displayMatchImage(Context context, Object path, T imageView,int loadPic);

    T createImageView(Context context);
}
