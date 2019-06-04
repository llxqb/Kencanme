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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerLoginComponent;
import com.shushan.kencanme.di.components.LoginComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.LoginModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.LoginRequest;
import com.shushan.kencanme.entity.response.LoginResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.Person_info.CreatePersonalInfoActivity;
import com.shushan.kencanme.mvp.ui.activity.main.MainActivity;
import com.shushan.kencanme.mvp.utils.LogUtils;
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
    private GoogleApiClient mGoogleApiClient;

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
        initGoogleLogin();
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
    }

    private void appLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.deviceId = "868040033198091";
        loginRequest.mobile = "18684923583";
        loginRequest.password = "e10adc3949ba59abbe56e057f20f883e";
        loginRequest.platform = "android";
        mPresenterLogin.onRequestLogin(loginRequest);
//        startActivitys(MainActivity.class);
//        finish();
    }


    @Override
    public void initData() {

    }

    @OnClick({R.id.login_google_rl, R.id.login_facebook_rl, R.id.login_whats_app_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_google_rl:
                //Google登录
                LoginDialog loginDialog = LoginDialog.newInstance();
                loginDialog.setListener(this);
                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), loginDialog, LoginDialog.TAG);
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

    /**
     * 初始化谷歌登录信息
     */
    private void initGoogleLogin() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .requestId()
//                .requestProfile()
//                .build();
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestProfile()
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 已登录 account 不为空
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    /**
     * 进行谷歌登录
     */
    private void signInGoogle() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, 100);
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 100) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            LogUtils.e("account=" + new Gson().toJson(account));
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            LogUtils.e("signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }


    @Override
    public void loginSuccess(LoginResponse response) {

    }

    @Override
    public void showErrMessage(Throwable e) {
        Log.e("ddd", "e" + e.toString());
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mPresenterLogin.onDestroy();
//    }

    private void initializeInjector() {
        LoginComponent mLoginComponent = DaggerLoginComponent.builder().appComponent(getAppComponent())
                .loginModule(new LoginModule(LoginActivity.this, this))
                .activityModule(new ActivityModule(this)).build();
        mLoginComponent.inject(this);
    }


    @Override
    public void loginDialogBtnOkListener() {
        startActivitys(CreatePersonalInfoActivity.class);
//        finish();
    }
}
