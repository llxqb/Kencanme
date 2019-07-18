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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 选择支付方式弹框
 */
public class PaySelectDialog extends BaseDialogFragment {
    public static final String TAG = PaySelectDialog.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.google_pay_iv)
    ImageView mGooglePayIv;
    @BindView(R.id.google_pay_ll)
    LinearLayout mGooglePayLl;
    @BindView(R.id.ahdi_pay_iv)
    ImageView mAhdiPayIv;
    @BindView(R.id.ahdi_pay_ll)
    LinearLayout mAhdiPayLl;
    @BindView(R.id.uniPin_pay_iv)
    ImageView mUniPinPayIv;
    @BindView(R.id.uniPin_pay_ll)
    LinearLayout mUniPinPayLl;
    @BindView(R.id.payment_btn)
    Button mPaymentBtn;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.dialog_pay_choose_layout)
    RelativeLayout mDialogPayChooseLayout;
    @BindView(R.id.google_pay_tv)
    TextView mGooglePayTv;
    @BindView(R.id.ahdi_pay_tv)
    TextView mAhdiPayTv;
    @BindView(R.id.uniPin_pay_tv)
    TextView mUniPinPayTv;
    @BindView(R.id.money_tv)
    TextView mMoneyTv;

    private int payType;
    //美元
    private String mMoney;
    //印尼币
    private int ynMoney;

    public static PaySelectDialog newInstance() {
        return new PaySelectDialog();
    }

    private payChoiceDialogListener payChoiceDialogListener;

    public void setMoney(String money, int ynMoney) {
        mMoney = money;
        this.ynMoney = ynMoney;
    }

    public void setListener(payChoiceDialogListener dialogListener) {
        this.payChoiceDialogListener = dialogListener;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_choice_dialog, container, true);
//        AniCreator.getInstance().apply_animation_translate(mRechargeDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        unbinder = ButterKnife.bind(this, view);
        //默认UniPinPay
        payType = 3;
        mUniPinPayIv.setImageResource(R.mipmap.sex_choose);
        mUniPinPayTv.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.color_blue_btn));
        String moneyValue = getResources().getString(R.string.money_yn) + ynMoney;
        mMoneyTv.setText(moneyValue);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    String moneyValue;

    @OnClick({R.id.google_pay_ll, R.id.ahdi_pay_ll, R.id.uniPin_pay_ll, R.id.payment_btn, R.id.pop_contain, R.id.dialog_pay_choose_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.google_pay_ll:
                initPayIv();
                payType = 1;
                mGooglePayIv.setImageResource(R.mipmap.sex_choose);
                mGooglePayTv.setTextColor(getResources().getColor(R.color.color_blue_btn));
                moneyValue = getResources().getString(R.string.money) + mMoney;
                mMoneyTv.setText(moneyValue);
                break;
            case R.id.ahdi_pay_ll:
                initPayIv();
                payType = 2;
                mAhdiPayIv.setImageResource(R.mipmap.sex_choose);
                mAhdiPayTv.setTextColor(getResources().getColor(R.color.color_blue_btn));
                moneyValue = getResources().getString(R.string.money_yn) + ynMoney;
                mMoneyTv.setText(moneyValue);
                break;
            case R.id.uniPin_pay_ll:
                initPayIv();
                payType = 3;
                mUniPinPayIv.setImageResource(R.mipmap.sex_choose);
                mUniPinPayTv.setTextColor(getResources().getColor(R.color.color_blue_btn));
                moneyValue = getResources().getString(R.string.money_yn) + ynMoney;
                mMoneyTv.setText(moneyValue);
                break;
            case R.id.payment_btn:
                if (payChoiceDialogListener != null) {
                    payChoiceDialogListener.payType(payType);
                }
                closeRechargeDialog();
                break;
            case R.id.pop_contain:
                break;
            case R.id.dialog_pay_choose_layout:
                closeRechargeDialog();
                break;
        }
    }


    private void initPayIv() {
        mGooglePayIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mAhdiPayIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mUniPinPayIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mGooglePayTv.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.first_text_color));
        mAhdiPayTv.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.first_text_color));
        mUniPinPayTv.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.first_text_color));
    }

    public interface payChoiceDialogListener {
        void payType(int payType);
    }


    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }
}
