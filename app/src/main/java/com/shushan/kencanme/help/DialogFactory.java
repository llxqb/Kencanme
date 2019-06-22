package com.shushan.kencanme.help;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.dialog.MessageUseBeansDialog;
import com.shushan.kencanme.mvp.views.dialog.RechargeBeansDialog;

import java.util.Calendar;


public class DialogFactory {

    public static AlertDialog newAlertDialog(Context context, String title,
                                             String msg, String[] btnNames,
                                             DialogInterface.OnClickListener... listeners) {

        DialogInterface.OnClickListener tempListener = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (title != null) {
            builder.setTitle(title);
        }
        if (msg != null) {
            builder.setMessage(msg);
        }

        if (btnNames.length > 0) {
            if (listeners == null || listeners.length < 1) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setPositiveButton(btnNames[0], listeners[0]);
            }
        }
        if (btnNames.length > 1) {
            if (listeners == null || listeners.length < 2) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNeutralButton(btnNames[1], listeners[1]);
            }
        }
        if (btnNames.length > 2) {
            if (listeners == null || listeners.length < 3) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNegativeButton(btnNames[2], listeners[2]);
            }
        }

        return builder.create();
    }

    public static AlertDialog newMsgDialog(Context context, String msg,
                                           String title, String confirm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_menu_more);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton(confirm, null);
        return builder.create();
    }

    public static DatePickerDialog newDatePickerDialog(Context context,
                                                       OnDateSetListener callBack) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(context,
                callBack, year, monthOfYear, dayOfMonth);
    }

    public static AlertDialog newCustomViewDialog(Context context,
                                                  String title, String[] btnNames, View customView,
                                                  DialogInterface.OnClickListener... btnListeners) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }

        builder.setView(customView);

        if (btnNames.length > 0) {
            if (btnListeners == null || btnListeners.length < 1) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setPositiveButton(btnNames[0], btnListeners[0]);
            }
        }
        if (btnNames.length > 1) {
            if (btnListeners == null || btnListeners.length < 2) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNeutralButton(btnNames[1], btnListeners[1]);
            }
        }
        if (btnNames.length > 2) {
            if (btnListeners == null || btnListeners.length < 3) {
                builder.setPositiveButton(btnNames[0], null);
            } else {
                builder.setNegativeButton(btnNames[2], btnListeners[2]);
            }
        }
        return builder.create();
    }

    public static void showDialogFragment(FragmentManager fragManager, DialogFragment dialogFragment, String tag) {
        try {
            FragmentTransaction ft = fragManager.beginTransaction();
            Fragment prev = fragManager.findFragmentByTag(tag);
            if (prev != null) {
                ft.remove(prev);
            }
            if (!dialogFragment.isAdded()) {
                ft.add(dialogFragment, tag);
                ft.commitAllowingStateLoss();
                fragManager.executePendingTransactions();//加上这句话可以让add方法执行 相当于更新操作
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void dismissDialogFragment(FragmentManager fragManager, String tag) {
        FragmentTransaction ft = fragManager.beginTransaction();
        Fragment fragment = fragManager.findFragmentByTag(tag);
        if (fragment != null) {
            ft.remove(fragment);
            ft.commitAllowingStateLoss();
        }
    }

    /**
     * 显示公共dialog
     *
     * @param context 上下文
     * @param content 内容
     * @param type    类型  1：底部左右两按钮   2;底部一按钮
     * @return commonDialog
     */
    public static void showCommonDialog(Activity context, String content, int type) {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setListener((CommonDialog.CommonDialogListener) context);
        commonDialog.setContent(content);
        commonDialog.setStyle(type);
        DialogFactory.showDialogFragment(((BaseActivity) context).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }


    public static ProgressDialog showProgressDialog(Context context, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        return progressDialog;
    }

    public static Dialog showLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_loading, (ViewGroup) ((BaseActivity) context).getWindow().getDecorView(), false);// 得到加载view
        TextView loadingTvText = v.findViewById(R.id.loading_tv);
        loadingTvText.setText(msg);
        final Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(v);
        return loadingDialog;
    }


    /**
     * 提示成为VIP弹框
     * 判断 去喜欢/去聊天
     * Activity中调用
     */
    public static void showOpenVipDialog(Activity context, String title) {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setListener((CommonDialog.CommonDialogListener) context);
        commonDialog.setContent(title);
        commonDialog.setStyle(Constant.DIALOG_TWO);
        DialogFactory.showDialogFragment(((BaseActivity) context).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    /**
     * 提示成为VIP弹框
     * 判断 去喜欢/去聊天
     * fragment中调用
     */
    public static void showOpenVipDialogFragment(Context context,Fragment fragment, String title) {
        CommonDialog commonDialog = CommonDialog.newInstance();
        commonDialog.setListener((CommonDialog.CommonDialogListener) fragment);
        commonDialog.setContent(title);
        commonDialog.setStyle(Constant.DIALOG_TWO);
        DialogFactory.showDialogFragment(((BaseActivity) context).getSupportFragmentManager(), commonDialog, CommonDialog.TAG);
    }

    /**
     * 提示去充值嗨豆
     * 判断使用嗨豆
     */
    public static void showRechargeBeansDialog(Activity context) {
        RechargeBeansDialog rechargeBeansDialog = RechargeBeansDialog.newInstance();
        rechargeBeansDialog.setListener((RechargeBeansDialog.RechargeDialogListener) context);
        DialogFactory.showDialogFragment(((BaseActivity) context).getSupportFragmentManager(), rechargeBeansDialog, RechargeBeansDialog.TAG);
    }

    /**
     * 显示使用嗨豆
     */
    public static void showUseBeansDialog(Activity context, String title, int beansNum) {
        MessageUseBeansDialog messageUseBeansDialog = MessageUseBeansDialog.newInstance();
        messageUseBeansDialog.setListener((MessageUseBeansDialog.MessageUseBeansDialogListener) context);
        messageUseBeansDialog.setTitle(title, beansNum);
        DialogFactory.showDialogFragment(((BaseActivity) context).getSupportFragmentManager(), messageUseBeansDialog, MessageUseBeansDialog.TAG);
    }

}
