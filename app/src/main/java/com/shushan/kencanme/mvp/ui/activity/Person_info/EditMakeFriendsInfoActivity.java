package com.shushan.kencanme.mvp.ui.activity.Person_info;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑交友资料
 */
public class EditMakeFriendsInfoActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.head_icon_iv)
    ImageView mHeadIconIv;
    @BindView(R.id.head_icon_rl)
    RelativeLayout mHeadIconRl;
    @BindView(R.id.user_name_tv)
    TextView mUserNameTv;
    @BindView(R.id.user_name_rl)
    RelativeLayout mUserNameRl;
    @BindView(R.id.declaration_ev)
    EditText mDeclarationEv;
    @BindView(R.id.declaration_world_limit_tv)
    TextView mDeclarationWorldLimitTv;
    @BindView(R.id.save_tv)
    TextView mSaveTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_make_friends_info);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.EditMakeFriendsInfoActivity_title));
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.head_icon_rl, R.id.save_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.head_icon_rl:
                break;
            case R.id.save_tv:

                break;
        }
    }
}
