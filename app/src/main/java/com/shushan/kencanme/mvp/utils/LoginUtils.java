package com.shushan.kencanme.mvp.utils;

import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;

public class LoginUtils {

    /**
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
        loginUser.height = personalInfoResponse.getHeight();
        loginUser.weight = personalInfoResponse.getWeight();
        loginUser.bust = personalInfoResponse.getBust();
        loginUser.occupation = personalInfoResponse.getOccupation();
        loginUser.beans = personalInfoResponse.getBeans();
        loginUser.token = personalInfoResponse.getToken();
        loginUser.age = personalInfoResponse.getAge();
        loginUser.forbidden = personalInfoResponse.getForbidden();
//        loginUser.pushing_age = personalInfoResponse.getPushing_age();
        loginUser.pushing_small_age = personalInfoResponse.pushing_small_age;
        loginUser.pushing_large_age = personalInfoResponse.pushing_large_age;
        loginUser.pushing_gender = personalInfoResponse.getPushing_gender();
        loginUser.exposure = personalInfoResponse.getExposure();
        loginUser.last_login_time = personalInfoResponse.getLast_login_time();
        return loginUser;
    }

    /**
     * LoginUser 转换为 UpdatePersonalInfoRequest
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
