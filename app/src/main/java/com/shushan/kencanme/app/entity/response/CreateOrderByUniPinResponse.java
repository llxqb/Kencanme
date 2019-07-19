package com.shushan.kencanme.app.entity.response;

public class CreateOrderByUniPinResponse {
    /**
     * order_no :
     * url : https://dev.unipin.com/unibox/d/TX3n1563439021pPNk4xDZAy65?lg=en
     */

    private String order_no;
    private String url;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
