package com.shushan.kencanme.app.mvp.views.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * 使用曝光 dialog
 *
 * @author li.liu
 */
public class UseExposureDialog extends BaseDialogFragment {
    public static final String TAG = UseExposureDialog.class.getSimpleName();
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.use_dialog_content_tv)
    TextView mUseDialogContentTv;
    @BindView(R.id.dialog_buy_use_exposure)
    TextView mDialogBuyUseExposure;
    @BindView(R.id.use_exposure_dialog_beans_tv)
    TextView mDialogUseExposureBeansTv;
    @BindView(R.id.beans_tv)
    TextView mBeansTv;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.dialog_buy_layout)
    RelativeLayout mDialogBuyLayout;
    private UseExposureDialogListener dialogBtnListener;
    private int mBeans, mExposure;
    private Unbinder bind;

    public static UseExposureDialog newInstance() {
        return new UseExposureDialog();
    }

    public void setContentData(int beans, int exposure) {
        this.mBeans = beans;
        mExposure = exposure;
    }

    public void setListener(UseExposureDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_use_exposure, container, true);
        bind = ButterKnife.bind(this, view);
        mDialogUseExposureBeansTv.setText(String.valueOf(mExposure));
        String beansValue = getResources().getString(R.string.buy_dailog_hint) + String.valueOf(mBeans);
        mBeansTv.setText(beansValue);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.iv_close,R.id.pop_contain, R.id.dialog_buy_use_exposure, R.id.dialog_buy_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.pop_contain:
                break;
            case R.id.dialog_buy_use_exposure:
                if (dialogBtnListener != null) {
                    dialogBtnListener.useExposureBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_buy_layout:
                closeCommonDialog();
                break;
        }
    }

    public interface UseExposureDialogListener {
        void useExposureBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }
}
