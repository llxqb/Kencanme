package com.shushan.kencanme.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.mvp.utils.SharePreferenceUtil;

import javax.inject.Inject;

public class GoogleLoginHelper {
    @Inject
    public GoogleLoginHelper() {
    }

    @Inject
    protected SharePreferenceUtil mSharePreferenceUtil;

    public GoogleApiClient mGoogleApiClient;

    public void googleLogin(Context context) {
        //初始化
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("611870031660-21c3miohhpbfd40v8t2ou30e3eshmf7a.apps.googleusercontent.com")
                .build();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage((FragmentActivity) context, connectionResult -> {
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        //进行谷歌登录
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) context).startActivityForResult(intent, Constant.GOOGLE_LOGIN);  //RC_SIGN_IN是requestcode
    }


    /**
     * 注销google登录
     */
    public void exitGoogleLogin() {
        if (mGoogleApiClient != null) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            mGoogleApiClient = null;
        }
    }

    /**
     * 退出登录
     */
//    private void signOut() {
//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                    }
//                });
//    }

}
