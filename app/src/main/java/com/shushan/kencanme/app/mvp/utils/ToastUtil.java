package com.shushan.kencanme.app.mvp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 管理toast的类，整个app用该类来显示toast
 * Created by li.liu on 16/3/22.
 */
public class ToastUtil {
    private static Toast toast;

    public ToastUtil(){
        throw new UnsupportedOperationException("不能被实例化");
    }


    public static void showToast(Context mContext, String message) {
        if (toast == null) {
            toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
