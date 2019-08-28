package com.shushan.kencanme.app.entity.response;

/**
 * 上传设备信息 response
 */
public class UploadDeviceResponse {

    /**
     * versionCode : 31
     * versionName : 1.1.1
     * versionDescription :
     */

    private String versionCode;
    private String versionName;
    private String versionDescription;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDescription() {
        return versionDescription;
    }

    public void setVersionDescription(String versionDescription) {
        this.versionDescription = versionDescription;
    }
}
