package com.shushan.kencanme.entity.response;

import java.util.List;

public class OpenVipResponse {


    private List<VipinfoBean> vipinfo;
    private List<String> notice;

    public List<VipinfoBean> getVipinfo() {
        return vipinfo;
    }

    public void setVipinfo(List<VipinfoBean> vipinfo) {
        this.vipinfo = vipinfo;
    }

    public List<String> getNotice() {
        return notice;
    }

    public void setNotice(List<String> notice) {
        this.notice = notice;
    }

    public static class VipinfoBean {
        /**
         * v_id : 1
         * name : 超级VIP
         * original_price : 2000000
         * special_price : 400000
         * describe : 独享：永久会员，私信免费
         * privilege : 特权：每天可以查看3个联系方式
         * give : 0
         */

        private int v_id;
        private String name;
        private String original_price;
        private String special_price;
        private String describe;
        private String privilege;
        private int give;
        public boolean isCheck;

        public int getV_id() {
            return v_id;
        }

        public void setV_id(int v_id) {
            this.v_id = v_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getSpecial_price() {
            return special_price;
        }

        public void setSpecial_price(String special_price) {
            this.special_price = special_price;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getPrivilege() {
            return privilege;
        }

        public void setPrivilege(String privilege) {
            this.privilege = privilege;
        }

        public int getGive() {
            return give;
        }

        public void setGive(int give) {
            this.give = give;
        }
    }
}
