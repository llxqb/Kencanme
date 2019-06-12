package com.shushan.kencanme.entity.request;

/**
 * ClassName: LoginRequest
 * date: 2019-05-28
 */
public class LoginRequest {
    /**
     * google id
     */
    public String id;
    /**
     * google token
     */
    public String access_token;
    /**
     * 设备id
     */
    public String deviceId;
    /**
     * 设备 android 和 ios
     */
    public String from;
}
