package com.shushan.kencanme.mvp.ui.activity.setting;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 问题反馈
 */
public class FeedbackProblemActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.question_desc_ev)
    EditText mQuestionDescEv;
    @BindView(R.id.world_limit_tv)
    TextView mWorldLimitTv;
    @BindView(R.id.photo_select_num)
    TextView mPhotoSelectNum;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.email_ev)
    EditText mEmailEv;
    @BindView(R.id.save_btn)
    Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_problem);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.FeedbackProblemActivity_title));
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.save_btn:
                break;
        }
    }
}
