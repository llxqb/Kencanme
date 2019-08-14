package com.shushan.kencanme.app.mvp.ui.activity.personInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.PersonalInfoModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.entity.response.UploadVideoResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.mvp.utils.DateUtil;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.SystemUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑个人信息
 */
public class EditPersonalInfoActivity extends BaseActivity implements PersonalInfoControl.PersonalInfoView {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.edit_city_et)
    EditText mEditCityEt;
    @BindView(R.id.edit_height_et)
    EditText mEditHeightEt;
    @BindView(R.id.edit_weight_et)
    EditText mEditWeightEt;
    @BindView(R.id.edit_chest_et)
    EditText mEditChestEt;
    @BindView(R.id.chest_ll)
    LinearLayout mChestLl;
    @BindView(R.id.edit_birthday_tv)
    TextView mEditBirthdayTv;
    @BindView(R.id.edit_occupation_et)
    EditText mEditOccupationEt;
    @BindView(R.id.save_btn)
    Button mSaveBtn;
    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;
    LoginUser mLoginUser;
    private String mBirthdayValue;
    UpdatePersonalInfoRequest updatePersonalInfoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        initializeInjector();
        ButterKnife.bind(this);
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mLoginUser = mBuProcessor.getLoginUser();
        mCommonTitleTv.setText(getResources().getString(R.string.EditPersonalInfoActivity_title));
        if (mLoginUser.sex == 1) {
            mChestLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        if (!TextUtils.isEmpty(mLoginUser.city)) {
            mEditCityEt.setText(mLoginUser.city);
        }
        if (!TextUtils.isEmpty(mLoginUser.height)) {
            mEditHeightEt.setText(mLoginUser.height);
        }
        if (!TextUtils.isEmpty(mLoginUser.weight)) {
            mEditWeightEt.setText(mLoginUser.weight);
        }
        if (!TextUtils.isEmpty(mLoginUser.bust)) {
            mEditChestEt.setText(mLoginUser.bust);
        }
        if (!TextUtils.isEmpty(mLoginUser.birthday)) {
            mEditBirthdayTv.setText(DateUtil.getStrTime(Long.parseLong(mLoginUser.birthday), "yyyy/MM/dd"));
        }
        if (!TextUtils.isEmpty(mLoginUser.occupation)) {
            mEditOccupationEt.setText(mLoginUser.occupation);
        }
        //光标移到最后
        mEditCityEt.setSelection(mEditCityEt.getText().length());
    }

    @OnClick({R.id.common_back, R.id.edit_birthday_tv, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.edit_birthday_tv:
                SystemUtils.hideSoftKeyboard(this);
                showBirthdayDialog();
                break;
            case R.id.save_btn:
                if (TextUtils.isEmpty(mEditCityEt.getText())) {
                    showToast(getResources().getString(R.string.CreatePersonalInfoActivity_address_is_empty));
                    return;
                }
                updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
                updatePersonalInfoRequest.token = mBuProcessor.getToken();
                updatePersonalInfoRequest.city = mEditCityEt.getText().toString();
                updatePersonalInfoRequest.height = mEditHeightEt.getText().toString();
                updatePersonalInfoRequest.weight = mEditWeightEt.getText().toString();
                updatePersonalInfoRequest.bust = mEditChestEt.getText().toString();
                if (!TextUtils.isEmpty(mBirthdayValue)) {
                    updatePersonalInfoRequest.birthday = DateUtil.getTime(mBirthdayValue, DateUtil.TIME_YYMMDD_HHMMSS);
                }
                updatePersonalInfoRequest.occupation = mEditOccupationEt.getText().toString();
                mPresenter.updatePersonalInfo(updatePersonalInfoRequest);
                break;
        }
    }

    /**
     * 选择生日弹框
     */
    private void showBirthdayDialog() {
        TimePickerView pvTime = new TimePickerBuilder(this, (date, v) -> {//选中事件回调
            mBirthdayValue = DateUtil.dateTranString(date, DateUtil.TIME_YYMMDD_HHMMSS);
            mEditBirthdayTv.setText(DateUtil.dateTranString(date, "yyyy/MM/dd"));
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText(getResources().getString(R.string.dialog_cancal))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.dialog_sure))//确认按钮文字
                .setTitleSize(18)//标题文字大小
                .setTitleText(getResources().getString(R.string.personal_info_birthday))//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.first_text_color))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.yellow_btn))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.first_text_color))//取消按钮文字颜色
                .setLabel("year", "month", "day", "hour", "minute", "second")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    @Override
    public void updateSuccess(String response) {
        requestPersonalInfo();
    }

    /**
     * 查询 我的
     */
    private void requestPersonalInfo() {
        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest();
        personalInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestPersonalInfo(personalInfoRequest);
    }

    /**
     * 查询我的成功
     */
    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {
        //更新用户数据
        showToast(getResources().getString(R.string.success));
        mBuProcessor.setLoginUser(LoginUtils.tranLoginUser(response));
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_USER_INFO));
        finish();
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


    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(EditPersonalInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
