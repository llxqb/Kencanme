package com.shushan.kencanme.mvp.views.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.DialogBuyBean;
import com.shushan.kencanme.help.DialogFactory;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.dialog_buy_layout)
    RelativeLayout mDialogBuyLayout;
    private CommonDialogListener dialogBtnListener;
    private String title, mContent;
    private Unbinder bind;
    private List<DialogBuyBean> dialogBuyBeans = new ArrayList<>();

    public static UseExposureDialog newInstance() {
        return new UseExposureDialog();
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
        View view = inflater.inflate(R.layout.dialog_use_exposure, container, true);
        bind = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.iv_close, R.id.dialog_buy_use_exposure, R.id.dialog_buy_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.dialog_buy_use_exposure:
                closeCommonDialog();
                break;
            case R.id.dialog_buy_layout:
                break;
        }
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
