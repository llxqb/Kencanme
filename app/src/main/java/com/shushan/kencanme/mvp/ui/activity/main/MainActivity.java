package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerMainComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.entity.response.MessageIdResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.RongCloudHelper;
import com.shushan.kencanme.mvp.ui.activity.login.LoginActivity;
import com.shushan.kencanme.mvp.ui.activity.personInfo.CreatePersonalInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.rongCloud.CustomizeMessageItemProvider;
import com.shushan.kencanme.mvp.ui.adapter.MyFragmentAdapter;
import com.shushan.kencanme.mvp.ui.fragment.HomeFragment;
import com.shushan.kencanme.mvp.ui.fragment.MessageFragment;
import com.shushan.kencanme.mvp.ui.fragment.MineFragment;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.LoginUtils;
import com.shushan.kencanme.mvp.views.MyNoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainControl.MainView, RongIM.UserInfoProvider {

    @BindView(R.id.main_bottom_navigation)
    BottomNavigationView mMainBottomNavigation;
    @BindView(R.id.main_viewpager)
    MyNoScrollViewPager mMainViewpager;
    public static final int SWITCH_HOME_PAGE = 0;
    public static final int SWITCH_MESSAGE_PAGE = 1;
    public static final int SWITCH_MINE_PAGE = 2;
    @Inject
    MainControl.PresenterMain mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initInjectData();
        initView();
    }


    @Override
    public void initView() {
        mMainBottomNavigation.setItemIconTintList(null);
        if (!mBuProcessor.isValidLogin()) {
            startActivitys(LoginActivity.class);
            finish();
        } else {
            if (!mBuProcessor.isFinishFirstWrite()) {
                startActivitys(CreatePersonalInfoActivity.class);
                finish();
            }
            LoginUser loginUser = mBuProcessor.getLoginUser();
            Log.e("ddd", "loginUser:" + new Gson().toJson(mBuProcessor.getLoginUser()));
            initData();
        }
        List<Fragment> fragments = new ArrayList<>();
        HomeFragment homeFragment = HomeFragment.newInstance();
        MessageFragment messageFragment = MessageFragment.newInstance();
        MineFragment mineFragment = MineFragment.newInstance();

        fragments.add(homeFragment);
        fragments.add(messageFragment);
        fragments.add(mineFragment);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mMainViewpager.setOffscreenPageLimit(fragments.size());
        mMainViewpager.setAdapter(adapter);
        mMainBottomNavigation.setOnNavigationItemSelectedListener(this);
        connectRongCloud();
    }

    @Override
    public void initData() {
        requestPersonalInfo();

    }

    private void requestPersonalInfo() {
        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest();
        personalInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestPersonalInfo(personalInfoRequest);
    }

    private void connectRongCloud() {
        //"MbbN5DyzAEs2Vruc4Sirkac3QJl342gyNW2NyYV7fKr3kEu705lRicWjNXyo5Ok1T7F5rN+y/6ypnXiFpNArqFxA4Ai8GBqr"
        String rToken = mSharePreferenceUtil.getData("ryToken");
        LogUtils.d("rToken:" + rToken);
        String rongId = mSharePreferenceUtil.getData("rongId");
        //连接融云
        if (!TextUtils.isEmpty(rToken)) {
            RongCloudHelper.connect(rToken);
        }
        //同步与服务器信息 new CustomerUserInfoProvider(rongId, mBuProcessor.getLoginUser())
        RongIM.setUserInfoProvider(this, true);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra("exitLogin", false)) {
            //退出登录
            startActivitys(LoginActivity.class);
            finish();
        } else if (intent.getBooleanExtra("update_personal_info", false)) {
            requestPersonalInfo();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        resetToDefaultIcon();//重置到默认不选中图片
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                //在这里替换图标
                menuItem.setIcon(R.mipmap.bottom_bar_home_choose);
                mMainViewpager.setCurrentItem(SWITCH_HOME_PAGE, false);
                break;
            case R.id.action_message:
                menuItem.setIcon(R.mipmap.bottom_bar_message_choose);
                mMainViewpager.setCurrentItem(SWITCH_MESSAGE_PAGE, false);
                break;
            case R.id.action_mine:
                menuItem.setIcon(R.mipmap.bottom_bar_mine_choose);
                mMainViewpager.setCurrentItem(SWITCH_MINE_PAGE, false);
                break;
        }
        return true;
    }

    private void resetToDefaultIcon() {
        MenuItem home = mMainBottomNavigation.getMenu().findItem(R.id.action_home);
        MenuItem message = mMainBottomNavigation.getMenu().findItem(R.id.action_message);
        MenuItem mine = mMainBottomNavigation.getMenu().findItem(R.id.action_mine);
        home.setIcon(R.mipmap.bottom_bar_home);
        message.setIcon(R.mipmap.bottom_bar_message);
        mine.setIcon(R.mipmap.bottom_bar_mine);
    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {
        //保存用户信息
        mBuProcessor.setLoginUser(LoginUtils.tranLoginUser(response));
        reqMessageId();
    }

    private void reqMessageId() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMessageId(tokenRequest);
    }

    @Override
    public void messageIdSuccess(MessageIdResponse messageIdResponse) {
        mSharePreferenceUtil.saveObjData("messageIdList", messageIdResponse.getData());
        CustomizeMessageItemProvider.setMessageList(messageIdResponse.getData());
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return findUserById(userId);
    }

    private UserInfo findUserById(String userId) {
        UserInfoByRidRequest userInfoByRidRequest = new UserInfoByRidRequest();
        userInfoByRidRequest.token = mBuProcessor.getToken();
        userInfoByRidRequest.rongyun_third_id = userId;
        return mPresenter.onRequestUserInfoByRid(userInfoByRidRequest);
    }

    private void initInjectData() {
        DaggerMainComponent.builder().appComponent(getAppComponent())
                .mainModule(new MainModule(MainActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    /**
     * 捕捉返回事件按钮
     * <p>
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
                this.exitApp();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private long exitTime = 0;

    /**
     * 退出程序
     */
    private void exitApp() {
        // 判断2次点击事件时间
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(getResources().getString(R.string.exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
