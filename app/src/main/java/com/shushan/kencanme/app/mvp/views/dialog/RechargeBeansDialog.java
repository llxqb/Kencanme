package com.shushan.kencanme.app.mvp.views.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 使用beans   查看相册
 * 聊天回复消息  提示开vip或者使用beans
 */
public class RechargeBeansDialog extends BaseDialogFragment {
    public static final String TAG = RechargeBeansDialog.class.getSimpleName();
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.dialog_recharge_title)
    TextView mDialogRechargeTitle;
    @BindView(R.id.btn_earn)
    Button mBtnEarn;
    @BindView(R.id.btn_recharge)
    Button mBtnRecharge;
    @BindView(R.id.dialog_contain_ll)
    LinearLayout mDialogContainLl;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;
    Unbinder unbinder;
    private RechargeDialogListener mRechargeDialogListener;
    private String mTitle;
    private String mTopContent;
    private String mBottomContent;

    public static RechargeBeansDialog newInstance() {
        return new RechargeBeansDialog();
    }


    public void setListener(RechargeDialogListener dialogBtnListener) {
        this.mRechargeDialogListener = dialogBtnListener;
    }

    public void setContent(String title,String topContent,String bottomContent){
        mTitle = title;
        mTopContent = topContent;
        mBottomContent = bottomContent;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_recharge_beans, container, true);
        unbinder = ButterKnife.bind(this, view);
        mDialogRechargeTitle.setText(mTitle);
        mBtnEarn.setText(mTopContent);
        mBtnRecharge.setText(mBottomContent);
        return view;
    }


    @OnClick({R.id.iv_close, R.id.btn_earn, R.id.btn_recharge, R.id.dialog_contain_ll, R.id.recharge_dialog_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_contain_ll:
                break;
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.btn_earn:
                if (mRechargeDialogListener != null) {
                    mRechargeDialogListener.earnBeansDialogBtnListener();
                }
                closeCommonDialog();
                break;
            case R.id.btn_recharge:
                if (mRechargeDialogListener != null) {
                    mRechargeDialogListener.rechargeBeansDialogBtnListener();
                }
                closeCommonDialog();
                break;
            case R.id.recharge_dialog_layout:
                closeCommonDialog();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public interface RechargeDialogListener {
        void earnBeansDialogBtnListener();

        void rechargeBeansDialogBtnListener();

    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
