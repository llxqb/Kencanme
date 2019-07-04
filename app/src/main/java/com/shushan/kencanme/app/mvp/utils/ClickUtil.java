package com.shushan.kencanme.app.mvp.utils;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * 点击事件的工具类
 */

public class ClickUtil {

    //activity中的view的集合
    private static Map<Activity, SparseArray<Long>> viewIdMap = new HashMap<>();

    public static boolean isDoubleClick(Activity activity, View view) {
        return isDoubleClick(activity, 1000, view);
    }

    //遍历当前activity内的view对应的点击时间
    public static boolean isDoubleClick(Activity activity, long time, View view) {

        //判断当前activity是否有对应的数据
        if (!viewIdMap.containsKey(activity)) {
            SparseArray<Long> viewIdArray = new SparseArray<>();
            viewIdArray.put(view.getId(), System.currentTimeMillis());
            viewIdMap.put(activity, viewIdArray);
            return false;
        }

        //view对应的点击时间数据
        SparseArray<Long> viewIdArray = viewIdMap.get(activity);

        //rxJava循环
        return Flowable.just(viewIdArray).subscribeOn(Schedulers.newThread()) //子线程内
                //遍历输出view的id：转换为string格式，long格式在null值的情况下会报错
                .map(longSparseArray -> String.valueOf(viewIdArray.get(view.getId())))
                .to(longObservable -> { //view的id
                    String viewTime = longObservable.blockingFirst(); //获取view对应的点击时间
                    if (viewTime.equals("null")) { //转换为string格式，默认为 "null" 值
                        viewIdArray.put(view.getId(), System.currentTimeMillis());
                        return false;
                    } else {
                        long lastClickTime = System.currentTimeMillis();
                        if (lastClickTime - Long.parseLong(viewTime) < time) {
                            viewIdArray.put(view.getId(), lastClickTime);
                            return true;
                        }
                        viewIdArray.put(view.getId(), lastClickTime);
                        return false;
                    }
                });
    }

    //移除view的数据
    public static void removeClickView(Activity activity) {
        if (viewIdMap.containsKey(activity)) {
            viewIdMap.remove(activity);
        }
    }
}
