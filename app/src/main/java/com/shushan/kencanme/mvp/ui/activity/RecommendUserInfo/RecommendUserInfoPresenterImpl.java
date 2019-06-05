package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;

import android.content.Context;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.mvp.model.RecommendUserInfoModel;
import com.shushan.kencanme.mvp.model.ResponseData;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * RecommendUserInfoPresenterImpl
 */

public class RecommendUserInfoPresenterImpl implements RecommendUserInfoControl.PresenterRecommendUserInfo {

    private RecommendUserInfoControl.RecommendUserInfoView mRecommendUserInfoView;
    private final RecommendUserInfoModel mRecommendUserInfoModel;
    private final Context mContext;

    @Inject
    public RecommendUserInfoPresenterImpl(Context context, RecommendUserInfoModel model, RecommendUserInfoControl.RecommendUserInfoView loginView) {
        mContext = context;
        mRecommendUserInfoModel = model;
        mRecommendUserInfoView = loginView;
    }

    /**
     * new RetryWithDelay(3, 3000) 总共重试3次，重试间隔3000毫秒
     * subscribe订阅
     * mLoginView.showErrMessage(throwable)加载出错 ，若加载集合数据用 mLoginView.loadFail(throwable)
     * ::全局作用域符号,修饰方法而不是变量
     */
    @Override
    public void onRequestRecommendUserInfo(RecommendUserInfoRequest request) {
//        mRecommendUserInfoView.showLoading("加载中...");
//        Disposable disposable = mRecommendUserInfoModel.recommendUserInfoRequest(request).compose(mRecommendUserInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
//                .subscribe(this::requestDataSuccess, throwable -> mRecommendUserInfoView.showErrMessage(throwable),
//                        () -> mRecommendUserInfoView.dismissLoading());
//        mRecommendUserInfoView.addSubscription(disposable);
        requestDataSuccess(null);
    }

    private void requestDataSuccess(ResponseData responseData) {
        String json = "{\n" +
                "                \"data\":[\n" +
                "{  \"picPath\": \"http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png\",\n" +
                "                \"picType\": 1,\n" +
                "                \"isVip\": false,\n" +
                "\"isPic\":true,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{\"picPath\": \"http://tb-video.bdstatic.com/tieba-smallvideo-transcode/2148489_1c9d8082c70caa732fc0648a21be383c_1.mp4\",\n" +
                "                \"picType\": 1,\n" +
                "\"isPic\":false,\n" +
                "                \"isVip\": true,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{  \"picPath\": \"http://imgsrc.baidu.com/forum/pic/item/1bd5ad6eddc451da826ef0f4bbfd5266d0163210.jpg\",\n" +
                "                \"picType\":2,\n" +
                "\"isPic\":true,\n" +
                "                \"isVip\": true,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{  \"picPath\": \"http://imgsrc.baidu.com/forum/pic/item/b912c8fcc3cec3fd316f6008db88d43f8794277e.jpg\",\n" +
                "                \"picType\": 2,\n" +
                "                \"isVip\": false,\n" +
                "\"isPic\":true,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{\"picPath\": \"http://tb-video.bdstatic.com/tieba-smallvideo-transcode/2148489_1c9d8082c70caa732fc0648a21be383c_1.mp4\",\n" +
                "                \"picType\": 2,\n" +
                "\"isPic\":false,\n" +
                "                \"isVip\": true,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{  \"picPath\": \"http://imgsrc.baidu.com/forum/pic/item/1bd5ad6eddc451da826ef0f4bbfd5266d0163210.jpg\",\n" +
                "                \"picType\": 3,\n" +
                "\"isPic\":true,\n" +
                "                \"isVip\": false,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{  \"picPath\": \"http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png\",\n" +
                "                \"picType\": 1,\n" +
                "\"isPic\":true,\n" +
                "                \"isVip\": true,\n" +
                "                \"isUseHiDou\": true},\n" +
                "{  \"picPath\": \"http://imgsrc.baidu.com/forum/pic/item/b912c8fcc3cec3fd316f6008db88d43f8794277e.jpg\",\n" +
                "                \"picType\": 3,\n" +
                "                \"isVip\": true,\n" +
                "\"isPic\":true,\n" +
                "                \"isUseHiDou\": true}\n" +
                "\n" +
                "]\n" +
                "             \n" +
                "            }";


        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");

        Gson gson = new Gson();
        ArrayList<RecommendUserInfoResponse.DataBean> userBeanList = new ArrayList<>();

        //循环遍历
        for (JsonElement user : jsonArray) {
            //通过反射 得到UserBean.class
            RecommendUserInfoResponse.DataBean userBean = gson.fromJson(user, new TypeToken<RecommendUserInfoResponse.DataBean>() {}.getType());
            userBeanList.add(userBean);
        }
        mRecommendUserInfoView.getRecommendUserInfoSuccess(userBeanList);


//        if (responseData.resultCode == 0) {
//            responseData.parseData(LoginResponse.class);
//            RecommendUserInfoResponse response = (RecommendUserInfoResponse) responseData.parsedData;
//            mRecommendUserInfoView.getRecommendUserInfoSuccess(response);
//        } else {
//            mRecommendUserInfoView.showToast(responseData.errorDesc);
//        }
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        mRecommendUserInfoView = null;
    }


}
