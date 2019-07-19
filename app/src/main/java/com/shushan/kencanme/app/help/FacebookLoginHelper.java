package com.shushan.kencanme.app.help;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;

import java.util.Arrays;

public class FacebookLoginHelper {

    private Context mContext;

    public FacebookLoginHelper(Context context) {
        mContext = context;
        FacebookSdk.sdkInitialize(context);
    }

    public void facebookLogin() {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.WEB_ONLY);
        LoginManager.getInstance().logInWithReadPermissions((Activity) mContext, Arrays.asList("public_profile", "user_friends"));
        LoginManager.getInstance().registerCallback(callbackManager, new fackBookCallBack());
    }


    private class fackBookCallBack implements FacebookCallback {

        @Override
        public void onSuccess(Object o) {
            //登录成功
            new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                    Log.e("ddd", "currentProfile:" + new Gson().toJson(currentProfile));
                    if (currentProfile != null) {

                    }
                }
            };
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    }
}
