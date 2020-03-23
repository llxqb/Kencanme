package com.shushan.kencanme.app.help;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.umeng.facebook.CallbackManager;
import com.umeng.facebook.FacebookCallback;
import com.umeng.facebook.FacebookException;
import com.umeng.facebook.FacebookSdk;
import com.umeng.facebook.login.LoginManager;
import com.umeng.facebook.login.LoginResult;

import java.util.Collections;

public class FacebookLoginHelper {
    public CallbackManager mFaceBookCallBack;
    //    private static FacebookLoginHelper sInstance;
    private OnLoginSuccessListener loginSuccessListener;

    public FacebookLoginHelper(OnLoginSuccessListener listener) {
        loginSuccessListener = listener;
    }

//    /**
//     * 单例
//     */
//    public static FaceBookLoginManager getInstance(OnLoginSuccessListener listener) {
//        if (sInstance == null) {
//            synchronized (FaceBookLoginManager.class) {
//                if (sInstance == null) {
//                    sInstance = new FaceBookLoginManager(listener);
//                }
//            }
//        }
//        return sInstance;
//    }


    /**
     * 初始化
     */
    public void initFaceBook(Context context) {
        FacebookSdk.sdkInitialize(context);
        mFaceBookCallBack = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFaceBookCallBack,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if (loginResult != null) {
                            Log.e("ddd", "loginResult:" + new Gson().toJson(loginResult));
                            if (loginSuccessListener != null) {
                                loginSuccessListener.onSuccess(loginResult);
                            }
                        }
                    }

                    @Override
                    public void onCancel() {
                        Log.e("ddd", "onCancel()");
//                        if (loginSuccessListener != null) {
//                            loginSuccessListener.onCancel();
//                        }
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("ddd", "onError()");
//                        if (loginSuccessListener != null) {
//                            loginSuccessListener.onError(error);
//                        }
                    }
                });

    }

    /**
     * 开始
     */
    public void faceBookLogin(Context context) {
        LoginManager.getInstance()
                .logInWithReadPermissions((Activity) context,
                        Collections.singletonList("public_profile"));
    }

    /**
     * 注销
     */
    public void faceBookLoginOut() {
        LoginManager.getInstance().logOut();
        mFaceBookCallBack = null;
    }


    public interface OnLoginSuccessListener {

        void onSuccess(LoginResult result);

//        void onCancel();
//
//        void onError(FacebookException error);
    }
}
