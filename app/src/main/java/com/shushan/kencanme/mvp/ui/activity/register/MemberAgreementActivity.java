package com.shushan.kencanme.mvp.ui.activity.register;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.ServerConstant;
import com.shushan.kencanme.entity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员协议
 */
public class MemberAgreementActivity extends BaseActivity {
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
        setContentView(R.layout.activity_member_agreement);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.AgreementActivity_title));
        mAgreementWb.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        mAgreementWb.setWebViewClient(webViewClient);
        WebSettings webSettings = mAgreementWb.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        mAgreementWb.loadUrl(ServerConstant.DISPATCH_SERVICE + getResources().getString(R.string.member_agreement_wb));//加载url
    }

    @Override
    public void initData() {

    }

    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            mProgressbar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

    };

    @OnClick(R.id.common_back)
    public void onViewClicked() {
        finish();
    }

}
