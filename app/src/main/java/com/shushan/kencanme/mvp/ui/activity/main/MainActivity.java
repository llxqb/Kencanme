package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerMainComponent;
import com.shushan.kencanme.di.components.MainComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.MainModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.entity.request.TokenRequest;
import com.shushan.kencanme.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.entity.response.HomeUserInfoResponse;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.RongCloudHelper;
import com.shushan.kencanme.mvp.ui.activity.login.LoginActivity;
import com.shushan.kencanme.mvp.ui.adapter.MyFragmentAdapter;
import com.shushan.kencanme.mvp.ui.fragment.HomeFragment;
import com.shushan.kencanme.mvp.ui.fragment.MessageFragment;
import com.shushan.kencanme.mvp.ui.fragment.MineFragment;
import com.shushan.kencanme.mvp.utils.LoginUtils;
import com.shushan.kencanme.mvp.views.MyJzvdStd;
import com.shushan.kencanme.mvp.views.MyNoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener, MainControl.MainView, MyJzvdStd.MyjzvdListener, RongIM.UserInfoProvider {

    @BindView(R.id.main_bottom_navigation)
    BottomNavigationView mMainBottomNavigation;
    @BindView(R.id.main_viewpager)
    MyNoScrollViewPager mMainViewpager;
    public static final int SWITCH_HOME_PAGE = 0;
    public static final int SWITCH_MESSAGE_PAGE = 1;
    public static final int SWITCH_MINE_PAGE = 2;
    private LoginUser loginUser;
    @Inject
    MainControl.PresenterMain mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInjectData();
        ButterKnife.bind(this);
        initView();
        initData();
    }


    @Override
    public void initView() {
        if (!mBuProcessor.isValidLogin()) {
            startActivitys(LoginActivity.class);
            finish();
        } else {
            loginUser = mBuProcessor.getLoginUser();
            Log.e("ddd", "loginUser:" + new Gson().toJson(mBuProcessor.getLoginUser()));
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
        PersonalInfoRequest personalInfoRequest = new PersonalInfoRequest();
        personalInfoRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestPersonalInfo(personalInfoRequest);
    }

    private void connectRongCloud() {
        //"MbbN5DyzAEs2Vruc4Sirkac3QJl342gyNW2NyYV7fKr3kEu705lRicWjNXyo5Ok1T7F5rN+y/6ypnXiFpNArqFxA4Ai8GBqr"
        String rToken = mSharePreferenceUtil.getData("ryToken");
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
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                mMainViewpager.setCurrentItem(SWITCH_HOME_PAGE, false);
                break;
            case R.id.action_message:
                mMainViewpager.setCurrentItem(SWITCH_MESSAGE_PAGE, false);
                break;
            case R.id.action_mine:
                mMainViewpager.setCurrentItem(SWITCH_MINE_PAGE, false);
                break;
        }
        return true;
    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse response) {
        //保存用户信息
        mBuProcessor.setLoginUser(LoginUtils.tranLoginUser(response));
        requestHomeUserInfo();
    }

    @Override
    public void homeUserInfoSuccess(HomeUserInfoResponse homeUserInfoResponse) {
        LoginUser loginUser = mBuProcessor.getLoginUser();
        HomeUserInfoResponse.UserBean userBean = homeUserInfoResponse.getUser();
        //把另外几项LoginUser加入进来
        loginUser.exposure_type = userBean.getExposure_type();
        loginUser.exposure_time = userBean.getExposure_time();
        loginUser.today_like = userBean.getToday_like();
        loginUser.today_chat = userBean.getToday_chat();
        loginUser.today_see_contact = userBean.getToday_see_contact();
        mBuProcessor.setLoginUser(loginUser);
    }


    /**
     * 首页用户信息
     */
    private void requestHomeUserInfo() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestHomeUserInfo(tokenRequest);
    }

    private void initInjectData() {
        MainComponent mMainComponent = DaggerMainComponent.builder().appComponent(getAppComponent())
                .mainModule(new MainModule(MainActivity.this, this))
                .activityModule(new ActivityModule(this)).build();
        mMainComponent.inject(this);
    }


    @Override
    public void jzvdClickListener(int clickPos) {
//        ToastUtil.showToast(this,"sssssssss");
        showToast("" + clickPos);
//        LookPhotoActivity.start(this, "");//查看大图
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return findUserById(userId);
    }

    private UserInfo findUserById(String userId) {
        UserInfoByRidRequest userInfoByRidRequest = new UserInfoByRidRequest();
        userInfoByRidRequest.token = mBuProcessor.getToken();
        userInfoByRidRequest.rongyun_third_id = userId;
        UserInfo userInfo = mPresenter.onRequestUserInfoByRid(userInfoByRidRequest);
        Log.e("ddd", "userInfo" + new Gson().toJson(userInfo));
        return userInfo;
    }
}
