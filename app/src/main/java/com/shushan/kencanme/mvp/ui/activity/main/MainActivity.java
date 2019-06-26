package com.shushan.kencanme.mvp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
        if (!mBuProcessor.isValidLogin()) {
            startActivitys(LoginActivity.class);
            finish();
        } else {
            if(!mBuProcessor.isFinishFirstWrite()){
                startActivitys(CreatePersonalInfoActivity.class);
                finish();
            }
            LoginUser loginUser = mBuProcessor.getLoginUser();
            LogUtils.e( "loginUser:" + new Gson().toJson(mBuProcessor.getLoginUser()));
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
        reqMessageId();
    }

    private void reqMessageId(){
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMessageId(tokenRequest);
    }
    @Override
    public void messageIdSuccess(MessageIdResponse messageIdResponse) {
        mSharePreferenceUtil.saveObjData("messageIdList",messageIdResponse.getData());
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

}
