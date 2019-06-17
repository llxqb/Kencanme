package com.shushan.kencanme.mvp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.jzvd.JzvdStd;

public class MyJzvdStd extends JzvdStd {

    private static boolean mIsClick = false;
    private static MyjzvdListener mMyjzvdListener;
    private static int mClickPos;

    public MyJzvdStd(Context context) {
        super(context);
    }

    public MyJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public interface MyjzvdListener {
        void jzvdClickListener(int clickPos);
    }

    public static  void setClick(MyjzvdListener myjzvdListener, boolean isClick, int clickPos) {
        mIsClick = isClick;
        mMyjzvdListener = myjzvdListener;
        mClickPos = clickPos;
    }

    @Override
    public void onClick(View v) {
        if (mIsClick) {
            super.onClick(v);
        } else {
            if (mMyjzvdListener != null) {
                mMyjzvdListener.jzvdClickListener( mClickPos);
            }
        }
    }

}
