package com.shushan.kencanme.mvp.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by li.liu on 2018/10/19.
 * 日期相关util
 */

public class DateUtil {

    public static final String TIME_YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_YYMMDD = "yyyy-MM-dd";
    public static final String TIME_YYMM = "yyyy-MM";
    public static final String TIME_YYMMDD_T_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * POS交易序号
     */
    public static int getPOSRandomDate() {
        SimpleDateFormat format = new SimpleDateFormat(TIME_YYMMDD_HHMMSS, Locale.CHINA);
        String time = "2014-01-01 00:00:00";
        try {
            Date date = format.parse(time);
            long tmp = System.currentTimeMillis() - date.getTime();
            return Math.abs((int) tmp / 1000);
        } catch (ParseException ex) {
            return 0;
        }
    }


    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_YYMMDD_HHMMSS);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 根据pattern获取当前时间
     */
    public static String getTimeByPattern(String pattern) {
        Date date = getDate();
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
        return df.format(date);
    }


    /**
     * 日期转换为字符串
     */
    public static String dateTranString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern,Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 获取n天前的时间戳（秒）
     */
    public static int beforeNDateStamp(int n) {
        return (int) (System.currentTimeMillis() / 1000) - 86400 * n;
    }

    public static String transformTimestamp(int timestamp, String pattern) {
        try {
            Date date = new Date(timestamp * 1000L);
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
            String d = format.format(date);
            return d;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 时间戳转换成字符串
     */
    public static String getStrTime(long cc_time, String pattern) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        re_StrTime = sdf.format(new Date(cc_time * 1000L));
        return re_StrTime;
    }



    /**
     * 浏览器时间转字符串
     */
    public static String getDateToString(String dateString, String pattern) {
        if (TextUtils.isEmpty(dateString)) return "";
        Date date = new Date(Long.parseLong(dateString));
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(date);
    }


    public static Date formatPayDate(String time) {
        Date date = new Date();
        try {
            String tmp = time.replace("T", " ").split("\\.")[0];
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(tmp);
        } catch (Exception ex) {
        }
        return date;
    }

    /**
     * 获取某月份天数
     * b
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间：long类型
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis() / 1000;
    }

}
