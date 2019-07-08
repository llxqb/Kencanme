package com.shushan.kencanme.app.mvp.ui.activity.register;
/**
 * 邀请页面
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shushan.kencanme.app.BuildConfig;
import com.shushan.kencanme.app.KencanmeApp;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.user.BuProcessor;
import com.shushan.kencanme.app.mvp.utils.LogUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.shushan.kencanme.app.BuildConfig.KENCANME_BASE_URL;

/**
 * 邀请页
 */
public class EarnBeansActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.share_btn)
    Button mShareBtn;
    @Inject
    protected BuProcessor mBuProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn_beans);
        ButterKnife.bind(this);
        ((KencanmeApp) getApplication()).getAppComponent().inject(this);
        setStatusBar();
        initView();
        initData();
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void initView() {
        mProgressbar.setMax(100);
        mCommonTitleTv.setText(getResources().getString(R.string.EarnBeansActivity_title));
        mWebview.addJavascriptInterface(this, "android");//添加js监听 这样html就能调用客户端
        mWebview.setWebChromeClient(new WebChromeViewClient());
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        mWebview.loadUrl(KENCANME_BASE_URL + getResources().getString(R.string.earn_beans_wb) + mSharePreferenceUtil.getData("code"));//加载url

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Log.e("ddd", "url:" + url);
//                if (url.contains("js-call:")) {
//                    if (url.contains("PlaySnake")) {
//                        Log.d("X5WebViewActivity", "玩蛇");
//                    }
//                    return false;
//                }
                if (url.contains("share")) {
                    share();
                }
//                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.share_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.share_btn:
//                share();
                break;
        }
    }

    private void share() {
        //分享到facebook
        SnsPlatform snsPlatform = SHARE_MEDIA.FACEBOOK.toSnsPlatform();
        //分享链接
        UMWeb web = new UMWeb(BuildConfig.KENCANME_BASE_URL + getResources().getString(R.string.down_app) + mSharePreferenceUtil.getData("code"));
        web.setTitle(getResources().getString(R.string.facebook_title));
        web.setThumb(new UMImage(this, R.drawable.logo));
        web.setDescription(getResources().getString(R.string.facebook_content));
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(snsPlatform.mPlatform)
                .setCallback(umShareListener).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         *  分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         *  分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtils.e("成功了");
            Toast.makeText(EarnBeansActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         *  分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            LogUtils.e("失败");
            Toast.makeText(EarnBeansActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            LogUtils.e("取消了");
            Toast.makeText(EarnBeansActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

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


}
