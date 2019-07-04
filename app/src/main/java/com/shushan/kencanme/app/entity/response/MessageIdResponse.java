package com.shushan.kencanme.app.entity.response;

import java.util.List;

/**
 * 获取融云消息 查看过的图片id
 *
 */
public class MessageIdResponse {
    /**
     * error : 0
     * msg : success
     * data : ["1","2","3"]
     */

    private int error;
    private String msg;
    private List<String> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
