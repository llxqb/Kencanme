package com.shushan.kencanme.app.mvp.views;

import android.content.Context;
import android.os.CountDownTimer;

public class MyTimer extends CountDownTimer {
    private static MyTimer mInstance;
    private MyTimeListener mMyTimeListener;

    public static MyTimer getInstance(long millisInFuture, long countDownInterval, Context context) {
        //单例
        if (mInstance == null) {
            synchronized (MyTimer.class) {
                if (mInstance == null) {
                    mInstance =
                            new MyTimer(millisInFuture, countDownInterval, context);
                }
            }
        }
        return mInstance;
    }

    public void setListener(MyTimeListener myTimeListener){
        mMyTimeListener = myTimeListener;
    }

    private MyTimer(long millisInFuture, long countDownInterval, Context context) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long time) {
//        ArmsUtils.snackbarText("time:"+time);
    }

    @Override
    public void onFinish() {
//        EventBus.getDefault().post(new Eveb);
        if (mMyTimeListener != null) {
            mMyTimeListener.onFinish();
        }
    }

    public interface MyTimeListener {
        void onFinish();
    }
}