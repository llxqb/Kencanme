package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑个人信息
 */
public class EditPersonalInfoActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_personal_info);
        ButterKnife.bind(this);
//        StatusBarUtil.setTranslucent(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.EditPersonalInfoActivity_title));
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.edit_birthday_tv, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.edit_birthday_tv:
                break;
            case R.id.save_btn:

                break;
        }
    }
}
