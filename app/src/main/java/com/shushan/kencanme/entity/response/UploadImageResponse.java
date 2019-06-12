package com.shushan.kencanme.entity.response;

/**
 * 上传图片
 */
public class UploadImageResponse {

    /**
     * error : 0
     * msg : success
     * data : https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190611/5cff965473abf.png
     */

    private int error;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
