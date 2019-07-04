package com.shushan.kencanme.app.mvp.ui.activity.personInfo;


import com.shushan.kencanme.app.entity.request.UpdateAlbumRequest;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

import okhttp3.MultipartBody;

/**
 * Created by li.liu on 2019/05/28.
 */

public class PersonalInfoControl {
    public interface PersonalInfoView extends LoadDataView {
        void updateSuccess(String msg);

        void uploadVideoSuccess(String videoPath);

        void uploadImageSuccess(String picPath);

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
