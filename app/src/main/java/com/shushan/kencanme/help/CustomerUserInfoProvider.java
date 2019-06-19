package com.shushan.kencanme.help;

import android.net.Uri;

import com.shushan.kencanme.entity.user.LoginUser;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class CustomerUserInfoProvider implements RongIM.UserInfoProvider {
    private LoginUser mLoginUser;
    private String mRongId;

    public CustomerUserInfoProvider(String rongId, LoginUser loginUser) {
        mLoginUser = loginUser;
        mRongId = rongId;
    }

    private UserInfo uInfo;

    /**
     * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
     * <p>
     * userInfoProvider 用户信息提供者。
     * isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
     * 如果 App 提供的 UserInfoProvider
     * 每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
     * 此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
     * UserInfoProvider
     */
    @Override
    public UserInfo getUserInfo(String userId) {

        uInfo = new UserInfo(userId, mLoginUser.nickname, Uri.parse(mLoginUser.trait));
        //刷新用户信息  可以刷新会话列表
        RongIM.getInstance().refreshUserInfoCache(uInfo);
        return uInfo;
    }
}
