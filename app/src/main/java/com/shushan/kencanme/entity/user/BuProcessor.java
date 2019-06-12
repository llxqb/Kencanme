package com.shushan.kencanme.entity.user;

import android.content.Context;
import android.text.TextUtils;

import com.shushan.kencanme.entity.SpConstant;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.mvp.utils.SharePreferenceUtil;

import javax.inject.Inject;

/**
 * BuProcessor
 */
public class BuProcessor {
    //    private LoginUser mLoginUser = new LoginUser();
    private final SharePreferenceUtil mSharePreferenceUtil;
    PersonalInfoResponse loginUser;
    @Inject
    public BuProcessor(Context context, SharePreferenceUtil mSharePreferenceUtil) {
        this.mSharePreferenceUtil = mSharePreferenceUtil;
        loginUser = (PersonalInfoResponse) mSharePreferenceUtil.readObjData("user");
    }

    public boolean isValidLogin() {
        return loginUser != null && !TextUtils.isEmpty(loginUser.getToken());
    }


    public String getToken() {
        return  loginUser != null?loginUser.getToken():null;
    }

    public PersonalInfoResponse getLoginUser() {
        return (PersonalInfoResponse) mSharePreferenceUtil.readObjData("user");
    }

    public void setLoginUser(PersonalInfoResponse loginUser) {
        mSharePreferenceUtil.saveObjData(SpConstant.LOGIN_USER, loginUser);
    }

    /**
     * 验证是否第一次完善资料
     * 用cover字段判断
     */
    public boolean isFinishFirstWrite() {
        return loginUser != null && !TextUtils.isEmpty(loginUser.getCover());
    }




    public PersonalInfoResponse reSetUserData() {
        // 恢复用户相关
//        Object o1 = mSharePreferenceUtil.readObjData(SpConstant.LOGIN_USER);
//        if (o1 != null && o1 instanceof LoginUser) {
//            mLoginUser = (LoginUser) o1;
//            return mLoginUser;
//        }
        return null;
    }

    //退出登录清除数据
    public void clearLoginUser() {
        // 清空用户
        PersonalInfoResponse loginUser = (PersonalInfoResponse) mSharePreferenceUtil.readObjData("user");
        if (loginUser != null) {
            mSharePreferenceUtil.saveObjData(SpConstant.LOGIN_USER, "");
        }
    }
}
