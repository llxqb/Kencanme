package com.shushan.kencanme.app.entity.request;

/**
 * 创建AHDI支付订单
 */
public class RequestOrderAHDIRequest {
    public String token;
    /**
     * 1购买会员2购买嗨豆
     */
    public String type;
    /**
     * 对应关联id
     */
    public String relation_id;
    /**
     * 印尼盾
     */
    public String money;
}
