package com.shushan.kencanme.mvp.ui.activity.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerLoginComponent;
import com.shushan.kencanme.di.components.LoginComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.LoginModule;
import com.shushan.kencanme.entity.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.LoginRequest;
import com.shushan.kencanme.entity.response.LoginResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.mvp.ui.activity.main.MainActivity;
import com.shushan.kencanme.mvp.ui.activity.personInfo.CreatePersonalInfoActivity;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.dialog.LoginDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginControl.LoginView, LoginDialog.LoginDialogListener {

    @BindView(R.id.logo_iv)
    ImageView mLogoIv;
    @BindView(R.id.app_name_tv)
    TextView mAppNameTv;
    @BindView(R.id.login_hint_tv)
    TextView mLoginHintTv;
    @BindView(R.id.login_google_rl)
    RelativeLayout mLoginGoogleRl;
    @BindView(R.id.login_facebook_rl)
    RelativeLayout mLoginFacebookRl;
    @BindView(R.id.login_whats_app_rl)
    RelativeLayout mLoginWhatsAppRl;

    @Inject
    LoginControl.PresenterLogin mPresenterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(this, null);
        initializeInjector();
        initView();
        initData();
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
    }


    @Override
    public void initData() {

    }

    @OnClick({R.id.login_google_rl, R.id.login_facebook_rl, R.id.login_whats_app_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_google_rl:
                //Google登录
//                LoginDialog loginDialog = LoginDialog.newInstance();
//                loginDialog.setListener(this);
//                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), loginDialog, LoginDialog.TAG);
                showLoading("登录中");
//                new GoogleLoginHelper(this).googleLogin();
                mGoogleLoginHelper.googleLogin(this);
                break;
            case R.id.login_facebook_rl:
                //facebook登录
                break;
            case R.id.login_whats_app_rl:
                //WhatsApp登录
                startActivitys(MainActivity.class);
                break;
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // 已登录 account 不为空
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//    }

    /**
     * 登录回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("ddd", "handleSignInResult----" + result.isSuccess());
//        Log.e("ddd",new Gson().toJson(result));
        dismissLoading();
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
//            Log.e("ddd", "id--------" + account.getId() + "----name----" + account.getDisplayName() + "---photo--" + account.getPhotoUrl() + " token:" + account.getIdToken());
            //登录后台系统
            appLogin(account.getIdToken());
        }
    }


    private void appLogin(String accessToken) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.deviceId = "868040033198091";
        loginRequest.access_token = accessToken;
        loginRequest.from = "android";
        mPresenterLogin.onRequestLogin(loginRequest);
    }

    @Override
    public void loginSuccess(LoginResponse response) {
        LoginResponse.UserinfoBean userinfoBean = response.getUserinfo();
        tranLoginUser(userinfoBean);
        startActivitys(CreatePersonalInfoActivity.class);
        finish();
    }

    @Override
    public void loginFail(String errorMsg) {
        showToast(errorMsg);
    }



    @Override
    public void loginDialogBtnOkListener() {
        startActivitys(CreatePersonalInfoActivity.class);
//        finish();
    }

    /**
     * 把LoginResponse.UserinfoBean 转换为LoginUser
     *
     * @param userinfoBean UserinfoBean
     */
    private void tranLoginUser(LoginResponse.UserinfoBean userinfoBean) {
        if (userinfoBean != null) {
            LoginUser loginUser = new LoginUser();
            loginUser.token = userinfoBean.getToken();
            loginUser.nickname = userinfoBean.getNickname();
            loginUser.rongyun_token = userinfoBean.getRongyun_token();
//            mSharePreferenceUtil.saveObjData("user", loginUser);
            mBuProcessor.setLoginUser(loginUser);
        }
    }

    private void initializeInjector() {
        LoginComponent mLoginComponent = DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(LoginActivity.this, this))
                .activityModule(new ActivityModule(this)).build();
        mLoginComponent.inject(this);
    }
}
