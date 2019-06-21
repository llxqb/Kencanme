package com.shushan.kencanme.mvp.ui.activity.splash;

import android.os.Bundle;
import android.view.WindowManager;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.ui.activity.main.MainActivity;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity {

    private ScheduledExecutorService scheduledThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        initView();
        initData();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (scheduledThreadPool == null) {
            scheduledThreadPool = Executors.newScheduledThreadPool(1);
        }

        scheduledThreadPool.schedule(() -> {
            startActivitys(MainActivity.class);
            finish();
        }, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (scheduledThreadPool != null) {
                scheduledThreadPool.shutdownNow();
                scheduledThreadPool = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
