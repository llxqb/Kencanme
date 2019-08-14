package com.shushan.kencanme.app.entity.response;

/**
 * 上传视频返回bean
 */
public class UploadVideoResponse {

    /**
     * url : https://www.kencanme.com/Public/uploads/video/20190813/be0598712121b163d103b24e9db710fc.mp4
     * taskId : vi7xE6dflTHc16$11ucGNwjk-1r8yk8
     */

    private String url;
    private String taskId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
