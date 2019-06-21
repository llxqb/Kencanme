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
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.views.dialog.BaseDialogFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


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
    @BindView(R.id.dialog_style1_ll)
    LinearLayout dialogStyle1ll;
    @BindView(R.id.dialog_style_2_rl)
    RelativeLayout dialogStyle2Rl;
    @BindView(R.id.dialog_style_3_rl)
    RelativeLayout dialogStyle3Rl;
    @BindView(R.id.dialog_style_3_tv)
    TextView dialogStyle3Tv;
    @BindView(R.id.dialog_style_2_iv)
    ImageView mDialogStyle2Iv;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.dialog_style_4_tv)
    TextView mDialogStyle4Tv;
    @BindView(R.id.dialog_style_4_rl)
    RelativeLayout mDialogStyle4Rl;
    private CommonDialogListener dialogBtnListener;
    private String title, mContent;
    private int mType;// 0 方式一  1 方式二
    private Unbinder bind;

    public static CommonDialog newInstance() {
        return new CommonDialog();
    }

    public void setStyle(int type) {
        this.mType = type;
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
        bind = ButterKnife.bind(this, view);
        if (mType == Constant.DIALOG_ONE) {
            dialogStyle1ll.setVisibility(View.VISIBLE);
            dialogStyle2Rl.setVisibility(View.GONE);
            dialogStyle3Rl.setVisibility(View.GONE);
        } else if (mType == Constant.DIALOG_TWO) {
            dialogStyle1ll.setVisibility(View.GONE);
            dialogStyle2Rl.setVisibility(View.VISIBLE);
            dialogStyle3Rl.setVisibility(View.GONE);
        } else if (mType == Constant.DIALOG_THREE) {
            dialogStyle1ll.setVisibility(View.GONE);
            dialogStyle2Rl.setVisibility(View.GONE);
            dialogStyle3Rl.setVisibility(View.VISIBLE);
        } else if (mType == Constant.DIALOG_FOUR) {
            dialogStyle1ll.setVisibility(View.GONE);
            dialogStyle2Rl.setVisibility(View.GONE);
            dialogStyle3Rl.setVisibility(View.GONE);
            mDialogStyle4Rl.setVisibility(View.VISIBLE);
        }
        commonDialogTitle.setText(mContent);
        return view;
    }


    @OnClick({R.id.iv_close, R.id.pop_contain, R.id.common_dialog_sure, R.id.common_dialog_cancel,
            R.id.dialog_style_2_rl, R.id.dialog_style_3_rl, R.id.common_dialog_layout, R.id.dialog_style_4_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.pop_contain:
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
            case R.id.dialog_style_2_rl:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_style_3_rl:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.dialog_style_4_rl:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener();
                }
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
        bind.unbind();
    }

    public interface CommonDialogListener {
        void commonDialogBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }
}
