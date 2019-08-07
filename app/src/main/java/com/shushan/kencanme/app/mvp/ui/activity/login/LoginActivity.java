package com.shushan.kencanme.app.mvp.ui.activity.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import com.shushan.kencanme.app.entity.request.FacebookLoginRequest;
import com.shushan.kencanme.app.entity.request.LoginRequest;
import com.shushan.kencanme.app.entity.request.PersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.FacebookLoginResponse;
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
import com.tbruyelle.rxpermissions2.RxPermissions;

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

    @OnClick({R.id.login_google_rl, R.id.login_facebook_rl, R.id.login_whats_app_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_google_rl:
                //Google登录
                showLoading(getResources().getString(R.string.loading));
                GoogleLoginHelper.googleLogin(this);
                //测试账户
//                appGoogleLogin("112699516221168481510","eyJhbGciOiJSUzI1NiIsImtpZCI6ImRmMzc1ODkwOGI3OTIyOTNhZDk3N2EwYjk5MWQ5OGE3N2Y0ZWVlY2QiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI2MTE4NzAwMzE2NjAtb2pqdWo3cXVybGlwbG9vcDcxM2hvY2Y3ajVzZ3JjcGMuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI2MTE4NzAwMzE2NjAtNWY0dDFiZjMwajFnajgwY2JrY3ZjaTByMG9nOWtjb3IuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMTI2OTk1MTYyMjExNjg0ODE1MTAiLCJlbWFpbCI6ImxseHFiX3R0QDEyNi5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6Iueri-WImCIsInBpY3R1cmUiOiJodHRwczovL2xoNC5nb29nbGV1c2VyY29udGVudC5jb20vLTZKYVRBQk9HcFlRL0FBQUFBQUFBQUFJL0FBQUFBQUFBQUFBL0FDSGkzcmM4TjVfaHRtQ2ptbGlLZm04THlzbk9xT2o1MUEvczk2LWMvcGhvdG8uanBnIiwiZ2l2ZW5fbmFtZSI6IuWImCIsImZhbWlseV9uYW1lIjoi56uLIiwibG9jYWxlIjoiemgtQ04iLCJpYXQiOjE1NjQ0Nzc5ODEsImV4cCI6MTU2NDQ4MTU4MX0.F9cTe0167tdoCMnd-oalxoBnnZaturAFMXDB_xK30vFe9-jR7mZIGd7NfIrLohiVwmP6A7qMjnLpNHHRxVsrAYEq7cEy9b74Dy-c6p2bYp33GZbw5BUKurFHkdnHQeF1VbkBZn_r-NgBEkm1Y-s7a6CcbimdBWx9KNePVQOAf7Dt0Ph3JYwGmi4Twl2bldN1g_V7x_OuRQUW9WSSCfO3zBW2M0OW0uuoCtvWk-l4HDVay1vXR_3c2493bb3y7hsk9zpnk3Ox969cfn5C3ubF7TP7LYbK8O_y70GB9gWPwYKc5NT46kxx_qeZBNR3FA3YUkHd8kCHnduKATlz_44ooQ");
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
        dismissLoading();
        if (result!=null && result.isSuccess()) {
//        Log.e("ddd", "handleSignInResult----" + result.isSuccess());
            GoogleSignInAccount account = result.getSignInAccount();
            //登录后台系统
            assert account != null;
            appGoogleLogin(account.getId(), account.getIdToken());
        } else {
            showToast(getResources().getString(R.string.login_google_fail));
        }
    }

    private void appGoogleLogin(String gId, String accessToken) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.id = gId;
        loginRequest.deviceId = SystemUtils.getDeviceId(this);
        loginRequest.access_token = accessToken;
        loginRequest.from = Constant.FROM;
        mPresenterLogin.onRequestLogin(loginRequest);
    }

    @Override
    public void googleLoginSuccess(LoginResponse response) {
        LoginResponse.UserinfoBean userinfoBean = response.getUserinfo();
        mSharePreferenceUtil.setData("ryToken", userinfoBean.getRongyun_token());
        mSharePreferenceUtil.setData("rongId", userinfoBean.getRongyun_third_id());
        mSharePreferenceUtil.setData("code", userinfoBean.getCode());//邀请码
        checkPermissions(userinfoBean.getToken());
    }

    private void initFaceBookLogin() {
        faceBookLoginManager = new FacebookLoginHelper(result -> {
            //登录成功
            FacebookLoginRequest facebookLoginRequest = new FacebookLoginRequest();
            if (result != null) {
                showToast("facebook-token:" + result.getAccessToken().getToken());
                facebookLoginRequest.access_token = result.getAccessToken().getToken();
            }
            facebookLoginRequest.from = Constant.FROM;
            facebookLoginRequest.deviceId = SystemUtils.getDeviceId(LoginActivity.this);
            mPresenterLogin.onRequestLoginFacebook(facebookLoginRequest);

        });
        faceBookLoginManager.initFaceBook(getApplicationContext());
    }

    @Override
    public void facebookLoginSuccess(FacebookLoginResponse facebookLoginResponse) {
        FacebookLoginResponse.UserinfoBean userinfoBean = facebookLoginResponse.getUserinfo();
        mSharePreferenceUtil.setData("ryToken", userinfoBean.getRongyun_token());
        mSharePreferenceUtil.setData("rongId", userinfoBean.getRongyun_third_id());
        mSharePreferenceUtil.setData("code", userinfoBean.getCode());//邀请码
        checkPermissions(userinfoBean.getToken());
    }

    /**
     * 检查app 权限
     */
    @SuppressLint("CheckResult")
    private void checkPermissions(String token) {
        RxPermissions mRxPermissions = new RxPermissions(this);
        mRxPermissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ).subscribe(permission -> {
            if (permission) {
                reqPersonalInfo(token);
            } else {
                showToast(getResources().getString(R.string.login_open_permission));
            }
        });
    }

    /**
     * 根据token请求个人信息
     */
    private void reqPersonalInfo(String token) {
        PersonalInfoRequest request = new PersonalInfoRequest();
        request.token = token;
        mPresenterLogin.onRequestPersonalInfo(request);
    }


    @Override
    public void personalInfoSuccess(PersonalInfoResponse personalInfoResponse) {
        GoogleLoginHelper.exitGoogleLogin();//执行退出登录  符合当前登录逻辑
        if (faceBookLoginManager != null) {
            faceBookLoginManager.faceBookLoginOut();
        }
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
