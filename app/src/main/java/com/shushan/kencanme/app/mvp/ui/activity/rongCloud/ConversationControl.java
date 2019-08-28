package com.shushan.kencanme.app.mvp.ui.activity.rongCloud;


import com.shushan.kencanme.app.entity.request.HiNumRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.request.UseBeansRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.app.entity.response.UserInfoByRidResponse;
import com.shushan.kencanme.app.entity.response.UserRelationResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ConversationControl {
    public interface ConversationView extends LoadDataView {
        void uploadImageSuccess(String picPath);

        void uploadImageFail(String erorMsg);

        void UseBeansSuccess(String msg);

        void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse);

        void getUserInfoSuccess(UserInfoByRidResponse userInfoByRidResponse);

        void getUserRelationSuccess(UserRelationResponse userRelationResponse);

        void getHiNumSuccess();

        void getHiNumFail(String msg);

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

        /**
         * 根据融云第三方id获取用户头像和昵称
         */
        void onRequestUserInfoByRid(UserInfoByRidRequest userInfoByRidRequest);

        /**
         * 根据融云第三方id获取关系
         */
        void onRequestUserRelation(UserInfoByRidRequest userInfoByRidRequest);

        /**
         * 免费打招呼数
         */
        void onRequestHiNum(HiNumRequest hiNumRequest);

    }


}
