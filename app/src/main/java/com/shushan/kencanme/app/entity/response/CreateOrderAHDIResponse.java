package com.shushan.kencanme.app.entity.response;

public class CreateOrderAHDIResponse {

    /**
     * appid : 1002005
     * app_userid : 62817786361010
     * order_no : 201907171451249245
     * token : HXjRgV-nHn7Cesvx6iLOYyAABdLsVsAAAASha_6zL7Y
     */

    private String appid;
    private String app_userid;
    private String order_no;
    private String token;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getApp_userid() {
        return app_userid;
    }

    public void setApp_userid(String app_userid) {
        this.app_userid = app_userid;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
