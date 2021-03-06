package com.shushan.kencanme.app.mvp.ui.activity.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerSettingComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.SettingModule;
import com.shushan.kencanme.app.entity.Constants.ActivityConstant;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.SexBean;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.user.LoginUser;
import com.shushan.kencanme.app.help.DialogFactory;
import com.shushan.kencanme.app.mvp.ui.activity.main.MainActivity;
import com.shushan.kencanme.app.mvp.ui.adapter.PushSexAdapter;
import com.shushan.kencanme.app.mvp.views.CommonDialog;
import com.shushan.kencanme.app.mvp.views.TwoWayRattingBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * desc:设置
 */
public class SettingActivity extends BaseActivity implements TwoWayRattingBar.OnProgressChangeListener, SettingControl.SettingView, CommonDialog.CommonDialogListener {

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
    private String[] sex;
    private PushSexAdapter pushSexAdapter;
    private String minYear = "18", maxYear = "50";
    private int pushSex;//性别 1男2女
    List<SexBean> sexList = new ArrayList<>();
    private boolean isUpdatePersonal = false;
    @Inject
    SettingControl.PresenterSetting mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initializeInjector();
        setStatusBar();
        initView();
        initData();
    }

    @Override
    public void initView() {
        sex = new String[]{getResources().getString(R.string.male), getResources().getString(R.string.female), getResources().getString(R.string.Unlimited)};
        mCommonTitleTv.setText(getResources().getString(R.string.SettingActivity_title));
        mTwoWayRattingBar.setOnProgressChangeListener(this);
        pushSexAdapter = new PushSexAdapter(sexList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mSexRecyclerView.setLayoutManager(gridLayoutManager);
        mSexRecyclerView.setAdapter(pushSexAdapter);

        pushSexAdapter.setOnItemClickListener((adapter, view, position) -> {
            isUpdatePersonal = true;
            SexBean sexBean = (SexBean) adapter.getItem(position);
            assert sexBean != null;
            for (SexBean sexBean1 : pushSexAdapter.getData()) {
                if (sexBean1.isCheck) sexBean1.isCheck = false;
            }
            sexBean.isCheck = true;
            if (position == 0) {
                pushSex = 1;
            } else if (position == 1) {
                pushSex = 2;
            } else if (position == 2) {
                pushSex = 0;
            }
            pushSexAdapter.notifyDataSetChanged();
        });

    }

    @Override
    public void initData() {
        LoginUser loginUser = mBuProcessor.getLoginUser();
        pushSex = loginUser.pushing_gender;
        for (String aSex : sex) {
            SexBean sexBean = new SexBean();
            sexBean.name = aSex;
            sexList.add(sexBean);
        }

        //等onCreate方法执行完了，我们定义的控件才会被度量(measure)，所以我们在onCreate方法里面通过view.getHeight()获取控件的高度或者宽度肯定是0
        ViewTreeObserver vto = mTwoWayRattingBar.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTwoWayRattingBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (Integer.parseInt(loginUser.pushing_small_age) > 0) {
                    float minValue = (float) (Integer.parseInt(loginUser.pushing_small_age) - 18) / 32;
                    mTwoWayRattingBar.setLeftProgress(minValue, mTwoWayRattingBar.getWidth(), true);
                }
                if (loginUser.pushing_large_age.equals("50+")) {
                    loginUser.pushing_large_age = "50";
                }
                if (Integer.parseInt(loginUser.pushing_large_age) > 0) {
                    float maxValue = (float) (Integer.parseInt(loginUser.pushing_large_age) - 18) / 32;
                    mTwoWayRattingBar.setRightProgress(maxValue, mTwoWayRattingBar.getWidth(), true);
                }
            }
        });


        for (int i = 0; i < sexList.size(); i++) {
            if (loginUser.pushing_gender == 0) {
                sexList.get(2).isCheck = true;
            }
            if (loginUser.pushing_gender == 1) {
                sexList.get(0).isCheck = true;
            }
            if (loginUser.pushing_gender == 2) {
                sexList.get(1).isCheck = true;
            }
        }

    }

    @OnClick({R.id.common_back, R.id.message_reminder_tv, R.id.clear_cache_tv, R.id.problem_feedback_tv, R.id.about_us_tv, R.id.logout_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                onBackPressed();
                break;
            case R.id.message_reminder_tv:
                //消息提醒
                startActivitys(MessageReminderActivity.class);
                break;
            case R.id.clear_cache_tv:
                startActivitys(ClearCacheActivity.class);
                break;
            case R.id.problem_feedback_tv:
                startActivitys(FeedbackProblemActivity.class);//问题反馈
                break;
            case R.id.about_us_tv:
                startActivitys(AboutUsActivity.class);
                break;
            case R.id.logout_tv:
                showExitLoginDialog();
                break;
        }
    }

    private void showExitLoginDialog() {
        DialogFactory.showCommonDialog(this, getResources().getString(R.string.log_out_hint), Constant.DIALOG_FIVE);
    }

    @Override
    public void onProgressChange(int leftProgress, int rightProgress, boolean isFirstComeIn) {
        if (!isFirstComeIn) {
            isUpdatePersonal = true;
        }
        minYear = String.valueOf(leftProgress);
        String ageSelectValue;
        if (rightProgress == 50) {
            ageSelectValue = leftProgress + "-" + rightProgress + "+";
            maxYear = String.valueOf(rightProgress);
        } else {
            ageSelectValue = leftProgress + "-" + rightProgress;
            maxYear = String.valueOf(rightProgress);
        }
        mAgeSelectTv.setText(ageSelectValue);
    }

    @Override
    public void updateSuccess(String msg) {
        showToast(msg);
        updatLoginUser();
        //更新首页推送内容
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_HOME_LIST_INFO));
        super.onBackPressed();
    }

    @Override
    public void updateFail(String errorMsg) {
        showToast(errorMsg);
        super.onBackPressed();
    }

    @Override
    public void uploadImageSuccess(String picPath) {
    }

    @Override
    public void feedbackProblemSuccess(String msg) {
    }

    /**
     * 退出登录
     */
    @Override
    public void commonDialogBtnOkListener() {
        exitLogin(this);
    }

    /**
     * 退出登录
     */
    public void exitLogin(Context context) {
        String hiLayoutKey = mSharePreferenceUtil.getData("hi_layout_key");
        mSharePreferenceUtil.clearData();
        //不清除会话id, 用来记录打招呼消息id ，设置切换用户不改变打招呼消息状态
        mSharePreferenceUtil.setData("hi_layout_key", hiLayoutKey);//记录会话id
        RongIM.getInstance().logout();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//表示 不创建新的实例activity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//表示 移除该activity上面的activity
        intent.putExtra("exitLogin", true);
        context.startActivity(intent);
        finish();
    }

    /**
     * 更新用户数据
     */
    private void updatLoginUser() {
        LoginUser loginUser = mBuProcessor.getLoginUser();
        loginUser.pushing_small_age = updatePersonalInfoRequest.pushing_small_age;
        loginUser.pushing_large_age = updatePersonalInfoRequest.pushing_large_age;
        loginUser.pushing_gender = updatePersonalInfoRequest.pushing_gender;
        mBuProcessor.setLoginUser(loginUser);
    }

    UpdatePersonalInfoRequest updatePersonalInfoRequest;

    @Override
    public void onBackPressed() {
        if (isUpdatePersonal) {
            updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
            updatePersonalInfoRequest.token = mBuProcessor.getToken();
            updatePersonalInfoRequest.pushing_small_age = minYear;
            updatePersonalInfoRequest.pushing_large_age = maxYear;
            updatePersonalInfoRequest.pushing_gender = pushSex;
            mPresenter.updatePersonalInfo(updatePersonalInfoRequest);
        } else {
            super.onBackPressed();
        }
    }

    private void initializeInjector() {
        DaggerSettingComponent.builder().appComponent(getAppComponent())
                .settingModule(new SettingModule(SettingActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
