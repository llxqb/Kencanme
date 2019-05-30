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
import com.shushan.kencanme.entity.Constant;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.views.dialog.BaseDialogFragment;

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
        } else if (mType == Constant.DIALOG_TWO) {
            dialogStyle1ll.setVisibility(View.GONE);
            dialogStyle2Rl.setVisibility(View.VISIBLE);
        }
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
        bind.unbind();
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
