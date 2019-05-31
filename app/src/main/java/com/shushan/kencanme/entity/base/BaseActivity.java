package com.shushan.kencanme.entity.base;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.shushan.kencanme.KencanmeApp;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.AppComponent;
import com.shushan.kencanme.help.ImageLoaderHelper;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.utils.ToastUtil;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by li.liu on 17/12/20.
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Inject
    protected ImageLoaderHelper mImageLoaderHelper;
//    @Inject
//    BuProcessor mBuProcessor;

    private CompositeDisposable mDisposable;
    private Dialog mProgressDialog;
    protected final IntentFilter mFilter = new IntentFilter();

    public AppComponent getAppComponent() {
        return ((KencanmeApp) getApplication()).getAppComponent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFilter();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mFilter);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent));
    }

    public abstract void initView();

    public abstract void initData();


    public void judgeToken(Integer code) {
        if (code == 100401 || code == 100107) {
            showToast("登入过期,请重新登入");
            clearSwitchToLogin();
        }
    }

    /**
     * 清除登录数据
     */
    public void clearSwitchToLogin() {
//        mBuProcessor.clearLoginUser();
//        LoginActivity.start(getContext());
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceivePro(context, intent);
        }
    };

    void onReceivePro(Context context, Intent intent) {
    }

    void addFilter() {
    }

    /**
     * 启动activity
     */
    public void startActivitys(Class<?> tClass) {
        Intent intent = new Intent(this, tClass);
        startActivity(intent);
    }

//    void supportActionBar(Toolbar toolbar, boolean isShowIcon) {
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setDisplayShowHomeEnabled(true);
//        }
//        if (isShowIcon) {
//            toolbar.setNavigationIcon(R.drawable.vector_arrow_left);
//            toolbar.setNavigationOnClickListener(v -> onBackPressed());
//        }
//    }

//    public void showErrMessage(Throwable e) {
//        LogUtils.i("e=" + e.toString());
//        dismissLoading();
//        String mErrMessage;
//        if (e instanceof HttpException || e instanceof ConnectException) {
//            mErrMessage = getString(R.string.text_check_internet);
//        } else {
//            mErrMessage = getString(R.string.text_wait_try);
//        }
//        showToast(mErrMessage);
//    }

    public void showToast(String message) {
        ToastUtil.showToast(getContext(), message);
    }

    public void showLoading() {
    }

    public void showLoading(String msg) {
//        dismissLoading();
//        mProgressDialog = DialogFactory.showLoadingDialog(this, msg);
//        mProgressDialog.show();
    }

    public void dismissLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public Context getContext() {
        return this;
    }

    /**
     * @param subscription RxJava取消订阅
     */
    public void addSubscription(Disposable subscription) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(subscription);
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        //noinspection unchecked
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    private final ObservableTransformer schedulersTransformer = (observable) -> (
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()));

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }
}
