package com.shushan.kencanme.app.mvp.views.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
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
 * 公用提示的dialog
 *
 * @author helei
 */
public class MessageUseBeansDialog extends BaseDialogFragment {
    public static final String TAG = MessageUseBeansDialog.class.getSimpleName();
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.use_beans_title)
    TextView mUseBeansTitle;
    @BindView(R.id.use_beans_num)
    TextView mUseBeansNum;
    @BindView(R.id.dialog_style_2_tv)
    TextView mDialogStyle2Tv;
    @BindView(R.id.dialog_style_2_rl)
    RelativeLayout mDialogStyle2Rl;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.message_use_beans_dialog_layout)
    RelativeLayout mMessageUseBeansDialogLayout;
    private String mTitle;
    /**
     * 需支付beans值
     */
    private int mBeans;
    Unbinder unbinder;
    private MessageUseBeansDialogListener mMessageUseBeansDialogListener;
    @SuppressLint("StaticFieldLeak")

    public static MessageUseBeansDialog newInstance() {
        return new MessageUseBeansDialog();
    }

    public void setListener(MessageUseBeansDialogListener dialogBtnListener) {
        this.mMessageUseBeansDialogListener = dialogBtnListener;
    }

    public void setTitle(String title, int beans) {
        mTitle = title;
        mBeans = beans;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_message_use_beans, container, true);
        unbinder = ButterKnife.bind(this, view);
        String titleValue = mTitle + " " + "<font color = '#DDBA67'>" + mBeans + " "+getResources().getString(R.string.Hi_Beans) + "</font>";
        mUseBeansTitle.setText(Html.fromHtml(titleValue));
        mUseBeansNum.setText(String.valueOf(mBeans));
        return view;
    }


    @OnClick({R.id.iv_close, R.id.dialog_style_2_rl, R.id.pop_contain, R.id.message_use_beans_dialog_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.dialog_style_2_rl:
                if (mMessageUseBeansDialogListener != null) {
                    mMessageUseBeansDialogListener.messageUseBeansDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.pop_contain:
                break;
            case R.id.message_use_beans_dialog_layout:
                closeCommonDialog();
                break;
        }
    }


    public interface MessageUseBeansDialogListener {
        void messageUseBeansDialogBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
