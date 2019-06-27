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
 * 匹配成功 dialog
 *
 * @author li.liu
 */
public class MatchSuccessDialog extends BaseDialogFragment {
    public static final String TAG = MatchSuccessDialog.class.getSimpleName();
    @BindView(R.id.match_success_iv)
    ImageView mMatchSuccessIv;
    @BindView(R.id.match_success_chat_tv)
    TextView mMatchSuccessChatTv;
    @BindView(R.id.dialog_match_success_layout)
    RelativeLayout mDialogMatchSuccessLayout;
    Unbinder unbinder;
    private MatchSuccessListener matchSuccessListener;

    public static MatchSuccessDialog newInstance() {
        return new MatchSuccessDialog();
    }

    public void setListener(MatchSuccessListener dialogBtnListener) {
        this.matchSuccessListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_match_success, container, true);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.match_success_iv, R.id.match_success_chat_tv, R.id.dialog_match_success_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.match_success_iv:
                break;
            case R.id.match_success_chat_tv:
                if (matchSuccessListener != null) {
                    matchSuccessListener.startChatBtnListener();
                }
                break;
            case R.id.dialog_match_success_layout:
                break;
        }
    }


    public interface MatchSuccessListener {
        void startChatBtnListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
