package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.PersonalInfoModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.mvp.ui.activity.login.LoginActivity;
import com.shushan.kencanme.mvp.utils.DateUtil;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建个人信息资料
 */
public class CreatePersonalInfoActivity extends BaseActivity implements PersonalInfoControl.PersonalInfoView {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.male_rb)
    RadioButton mMaleRb;
    @BindView(R.id.female_rb)
    RadioButton mFemaleRb;
    @BindView(R.id.user_nice)
    EditText mUserNice;
    @BindView(R.id.birthday)
    TextView mBirthday;
    @BindView(R.id.address)
    EditText mAddress;
    @BindView(R.id.preserve_btn)
    Button mPreserveBtn;
    String mToken;

    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_personal_info);
        StatusBarUtil.setTranslucent(this);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.create_personal_info_title));
        if (!mBuProcessor.isValidLogin()) {
            startActivitys(LoginActivity.class);
            finish();
        } else {
            mToken = mBuProcessor.getToken();
        }
        //光标移到最后
        mUserNice.setSelection(mUserNice.getText().length());
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.common_back, R.id.male_rb, R.id.female_rb,
            R.id.birthday, R.id.address, R.id.preserve_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.male_rb:
                break;
            case R.id.female_rb:
                break;
            case R.id.birthday:
                showBirthdayDialog();
                break;
            case R.id.address:
                break;
            case R.id.preserve_btn:
                if (isValidEmpty()) {
                    //开始创建信息接口
                    UpdatePersonalInfoRequest request = new UpdatePersonalInfoRequest();
                    request.nickname = mUserNice.getText().toString();
                    request.sex = mMaleRb.isChecked() ? 1 : 2;// 1男2女
                    request.birthday = DateUtil.getTime(mBirthday.getText().toString(), "yyyy/MM/dd");
                    request.city = mAddress.getText().toString();
                    request.token = mToken;
                    PersonalInfoUploadPhotoActivity.start(this, request);
                }
                break;
        }
    }

    /**
     * 选择生日弹框
     */
    private void showBirthdayDialog() {
        TimePickerView pvTime = new TimePickerBuilder(this, (date, v) -> {//选中事件回调
            mBirthday.setText(DateUtil.dateTranString(date, "yyyy/MM/dd"));
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText(getResources().getString(R.string.dialog_cancal))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.dialog_sure))//确认按钮文字
                .setTitleSize(16)//标题文字大小
                .setTitleText(getResources().getString(R.string.personal_info_birthday))//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.first_text_color))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.yellow_btn))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.first_text_color))//取消按钮文字颜色
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("year", "month", "day", "hour", "minute", "second")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }


    /**
     * 验证资料是否填满
     */
    private boolean isValidEmpty() {
        if (TextUtils.isEmpty(mUserNice.getText())) {
            showToast(getResources().getString(R.string.CreatePersonalInfoActivity_nice_is_empty));
            return false;
        }
        if (!mMaleRb.isChecked() && !mFemaleRb.isChecked()) {
            showToast(getResources().getString(R.string.CreatePersonalInfoActivity_sex_is_empty));
            return false;
        }
        if (TextUtils.isEmpty(mBirthday.getText())) {
            showToast(getResources().getString(R.string.CreatePersonalInfoActivity_birthday_is_empty));
            return false;
        }
        if (TextUtils.isEmpty(mAddress.getText())) {
            showToast(getResources().getString(R.string.CreatePersonalInfoActivity_address_is_empty));
            return false;
        }
        return true;
    }

    @Override
    public void updateSuccess(String response) {
        showToast(response);
        startActivitys(PersonalInfoUploadPhotoActivity.class);
    }


    @Override
    public void uploadVideoSuccess(String videoPath) {

    }


    @Override
    public void uploadImageSuccess(String picPath) {

    }


    @Override
    public void updateMyAlbumSuccess(String msg) {

    }

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(CreatePersonalInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
