package com.shushan.kencanme.mvp.ui.activity.setting;


import com.shushan.kencanme.entity.request.FeedbackProblemRequest;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SettingControl {
    public interface SettingView extends LoadDataView {
        void updateSuccess(String msg);

        void updateFail(String errorMsg);

        void uploadImageSuccess(String picPath);

        void feedbackProblemSuccess(String msg);
    }

    public interface PresenterSetting extends Presenter<SettingView> {
        //编辑个人资料信息
        void onRequestPersonalInfo(UpdatePersonalInfoRequest personalInfoRequest);

        /**
         * 上传图片
         */
        void uploadImage(UploadImage uploadImage);

        /**
         * 问题反馈
         */
        void onRequestFeedbackProblem(FeedbackProblemRequest feedbackProblemRequest);
    }


}
