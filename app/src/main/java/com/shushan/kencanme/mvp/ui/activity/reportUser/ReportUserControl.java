package com.shushan.kencanme.mvp.ui.activity.reportUser;


import com.shushan.kencanme.entity.request.ReportUserRequest;
import com.shushan.kencanme.entity.request.UploadImage;
import com.shushan.kencanme.mvp.presenter.LoadDataView;
import com.shushan.kencanme.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ReportUserControl {
    public interface ReportUserView extends LoadDataView {
        void reportUserSuccess(String msg);

        void uploadImageSuccess(String picPath);

    }

    public interface PresenterReportUser extends Presenter<ReportUserView> {
        /**
         * 举报用户
         */
        void onRequestReportUser(ReportUserRequest reportUserRequest);

        /**
         * 上传图片
         */
        void uploadImage(UploadImage uploadImage);

    }


}
