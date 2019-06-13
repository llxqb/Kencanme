package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.kencanme.entity.request.HomeFragmentRequest;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.help.RetryWithDelay;
import com.shushan.kencanme.mvp.model.MainModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class HomeFragmentPresenterImpl implements HomeFragmentControl.homeFragmentPresenter{

    private HomeFragmentControl.HomeView mHomeView;
    private final MainModel mHomeFragmentModel;
    private final Context mContext;

    @Inject
    public HomeFragmentPresenterImpl(Context context, MainModel model, HomeFragmentControl.HomeView homeView) {
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
    public void onRequestInfo(HomeFragmentRequest request) {
        mHomeView.showLoading("加载中...");
        Disposable disposable = mHomeFragmentModel.onRequestInfo(request).compose(mHomeView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestDataSuccess, throwable -> mHomeView.showErrMessage(throwable),
                        () -> mHomeView.dismissLoading());
        mHomeView.addSubscription(disposable);
    }

    private void requestDataSuccess(ResponseData responseData) {
        String ss = "{\"list\":[\n" +
                "{\"active_time\":19,\"age\":22,\"city\":\"Makassar\",\"cover\":\"http://img0.pconline.com.cn/pconline/1304/22/3266618_12.jpg\",\"exposure_time\":0,\"is_like\":0,\"last_login_time\":1559181225,\"nickname\":\"Meong kucing\",\"sex\":2,\"trait\":\"\",\"uid\":1},\n" +
                "{\"active_time\":20,\"age\":24,\"city\":\"Makassar\",\"cover\":\"http://tb-video.bdstatic.com/tieba-smallvideo-transcode/2148489_1c9d8082c70caa732fc0648a21be383c_1.mp4\",\"exposure_time\":0,\"is_like\":0,\"last_login_time\":1559181225,\"nickname\":\"KKKKK\",\"sex\":2,\"trait\":\"http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png\",\"uid\":1}\n" +
                "],\"user\":{\"age\":49,\"album\":[],\"beans\":0,\"birthday\":\"2010/06/12\",\"bust\":\"\",\"city\":\"yyyyy\",\"contact\":[],\"cover\":\"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190612/5d0053fa8a97c.png\",\"declaration\":\"yyyyy\",\"exposure\":0,\"exposure_time\":0,\"exposure_type\":0,\"forbidden\":0,\"height\":0,\"label\":[],\"last_login_time\":1560325844,\"nickname\":\"yyy\",\"occupation\":\"\",\"pushing_age\":\"18-30\",\"pushing_gender\":2,\"sex\":1,\"svip\":0,\"token\":\"33495e9da0bbbca9787466ac70875990\",\"trait\":\"\",\"uid\":6,\"vip\":0,\"vip_time\":0,\"weight\":0}\n" +
                "}\n";
        HomeFragmentResponse  response = new Gson().fromJson(ss,HomeFragmentResponse.class);
        mHomeView.getInfoSuccess(response);


//        if (responseData.resultCode == 0) {
//            responseData.parseData(HomeFragmentResponse.class);
//            HomeFragmentResponse response = (HomeFragmentResponse) responseData.parsedData;
//            mHomeView.getInfoSuccess(response);
//        } else {
//            mHomeView.showToast(responseData.errorMsg);
//        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mHomeView = null;
    }


}
