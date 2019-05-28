package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    @Override
    public void initView() {
        showLoading();
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("exitLogin", false)) {
            //退出登录
            finish();
        }
    }
}
