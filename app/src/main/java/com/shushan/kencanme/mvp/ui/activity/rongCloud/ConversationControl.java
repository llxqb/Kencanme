package com.shushan.kencanme.mvp.ui.activity.rongCloud;


import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ConversationControl {
    public interface ConversationView extends LoadDataView {
        void uploadImageSuccess(String picPath);

        void uploadImageFail(String erorMsg);
    }

    public interface PresenterConversation extends Presenter<ConversationView> {
        /**
         * 上传图片到服务器
         */
        void uploadImage(UploadImage uploadImage);

        //上传视频
//        void uploadVideo(MultipartBody.Part uploadVideo);
    }


}
