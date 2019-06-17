package com.shushan.kencanme.mvp.views.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.shushan.kencanme.R;
import com.shushan.kencanme.help.AniCreator;
import com.shushan.kencanme.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * CommonChoiceDialog
 */
public class CommonChoiceDialog extends BaseDialogFragment {
    public static final String TAG = CommonChoiceDialog.class.getSimpleName();
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;
    @BindView(R.id.delete_user)
    Button mDeleteUser;
    @BindView(R.id.add_to_blacklist_btn)
    Button mAddToBlacklistBtn;
    @BindView(R.id.report_btn)
    Button mReportBtn;

    public static CommonChoiceDialog newInstance() {
        return new CommonChoiceDialog();
    }

    private Unbinder unbind;
    private commonChoiceDialogListener commonChoiceDialogListener;


    public void setListener(commonChoiceDialogListener dialogListener) {
        this.commonChoiceDialogListener = dialogListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_choice_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        AniCreator.getInstance().apply_animation_translate(mRechargeDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        return view;
    }


    @OnClick({R.id.delete_user, R.id.add_to_blacklist_btn, R.id.report_btn, R.id.recharge_dialog_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_user:
                if(commonChoiceDialogListener!=null){
                    commonChoiceDialogListener.deleteUserListener();
                }
                closeRechargeDialog();
                break;
            case R.id.add_to_blacklist_btn:
                if(commonChoiceDialogListener!=null){
                    commonChoiceDialogListener.blackUserListener();
                }
                closeRechargeDialog();
                break;
            case R.id.report_btn:
                if(commonChoiceDialogListener!=null){
                    commonChoiceDialogListener.reportUserListener();
                }
                closeRechargeDialog();
                break;
            case R.id.recharge_dialog_layout:
                closeRechargeDialog();
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    public interface commonChoiceDialogListener {

        void deleteUserListener();

        void blackUserListener();

        void reportUserListener();
    }


    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
