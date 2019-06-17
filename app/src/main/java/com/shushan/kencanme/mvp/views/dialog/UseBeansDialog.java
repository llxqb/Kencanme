package com.shushan.kencanme.mvp.views.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 公用提示的dialog
 *
 * @author helei
 */
public class UseBeansDialog extends BaseDialogFragment {
    public static final String TAG = UseBeansDialog.class.getSimpleName();
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.use_beans_dialog_title)
    TextView mUseBeansDialogTitle;
    @BindView(R.id.use_beans_left_tv)
    TextView mUseBeansLeftTv;
    @BindView(R.id.use_beans_right_tv)
    TextView mUseBeansRightTv;
    @BindView(R.id.use_beans_dialog_layout)
    RelativeLayout mUseBeansDialogLayout;
    Unbinder unbinder;
    private UseBeansDialogListener mUseBeansDialogListener;
    private String mTitle, mLeftTx, mRightTx;

    public static UseBeansDialog newInstance() {
        return new UseBeansDialog();
    }


    public void setContent(String leftTx, String rightTv) {
        this.mLeftTx = leftTx;
        mRightTx = rightTv;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setListener(UseBeansDialogListener dialogBtnListener) {
        this.mUseBeansDialogListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_use_beans, container, true);
        unbinder = ButterKnife.bind(this, view);
        mUseBeansDialogTitle.setText(mTitle);
        mUseBeansLeftTv.setText(mLeftTx);
        mUseBeansRightTv.setText(mRightTx);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_close, R.id.use_beans_left_tv, R.id.use_beans_right_tv, R.id.use_beans_dialog_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.use_beans_left_tv:
                if (mUseBeansDialogListener != null) {
                    mUseBeansDialogListener.useBeansDialogLeftListener();
                }
                closeCommonDialog();
                break;
            case R.id.use_beans_right_tv:
                if (mUseBeansDialogListener != null) {
                    mUseBeansDialogListener.useBeansDialogRightListener();
                }
                closeCommonDialog();
                break;
            case R.id.use_beans_dialog_layout:
                closeCommonDialog();
                break;
        }
    }


    public interface UseBeansDialogListener {
        void useBeansDialogLeftListener();

        void useBeansDialogRightListener();

    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
