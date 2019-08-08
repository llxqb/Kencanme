package com.shushan.kencanme.app.mvp.views.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.help.DialogFactory;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 支付成功 上报失败dialog
 *
 * @author li.liu
 */
public class PayReportErrorDialog extends BaseDialogFragment {
    public static final String TAG = PayReportErrorDialog.class.getSimpleName();
    @BindView(R.id.dialog_title)
    TextView mDialogTitle;
    @BindView(R.id.sure)
    RelativeLayout mSure;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.dialog_pay_report_layout)
    RelativeLayout mDialogPayReportLayout;
    private PayReportDialogListener mPayReportDialogListener;
    private String mTitle;
    Unbinder unbinder;

    public static PayReportErrorDialog newInstance() {
        return new PayReportErrorDialog();
    }


    public void setListener(PayReportDialogListener dialogBtnListener) {
        this.mPayReportDialogListener = dialogBtnListener;
    }
    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_pay_report_error, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mDialogTitle.setText(mTitle);
        //设置点击返回键不消失
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sure, R.id.pop_contain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sure:
                if (mPayReportDialogListener != null) {
                    mPayReportDialogListener.payReportBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.pop_contain:
                break;
        }
    }


    public interface PayReportDialogListener {
        void payReportBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }
}
