package com.shushan.kencanme.help;

import android.content.Context;
import android.view.View;

/**
 * Created by li.liu on 2017/7/10.
 * GlideInterface
 */

public interface GlideInterface<T extends View> {
    void displayImage(Context context, Object path, T imageView);

    void displayImage(Context context, Object path, T imageView, int res);

    void displayCircularImage(Context context, Object path, T imageView);

    void displayRoundedCornerImage(Context context, Object path, T imageView, Integer size);

    void displayMatchImage(Context context, Object path, T imageView);

    T createImageView(Context context);
}
