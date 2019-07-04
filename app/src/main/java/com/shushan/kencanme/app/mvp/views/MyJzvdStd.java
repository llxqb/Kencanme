package com.shushan.kencanme.app.mvp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.jzvd.JzvdStd;

/**
 * 自定义播放器view 防止点击往下传
 */
public class MyJzvdStd extends JzvdStd {


    public MyJzvdStd(Context context) {
        super(context);
    }

    public MyJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void onClick(View v) {
//        if (mIsClick) {
//            super.onClick(v);
//        }
    }

}
