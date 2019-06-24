package com.shushan.kencanme.entity.request;

public class ReportUserRequest {
    public String token;

    public String uid;
    /**
     * 图片数组字符串
     */
    public String pics;
    public String describe;
    /**
     * 1 头像、资料作假
     * 2 广告、营销
     * 3 诈骗、托儿
     * 4 色情、低俗
     * 5 恶意骚扰、不文明语言
     * 6 其他
     */
    public String reason;

}
