package com.shushan.kencanme.mvp.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.LocationManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

/**
 * Created by li.liu on 2018/10/16.
 * SystemUtil
 */

public class SystemUtils {

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    public static void getPixelSize(Activity activity) {
        // 通过Activity类中的getWindowManager()方法获取窗口管理，再调用getDefaultDisplay()方法获取获取Display对象
        Display display = activity.getWindowManager().getDefaultDisplay();

        // 方法一(推荐使用)使用Point来保存屏幕宽、高两个数据
        Point outSize = new Point();
        // 通过Display对象获取屏幕宽、高数据并保存到Point对象中
        display.getSize(outSize);
        // 从Point对象中获取宽、高
        int x = outSize.x;
        int y = outSize.y;
        // 通过吐司显示屏幕宽、高数据
        Log.e("SystemUtils", "手机像素为:" + x + " y:" + y);
    }

    public static void getMinDp(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;
        float density = dm.density;
        float heightDP = heightPixels / density;
        float widthDP = widthPixels / density;
        float smallestWidthDP;
        if (widthDP < heightDP) {
            smallestWidthDP = widthDP;
        } else {
            smallestWidthDP = heightDP;
        }
        Log.e("SystemUtils", "手机最小宽度为:" + smallestWidthDP);
    }

    /**
     * 获取当前进程名称
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return "";
    }

    /**
     * 获取分辨率_宽
     *
     * @param activity
     * @return
     */
    public static int getWidthPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取分辨率_高
     *
     * @param activity
     * @return
     */
    public static int getHeightPixels(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }



    /**
     * 检查GPS是否开启
     */
    public static boolean checkGPSIsOpen(Context context) {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }
}
