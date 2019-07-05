package com.shushan.kencanme.app.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.Constants.ServerConstant;

public class GoogleLoginHelper {

    private static GoogleApiClient mGoogleApiClient;

    public static void googleLogin(Context context) {
        //初始化
        //重点：用的Web 应用 客户端id 为秘钥 下载json也是web 端的
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(ServerConstant.GOOGLE_LOGIN_KEY)
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
    public static void exitGoogleLogin() {
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
