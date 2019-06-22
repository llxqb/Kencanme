package com.shushan.kencanme.mvp.ui.activity.rongCloud;


import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.request.UseBeansRequest;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ConversationControl {
    public interface ConversationView extends LoadDataView {
        void uploadImageSuccess(String picPath);

        void uploadImageFail(String erorMsg);

        void UseBeansSuccess(String msg);

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

    }

    public interface PresenterConversation extends Presenter<ConversationView> {
        /**
         * 上传图片到服务器
         */
        void uploadImage(UploadImage uploadImage);

        //上传视频
//        void uploadVideo(MultipartBody.Part uploadVideo);

        /**
         * 嗨豆回复消息/查看私密照片
         */
        void onRequestUseBeans(UseBeansRequest useBeansRequest);

        /**
         * 查询用户信息（首页）
         */
        void onRequestHomeUserInfo(TokenRequest tokenRequest);

    }


}
