package com.shushan.kencanme.app.mvp.views.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.DialogBuyBean;
import com.shushan.kencanme.app.help.DialogFactory;

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
public class LoginDialog extends BaseDialogFragment {
    public static final String TAG = LoginDialog.class.getSimpleName();
    @BindView(R.id.account_avator_iv)
    ImageView mAccountAvatorIv;
    @BindView(R.id.account_name)
    TextView mAccountName;
    @BindView(R.id.login_app_rl)
    RelativeLayout mLoginAppRl;
    @BindView(R.id.login_other_account_rl)
    RelativeLayout mLoginOtherAccountRl;
    @BindView(R.id.pop_contain)
    LinearLayout mPopContain;
    @BindView(R.id.dialog_login_layout)
    RelativeLayout mDialogLoginLayout;
    private LoginDialogListener dialogBtnListener;
    private Unbinder bind;
    private List<DialogBuyBean> dialogBuyBeans = new ArrayList<>();

    public static LoginDialog newInstance() {
        return new LoginDialog();
    }


    public void setListener(LoginDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, true);
        bind = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.login_app_rl, R.id.login_other_account_rl, R.id.dialog_login_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_app_rl:
                if (dialogBtnListener != null) {
                    dialogBtnListener.loginDialogBtnOkListener();
                }
                closeCommonDialog();
                break;
            case R.id.login_other_account_rl:
                closeCommonDialog();
                break;
            case R.id.dialog_login_layout:
                closeCommonDialog();
                break;
        }
    }


    public interface LoginDialogListener {
        void loginDialogBtnOkListener();
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
