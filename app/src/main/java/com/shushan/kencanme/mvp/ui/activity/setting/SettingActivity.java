package com.shushan.kencanme.mvp.ui.activity.setting;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.ui.adapter.PushSexAdapter;
import com.shushan.kencanme.mvp.views.TwoWayRattingBar;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * desc:设置
 */
public class SettingActivity extends BaseActivity implements TwoWayRattingBar.OnProgressChangeListener {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.age_select_tv)
    TextView mAgeSelectTv;
    @BindView(R.id.twoWayRattingBar)
    TwoWayRattingBar mTwoWayRattingBar;
    @BindView(R.id.sex_recycler_view)
    RecyclerView mSexRecyclerView;
    @BindView(R.id.message_reminder_tv)
    TextView mMessageReminderTv;
    @BindView(R.id.clear_cache_tv)
    TextView mClearCacheTv;
    @BindView(R.id.problem_feedback_tv)
    TextView mProblemFeedbackTv;
    @BindView(R.id.about_us_tv)
    TextView mAboutUsTv;
    @BindView(R.id.logout_tv)
    TextView mLogoutTv;

    private String sex[] = {"male", "female", "Unlimited"};
    private PushSexAdapter pushSexAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.SettingActivity_title));
        mTwoWayRattingBar.setOnProgressChangeListener(this);
        List<String> sexList = Arrays.asList(sex);
        pushSexAdapter = new PushSexAdapter(sexList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mSexRecyclerView.setLayoutManager(gridLayoutManager);
        mSexRecyclerView.setAdapter(pushSexAdapter);

        pushSexAdapter.setOnItemClickListener((adapter, view, position) -> {
            showToast("" + sexList.get(position));
        });
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back, R.id.message_reminder_tv, R.id.clear_cache_tv, R.id.problem_feedback_tv, R.id.about_us_tv, R.id.logout_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.message_reminder_tv:
                //消息提醒
                startActivitys(MessageReminderActivity.class);
                break;
            case R.id.clear_cache_tv:
                startActivitys(ClearCacheActivity.class);
                break;
            case R.id.problem_feedback_tv:
                startActivitys(FeedbackProblemActivity.class);
                break;
            case R.id.about_us_tv:
                startActivitys(AboutUsActivity.class);
                break;
            case R.id.logout_tv:
                break;
        }
    }

    @Override
    public void onProgressChange(int leftProgress, int rightProgress) {
//        Log.e("ddd", "left_progress:" + leftProgress + " rightProgress:" + rightProgress);
        String ageSelectValue;
        if (rightProgress == 50) {
            ageSelectValue = leftProgress + "-" + rightProgress + "+";
        } else {
            ageSelectValue = leftProgress + "-" + rightProgress;
        }
        mAgeSelectTv.setText(ageSelectValue);
    }
}
