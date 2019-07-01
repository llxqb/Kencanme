package com.shushan.kencanme.mvp.utils;

/**
 * kencanme_monthly_vip 类型为订阅商品（取消了）
 */
public class PayUtil {
    public static String payGoodId(String goodId) {
        String goodIdValue = DataUtils.uppercaseToLowercase(goodId);
        switch (goodIdValue) {
            case "kencanme_monthly_vip":
                goodIdValue = "kencanme_monthly_vip2";//月度vip
                break;
            case "kencanme_halfyear_vip":
                goodIdValue = "kencanme_halfyear_vi2";//半年vip
                break;
            case "kencanme_year_vip":
                goodIdValue = "kencanme_year_vip2";//一年vip
                break;
            case " kencanme_super_vip":
                goodIdValue = " kencanme_super_vip2";//超级vip
                break;
        }
        return goodIdValue;
    }
}
