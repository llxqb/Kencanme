package com.shushan.kencanme.entity.request;

/**
 * 创建订单
 */
public class CreateOrderRequest {
    public String token;
    /**
     * 1购买会员2购买嗨豆
     */
    public String type;
    /**
     * 对应购买 会员/嗨豆id
     */
    public String relation_id;
    public String money;
    /**
     * 来源
     */
    public String from;
}
