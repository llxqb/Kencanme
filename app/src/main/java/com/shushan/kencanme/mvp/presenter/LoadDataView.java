
package com.shushan.kencanme.mvp.presenter;

import android.content.Context;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;

public interface LoadDataView {

    void showLoading(String msg);

    void dismissLoading();

    void showToast(String message);

    void judgeToken(Integer code);

    <T> ObservableTransformer<T, T> applySchedulers();

    void addSubscription(Disposable disposable);

    void showErrMessage(Throwable e);

    Context getContext();

//    void initView();
//
//    void initData();

}
