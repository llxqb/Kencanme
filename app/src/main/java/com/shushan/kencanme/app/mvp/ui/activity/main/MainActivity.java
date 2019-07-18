package com.shushan.kencanme.app.mvp.ui.activity.main;

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
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerMainComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.MainModule;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.TokenRequest;
import com.shushan.kencanme.app.entity.request.UserInfoByRidRequest;
import com.shushan.kencanme.app.entity.response.MessageIdResponse;
import com.shushan.kencanme.app.help.RongCloudHelper;
import com.shushan.kencanme.app.mvp.ui.activity.login.LoginActivity;
import com.shushan.kencanme.app.mvp.ui.activity.rongCloud.CustomizeMessageItemProvider;
import com.shushan.kencanme.app.mvp.ui.adapter.MyFragmentAdapter;
import com.shushan.kencanme.app.mvp.ui.fragment.HomeFragment;
import com.shushan.kencanme.app.mvp.ui.fragment.MessageFragment;
import com.shushan.kencanme.app.mvp.ui.fragment.MineFragment;
import com.shushan.kencanme.app.mvp.views.MyNoScrollViewPager;

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
        showToast("进来了main");
        if (!mBuProcessor.isValidLogin() || !mBuProcessor.isFinishFirstWrite()) {
            startActivitys(LoginActivity.class);
            finish();
        } else {
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
        reqMessageId();
    }



    private void connectRongCloud() {
        String rToken = mSharePreferenceUtil.getData("ryToken");
//        LogUtils.d("rToken:" + rToken);
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

    /**
     * 请求使用嗨豆查看私密照片message_id
     */
    private void reqMessageId() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMessageId(tokenRequest);
    }

    @Override
    public void messageIdSuccess(MessageIdResponse messageIdResponse) {
        //根据messageIdList 对比查询出已经查看过的图片消息id ,(融云消息有唯一的uid)
        mSharePreferenceUtil.saveObjData("messageIdList", messageIdResponse.getData());
        CustomizeMessageItemProvider.setMessageList(messageIdResponse.getData());
    }

    /**
     * 获取融云列表用户头像和昵称
     */
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
