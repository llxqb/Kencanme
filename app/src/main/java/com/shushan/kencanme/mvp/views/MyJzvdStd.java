package com.shushan.kencanme.mvp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JzvdStd;

public class MyJzvdStd extends JzvdStd {

    public MyJzvdStd(Context context) {
        super(context);
    }

    public MyJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {
//        super.onClick(v);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e("ddd","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("ddd","ACTION_UP");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("ddd","ACTION_MOVE");
                break;
        }
        return false;
    }



}
