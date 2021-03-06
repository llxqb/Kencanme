package com.shushan.kencanme.app.mvp.ui.activity.reportUser;


import com.shushan.kencanme.app.entity.request.ReportUserRequest;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UploadImage;
import com.shushan.kencanme.app.entity.response.ReportUserListResponse;
import com.shushan.kencanme.app.mvp.presenter.LoadDataView;
import com.shushan.kencanme.app.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ReportUserControl {
    public interface ReportUserView extends LoadDataView {
        void reportUserListSuccess(ReportUserListResponse reportUserListResponse);

        void reportUserSuccess(String msg);

        void uploadImageSuccess(String picPath);

    }

    public interface PresenterReportUser extends Presenter<ReportUserView> {
        /**
         * 举报原因列表
         */
        void onRequestReportUserList(TokenRequest tokenRequest);
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
