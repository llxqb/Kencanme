package com.shushan.kencanme.entity.response;

import java.util.List;

/**
 * ClassName: RecommendUserInfoResponse
 * author: li.liu
 * date: 2019-05-28
 */
public class RecommendUserInfoResponse{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * picPath : http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png
         * picType : 1
         * isVip : true
         * isUseHiDou : true
         */

        public String picPath;
        public int picType;
        public boolean isVip;
        public boolean isUseHiDou;
        public boolean isPic;

    }
}
