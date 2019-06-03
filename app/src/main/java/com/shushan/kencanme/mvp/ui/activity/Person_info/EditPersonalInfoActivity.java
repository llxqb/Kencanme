package com.shushan.kencanme.mvp.ui.activity.Person_info;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditPersonalInfoActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        StatusBarUtil.setTranslucent(this);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText("Personal information");
        //定义底部标签图片大小和位置
//        Drawable drawableMale = getResources().getDrawable(R.drawable.radiobutton_bg);
//        //第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        drawableMale.setBounds(0, 0, 60, 50);
//        //设置图片在文字的哪个方向
//        mMaleRb.setCompoundDrawables(null, drawableMale, null, null);
//        mFemaleRb.setCompoundDrawables(null, drawableMale, null, null);
        mMaleRb.setButtonDrawable(R.drawable.radiobutton_bg);
        mFemaleRb.setButtonDrawable(R.drawable.radiobutton_bg);
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
                break;
            case R.id.address:
                break;
            case R.id.preserve_btn:
                startActivitys(PersonalInfoUploadPhotoActivity.class);
                break;
        }
    }

}
