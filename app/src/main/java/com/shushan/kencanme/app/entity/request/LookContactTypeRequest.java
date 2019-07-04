package com.shushan.kencanme.app.entity.request;

/**
 * 查看联系方式
 */
public class LookContactTypeRequest {
    public String token;
    public String uid;
    /**
     * 1使用vip次数2使用嗨豆
     */
    public String type;
}
