package com.shushan.kencanme.mvp.utils;

import java.util.List;

/**
 * desc:数据相关
 */
public class DataUtils {
    /**
     * json字符串转list
     */
    public static boolean isContainString(List<String> list,String text){
        if(list==null) return false;
        for (String s:list){
            if(s.equals(text)){
                return true;
            }
        }
        return false;
    }
}
