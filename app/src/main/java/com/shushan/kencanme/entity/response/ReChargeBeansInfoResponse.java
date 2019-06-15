package com.shushan.kencanme.entity.response;

import java.util.List;

/**
 * 购买嗨豆信息list
 */
public class ReChargeBeansInfoResponse {
    private List<BeansinfoBean> beansinfo;

    public List<BeansinfoBean> getBeansinfo() {
        return beansinfo;
    }

    public void setBeansinfo(List<BeansinfoBean> beansinfo) {
        this.beansinfo = beansinfo;
    }

    public static class BeansinfoBean {
        /**
         * b_id : 1
         * amount : 588
         * describe : 首选赠送100
         * price : 45
         * give : 100
         * vip_give : 100
         */

        private int b_id;
        private int amount;
        private String describe;
        private int price;
        private int give;
        private int vip_give;

        public int getB_id() {
            return b_id;
        }

        public void setB_id(int b_id) {
            this.b_id = b_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getGive() {
            return give;
        }

        public void setGive(int give) {
            this.give = give;
        }

        public int getVip_give() {
            return vip_give;
        }

        public void setVip_give(int vip_give) {
            this.vip_give = vip_give;
        }
    }
}
