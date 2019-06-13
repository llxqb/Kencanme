package com.shushan.kencanme.mvp.ui.fragment.mine;

import android.content.Context;

import com.shushan.kencanme.mvp.model.MainModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class MineFragmentPresenterImpl implements MineFragmentControl.mineFragmentPresenter{

    private MineFragmentControl.MineView mHomeView;
    private final MainModel mHomeFragmentModel;
    private final Context mContext;

    @Inject
    public MineFragmentPresenterImpl(Context context, MainModel model, MineFragmentControl.MineView homeView) {
        mContext = context;
        mHomeFragmentModel = model;
        mHomeView = homeView;
    }

    /**
     * new RetryWithDelay(3, 3000) 总共重试3次，重试间隔3000毫秒
     * subscribe订阅
     * mLoginView.showErrMessage(throwable)加载出错 ，若加载集合数据用 mLoginView.loadFail(throwable)
     * ::全局作用域符号,修饰方法而不是变量
     */


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mHomeView = null;
    }


}
