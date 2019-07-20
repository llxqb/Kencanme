package com.shushan.kencanme.app.mvp.ui.activity.login;

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
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.di.components.DaggerLoginComponent;
import com.shushan.kencanme.app.di.components.LoginComponent;
import com.shushan.kencanme.app.di.modules.ActivityModule;
import com.shushan.kencanme.app.di.modules.LoginModule;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.base.BaseActivity;
import com.shushan.kencanme.app.entity.request.LoginRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.LoginResponse;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.help.FacebookLoginHelper;
import com.shushan.kencanme.app.help.GoogleLoginHelper;
import com.shushan.kencanme.app.mvp.ui.activity.main.MainActivity;
import com.shushan.kencanme.app.mvp.ui.activity.personInfo.CreatePersonalInfoActivity;
import com.shushan.kencanme.app.mvp.utils.LoginUtils;
import com.shushan.kencanme.app.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.app.mvp.utils.SystemUtils;
import com.shushan.kencanme.app.mvp.views.dialog.LoginDialog;
import com.umeng.facebook.login.LoginResult;

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
    private FacebookLoginHelper faceBookLoginManager;

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
        initFaceBookLogin();
    }


    @Override
    public void initData() {

    }

    private void initFaceBookLogin() {
        faceBookLoginManager = new FacebookLoginHelper(new FacebookLoginHelper.OnLoginSuccessListener() {
            @Override
            public void onSuccess(LoginResult result) {
                //登录成功
            }
        });
        faceBookLoginManager.initFaceBook(getApplicationContext());
    }

    @OnClick({R.id.login_google_rl, R.id.login_facebook_rl, R.id.login_whats_app_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_google_rl:
                //Google登录
                showLoading(getResources().getString(R.string.loading));
                GoogleLoginHelper.googleLogin(this);
                break;
            case R.id.login_facebook_rl:
                //facebook登录
                faceBookLoginManager.faceBookLogin(this);
                break;
            case R.id.login_whats_app_rl:
                //WhatsApp登录
                startActivitys(MainActivity.class);
                break;
        }
    }


    /**
     * 登录回调
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            //facebook回调  64206
            FacebookLoginHelper.mFaceBookCallBack.onActivityResult(requestCode, resultCode, data);
        }
    }

    //处理google回调
    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("ddd", "handleSignInResult----" + result.isSuccess());
        dismissLoading();
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            //登录后台系统
            assert account != null;
            appLogin(account.getId(), account.getIdToken());
        } else {
            showToast(getResources().getString(R.string.login_google_fail));
        }
    }

    private void appLogin(String gId, String accessToken) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.id = gId;
        loginRequest.deviceId = SystemUtils.getDeviceId(this);
        loginRequest.access_token = accessToken;
        loginRequest.from = "android";
//        LogUtils.e("loginRequest:"+new Gson().toJson(loginRequest));
        mPresenterLogin.onRequestLogin(loginRequest);
    }

    @Override
    public void loginSuccess(LoginResponse response) {
        LoginResponse.UserinfoBean userinfoBean = response.getUserinfo();
        mSharePreferenceUtil.setData("ryToken", userinfoBean.getRongyun_token());
        mSharePreferenceUtil.setData("rongId", userinfoBean.getRongyun_third_id());
        mSharePreferenceUtil.setData("code", userinfoBean.getCode());//邀请码
        //根据token请求个人信息
        PersonalInfoRequest request = new PersonalInfoRequest();
        request.token = userinfoBean.getToken();
        mPresenterLogin.onRequestPersonalInfo(request);
    }

    @Override
    public void loginFail(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void personalInfoSuccess(PersonalInfoResponse personalInfoResponse) {
        GoogleLoginHelper.exitGoogleLogin();//执行退出登录  符合当前登录逻辑
        faceBookLoginManager.faceBookLoginOut();
        //保存用户信息
        mBuProcessor.setLoginUser(LoginUtils.tranLoginUser(personalInfoResponse));
        //没创建资料跳转到CreatePersonalInfoActivity  否则跳转到MainActivity
        if (mBuProcessor.isFinishFirstWrite()) {
            startActivitys(MainActivity.class);
            finish();
        } else {
            startActivitys(CreatePersonalInfoActivity.class);
            finish();
        }
    }

    @Override
    public void personalInfoFail(String errorMsg) {

    }


    @Override
    public void loginDialogBtnOkListener() {
        startActivitys(CreatePersonalInfoActivity.class);
//        finish();
    }


    private void initializeInjector() {
        LoginComponent mLoginComponent = DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(LoginActivity.this, this))
                .activityModule(new ActivityModule(this)).build();
        mLoginComponent.inject(this);
    }
}
