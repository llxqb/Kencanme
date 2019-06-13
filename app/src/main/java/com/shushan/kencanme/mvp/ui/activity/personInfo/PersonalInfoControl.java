package com.shushan.kencanme.mvp.ui.activity.personInfo;


import com.shushan.kencanme.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.entity.response.UpdatePersonalInfoResponse;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

import okhttp3.MultipartBody;

/**
 * Created by li.liu on 2019/05/28.
 */

public class PersonalInfoControl {
    public interface PersonalInfoView extends LoadDataView {
        void updateSuccess(UpdatePersonalInfoResponse response);
        void updateFail(String errorMsg);

        void uploadVideoSuccess(String videoPath);
        void uploadVideoFail(String msg);

        void uploadImageSuccess(String picPath);
        void uploadImageFail(String msg);


        void updateMyAlbumSuccess(String msg);

    }

    public interface PresenterPersonalInfo extends Presenter<PersonalInfoView> {
        //编辑个人资料信息
        void onRequestPersonalInfo(UpdatePersonalInfoRequest personalInfoRequest);
        //上传视频
        void uploadVideo(MultipartBody.Part uploadVideo);
        //上传图片
        void uploadImage(UploadImage uploadImage);
        //我的相册增加、修改
        void updateMyAlbum(UpdateAlbumRequest updateAlbumRequest);

    }


}
