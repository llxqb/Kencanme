package com.shushan.kencanme.app.mvp.views;

import android.content.Context;
import android.util.AttributeSet;

import com.shushan.kencanme.app.R;

import cn.jzvd.JzvdStd;

/**
 * 自定义播放器view 不显示全屏按钮
 */
public class NoFullScreenJzvdStd extends JzvdStd {


    public NoFullScreenJzvdStd(Context context) {
        super(context);
    }

    public NoFullScreenJzvdStd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.np_full_screen_jzvdstd;
    }

}
