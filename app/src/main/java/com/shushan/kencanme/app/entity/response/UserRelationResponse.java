package com.shushan.kencanme.app.entity.response;

/**
 * 根据融云第三方id获取关系
 * 0 未喜欢  1 喜欢  2 相互喜欢/好友  3 黑名单
 */
public class UserRelationResponse {
    /**
     * state : 2
     */

    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
