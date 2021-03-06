package com.shushan.kencanme.app.mvp.views.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * 购买VIP dialog
 *
 * @author li.liu
 */
public class SendPhotoTypeDialog extends BaseDialogFragment {
    public static final String TAG = SendPhotoTypeDialog.class.getSimpleName();
    @BindView(R.id.iv_close)
    ImageView mIvClose;
    @BindView(R.id.ordinary_photo_check_iv)
    ImageView mOrdinaryPhotoCheckIv;
    @BindView(R.id.private_photo_check_iv)
    ImageView mPrivatePhotoCheckIv;
    @BindView(R.id.beans_one)
    TextView mBeansOne;
    @BindView(R.id.beans_five)
    TextView mBeansFive;
    @BindView(R.id.beans_custom_ev)
    EditText mBeansCustomEv;
    @BindView(R.id.sure_btn)
    Button mSureBtn;
    @BindView(R.id.dialog_send_photo_layout)
    RelativeLayout mDialogLoginLayout;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    Unbinder unbinder;
    @BindView(R.id.ordinary_photo_tv)
    TextView mOrdinaryPhotoTv;
    @BindView(R.id.ordinary_photo_ll)
    LinearLayout mOrdinaryPhotoLl;
    @BindView(R.id.private_photo_tv)
    TextView mPrivatePhotoTv;
    @BindView(R.id.private_photo_ll)
    LinearLayout mPrivatePhotoLl;
    //选择上传的图片类型  1 普通 2 VIP 3 私密
    private int picType = 0;
    //如果是私密照片 需支付嗨豆数量
    private int beansNumber = 0;

    public static SendPhotoTypeDialog newInstance() {
        return new SendPhotoTypeDialog();
    }

    private SendPhotoTypeDialogListener mSendPhotoTypeDialogListener;


    public void setListener(SendPhotoTypeDialogListener dialogBtnListener) {
        this.mSendPhotoTypeDialogListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_send_photo_type, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mBeansCustomEv.addTextChangedListener(textWatcher);
    }

    @OnClick({R.id.iv_close, R.id.ordinary_photo_ll, R.id.private_photo_ll, R.id.beans_one, R.id.beans_five, R.id.beans_custom_ev, R.id.sure_btn,
            R.id.dialog_send_photo_layout, R.id.pop_contain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.ordinary_photo_ll:
                //普通照片
                setIvTypeBg();
                mOrdinaryPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                mOrdinaryPhotoTv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                picType = 1;
                break;
            case R.id.private_photo_ll:
                //私密照片
                setIvTypeBg();
                picType = 3;
                mPrivatePhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_choose);
                mPrivatePhotoTv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                break;
            case R.id.beans_one:
                initBeansBg();
                mBeansOne.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansOne.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                beansNumber = 1;
                break;
            case R.id.beans_five:
                initBeansBg();
                mBeansFive.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansFive.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                beansNumber = 5;
                break;
            case R.id.beans_custom_ev:
                initBeansBg();
                mBeansCustomEv.setCursorVisible(true);
                mBeansCustomEv.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansCustomEv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                break;
            case R.id.sure_btn:
                if (isValidEmpty()) {
                    if (mSendPhotoTypeDialogListener != null) {
                        if (picType == 1) {
                            mSendPhotoTypeDialogListener.sendOrdinaryPhotoBtnOKListener();
                        } else if (picType == 3) {
                            mSendPhotoTypeDialogListener.sendPrivatePhotoBtnOKListener(beansNumber);
                        }
                    }
                    closeCommonDialog();
                }
                break;
            case R.id.dialog_send_photo_layout:
                closeCommonDialog();
                break;
            case R.id.pop_contain:
                break;
        }
    }


    private void initBeansBg() {
        mBeansOne.setBackgroundResource(R.drawable.bg_beans_selector_5);
        mBeansFive.setBackgroundResource(R.drawable.bg_beans_selector_5);
        mBeansCustomEv.setBackgroundResource(R.drawable.bg_beans_selector_5);
        mBeansOne.setTextColor(getResources().getColor(R.color.third_text_color));
        mBeansFive.setTextColor(getResources().getColor(R.color.third_text_color));
        mBeansCustomEv.setTextColor(getResources().getColor(R.color.third_text_color));
    }

    private boolean isValidEmpty() {
        if (picType == 0) {
            showToast(getResources().getString(R.string.UploadPhotoActivity_upload_pic_hint));
            return false;
        }
        if (picType == 3) {
            if (beansNumber == 0) {
                showToast(getResources().getString(R.string.UploadPhotoActivity_upload_beans_hint));
                return false;
            }
        }
        return true;
    }

    /**
     * 重置图片背景资源 check
     */
    private void setIvTypeBg() {
        mOrdinaryPhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mPrivatePhotoCheckIv.setImageResource(R.mipmap.pay_hibeans_no_choose);
        mOrdinaryPhotoTv.setTextColor(getResources().getColor(R.color.color_80));
        mPrivatePhotoTv.setTextColor(getResources().getColor(R.color.color_80));

    }


    public interface SendPhotoTypeDialogListener {
        void sendOrdinaryPhotoBtnOKListener();

        void sendPrivatePhotoBtnOKListener(int beansNum);
    }


    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(s)) {
                initBeansBg();
                mBeansCustomEv.setBackgroundResource(R.drawable.bg_beans_selectored_5);
                mBeansCustomEv.setTextColor(getResources().getColor(R.color.photo_check_text_color));
                beansNumber = Integer.parseInt(s.toString());
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), TAG);
        }
    }
}
