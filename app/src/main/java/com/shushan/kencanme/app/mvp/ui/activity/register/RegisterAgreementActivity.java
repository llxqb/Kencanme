package com.shushan.kencanme.app.mvp.ui.activity.register;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册协议
 */
public class RegisterAgreementActivity extends BaseActivity {
    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.agreement_wb)
    WebView mAgreementWb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_agreement);
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        initData();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initView() {
        mProgressbar.setMax(100);
        mCommonTitleTv.setText(getResources().getString(R.string.AgreementActivity_title));
        mAgreementWb.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        mAgreementWb.setWebChromeClient(new WebChromeViewClient());
        WebSettings webSettings = mAgreementWb.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        mAgreementWb.loadUrl(BuildConfig.KENCANME_BASE_URL + getResources().getString(R.string.register_agreement_wb));//加载url
    }

    @Override
    public void initData() {

    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private class WebChromeViewClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }
}
