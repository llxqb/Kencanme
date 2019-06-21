package com.shushan.kencanme.entity.request;

/**
 * 嗨豆回复消息/查看私密照片
 */
public class UseBeansRequest {
    public String token;
    /**
     * 3查看私密照片4男性回复女性消息
     */
    public String type;
    public String beans;
    /**
     * 消息id
     */
    public String message_id;
}
