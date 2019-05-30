package com.shushan.kencanme.mvp.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 公用提示的dialog
 *
 * @author helei
 */
public class CommonDialog extends BaseDialogFragment {
    public static final String TAG = CommonDialog.class.getSimpleName();
    @BindView(R.id.common_dialog_title)
    TextView commonDialogTitle;
    @BindView(R.id.common_dialog_sure)
    Button commonDialogSure;
    @BindView(R.id.common_dialog_cancel)
    Button commonDialogCancel;
    @BindView(R.id.common_dialog_layout)
    RelativeLayout commonDialogLayout;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.dialog_style_2_tv)
    TextView dialogStyle2Tv;
    private CommonDialogListener dialogBtnListener;
    private String title, mContent;

    public static CommonDialog newInstance() {
        return new CommonDialog();
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setListener(CommonDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_common, container, true);
        ButterKnife.bind(this, view);
        commonDialogTitle.setText(mContent);
        return view;
    }


    @OnClick({R.id.iv_close, R.id.common_dialog_sure, R.id.common_dialog_cancel, R.id.common_dialog_layout,
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.common_dialog_sure:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.common_dialog_cancel:
                closeCommonDialog();
                break;
            case R.id.common_dialog_layout:
                closeCommonDialog();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface CommonDialogListener {
        void commonDialogBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
