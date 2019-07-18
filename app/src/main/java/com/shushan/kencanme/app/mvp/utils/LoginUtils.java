package com.shushan.kencanme.app.mvp.utils;

import com.google.gson.Gson;
import com.shushan.kencanme.app.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.app.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.app.entity.user.LoginUser;

public class LoginUtils {

    /**
     * 请求个人信息时调用
     * PersonalInfoResponse 转换位LoginUser
     */
    public static LoginUser tranLoginUser(PersonalInfoResponse personalInfoResponse) {
        LoginUser loginUser = new LoginUser();
        loginUser.uid = personalInfoResponse.getUid();
        loginUser.nickname = personalInfoResponse.getNickname();
        loginUser.trait = personalInfoResponse.getTrait();
        loginUser.cover = personalInfoResponse.getCover();
        loginUser.sex = personalInfoResponse.getSex();
        loginUser.birthday = personalInfoResponse.getBirthday();
        loginUser.city = personalInfoResponse.getCity();
        loginUser.declaration = personalInfoResponse.getDeclaration();
        loginUser.vip = personalInfoResponse.getVip();
        loginUser.vip_time = personalInfoResponse.getVip_time();
        loginUser.svip = personalInfoResponse.getSvip();
        loginUser.height = String.valueOf(personalInfoResponse.getHeight());
        loginUser.weight = personalInfoResponse.getWeight();
        loginUser.bust = personalInfoResponse.getBust();
        loginUser.occupation = personalInfoResponse.getOccupation();
        loginUser.beans = personalInfoResponse.getBeans();
        loginUser.token = personalInfoResponse.getToken();
        loginUser.age = personalInfoResponse.getAge();
        loginUser.forbidden = personalInfoResponse.getForbidden();
        loginUser.pushing_small_age = personalInfoResponse.getPushing_small_age();
        loginUser.pushing_large_age = personalInfoResponse.getPushing_large_age();
        loginUser.pushing_gender = personalInfoResponse.getPushing_gender();
        loginUser.exposure = personalInfoResponse.getExposure();
        loginUser.last_login_time = personalInfoResponse.getLast_login_time();
        loginUser.contact = new Gson().toJson(personalInfoResponse.getContact());
        loginUser.label = new Gson().toJson(personalInfoResponse.getLabel());
        return loginUser;
    }


    /**
     * LoginUser 转换为 UpdatePersonalInfoRequest
     * 只在登录才用到
     */
    public static UpdatePersonalInfoRequest tranPersonalInfoResponse(LoginUser loginUser) {
        UpdatePersonalInfoRequest updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
        updatePersonalInfoRequest.nickname = loginUser.nickname;
        updatePersonalInfoRequest.trait = loginUser.trait;
        updatePersonalInfoRequest.cover = loginUser.cover;
        updatePersonalInfoRequest.sex = loginUser.sex;
        updatePersonalInfoRequest.birthday = loginUser.birthday;
        updatePersonalInfoRequest.city = loginUser.city;
        updatePersonalInfoRequest.declaration = loginUser.declaration;
        updatePersonalInfoRequest.height = loginUser.height;
        updatePersonalInfoRequest.weight = loginUser.weight;
        updatePersonalInfoRequest.bust = loginUser.bust;
        updatePersonalInfoRequest.occupation = loginUser.occupation;
        updatePersonalInfoRequest.token = loginUser.token;
        updatePersonalInfoRequest.pushing_small_age = loginUser.pushing_small_age;
        updatePersonalInfoRequest.pushing_large_age = loginUser.pushing_large_age;
        updatePersonalInfoRequest.pushing_gender = loginUser.pushing_gender;
        return updatePersonalInfoRequest;
    }


}