package com.shushan.kencanme.app.mvp.ui.activity.personInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.PersonalInfoModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.entity.response.UploadVideoResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.app.mvp.views.CommonDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑个人标签
 */
public class EditLabelActivity extends BaseActivity implements PersonalInfoControl.PersonalInfoView, CommonDialog.CommonDialogListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.save_btn)
    Button mSaveBtn;
    @BindView(R.id.add_tv)
    TextView mAddTv;
    @BindView(R.id.text_et)
    EditText mTextEt;
    RecommendUserLabelAdapter recommendUserLabelAdapter;
    List<String> labelList = new ArrayList<>();
    ;
    int deletePos;
    /**
     * 是否是保存  false是指执行删除
     */
    private boolean isPreserveLabel = true;
    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;
    UpdatePersonalInfoRequest updatePersonalInfoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_label);
        ButterKnife.bind(this);
        initializeInjector();
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.EditLabelActivity_title));
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 3);
        mLabelRecyclerView.setLayoutManager(gridLayoutManager2);
        recommendUserLabelAdapter = new RecommendUserLabelAdapter(this, labelList);
        mLabelRecyclerView.setAdapter(recommendUserLabelAdapter);
        mLabelRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                isPreserveLabel = false;
                deletePos = position;
                switch (view.getId()) {
                    case R.id.label_delete_iv:
                        showDialog();
                        break;
                }
            }
        });
    }

    /**
     * 显示是否删除label
     */
    private void showDialog() {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setStyle(Constant.DIALOG_ONE);
        commonDialog.setContent(getResources().getString(R.string.EditLabelActivity_dialog_delete_label_hint));
        commonDialog.setListener(this);
        DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }


    @Override
    public void initData() {
        String labelString = mBuProcessor.getLoginUser().label; //转换联系方式为list
        if (!TextUtils.isEmpty(labelString) && !labelString.equals("null")) {
            Gson gson = new Gson();
            Type labelListType = new TypeToken<List<String>>() {
            }.getType();
            labelList = gson.fromJson(labelString, labelListType);
            recommendUserLabelAdapter.setNewData(labelList);
        }
    }

    @OnClick({R.id.common_back, R.id.add_tv, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.add_tv:
                if (!TextUtils.isEmpty(mTextEt.getText())) {
//                    LabelBean labelBean = new LabelBean();
//                    labelBean.name = mTextEt.getText().toString();
                    recommendUserLabelAdapter.addData(mTextEt.getText().toString());
                    mTextEt.setText("");
                } else {
                    showToast(getResources().getString(R.string.EditLabelActivity_dialog_add_label_hint));
                }
                break;
            case R.id.save_btn:
                isPreserveLabel = true;
                saveLabel();
                break;
        }
    }

    private void saveLabel() {
        if (labelList != null && labelList.size() > 0) {
            updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
            updatePersonalInfoRequest.token = mBuProcessor.getToken();
            updatePersonalInfoRequest.label = new Gson().toJson(recommendUserLabelAdapter.getData());
//            LogUtils.e("updatePersonalInfoRequest:" + new Gson().toJson(updatePersonalInfoRequest));
            mPresenter.updatePersonalInfo(updatePersonalInfoRequest);
        } else {
            showToast(getResources().getString(R.string.EditLabelActivity_dialog_add_label_hint));
        }
    }

    private void deleteLabel(){
        updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
        updatePersonalInfoRequest.token = mBuProcessor.getToken();
        updatePersonalInfoRequest.label = new Gson().toJson(recommendUserLabelAdapter.getData());
        mPresenter.updatePersonalInfo(updatePersonalInfoRequest);
    }


    @Override
    public void updateSuccess(String msg) {
        showToast(msg);
        updatLoginUser();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_USER_INFO));
        if (isPreserveLabel) {
            finish();
        }
    }

    /**
     * 更新用户数据
     */
    private void updatLoginUser() {
        LoginUser loginUser = mBuProcessor.getLoginUser();
        loginUser.label = updatePersonalInfoRequest.label;
//        LogUtils.e("loginUser" + new Gson().toJson(loginUser));
        mBuProcessor.setLoginUser(loginUser);
    }


    @Override
    public void uploadVideoSuccess(UploadVideoResponse uploadVideoResponse) {

    }

    @Override
    public void uploadImageSuccess(String picPath) {

    }

    @Override
    public void updateMyAlbumSuccess(String msg) {

    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {

    }

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(EditLabelActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public void commonDialogBtnOkListener() {
        recommendUserLabelAdapter.remove(deletePos);
        deleteLabel();
    }
}
