package com.shushan.kencanme.mvp.views.dialog;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.DialogBuyBean;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.BuyDialogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 购买VIP dialog
 *
 * @author li.liu
 */
public class BuyDialog extends BaseDialogFragment {
    public static final String TAG = BuyDialog.class.getSimpleName();
    @BindView(R.id.dialog_buy_layout)
    RelativeLayout dialogBuyLayout;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.buy_dialog_content_tv)
    TextView buyDialogContentTv;
    @BindView(R.id.dialog_buy_recycler_view)
    RecyclerView dialogBuyRecyclerView;
    @BindView(R.id.dialog_buy_buy)
    TextView dialogBuyBuy;
    @BindView(R.id.buy_dialog_bean_tv)
    TextView dialogBuyBeanTv;
    private BuyDialogListener dialogBtnListener;
    private String mTitle, mContent;
    private Unbinder bind;
    private List<DialogBuyBean.DataBean> dialogBuyBeans = new ArrayList<>();
    private DialogBuyBean mDialogBuyBean;
    private int beans;
    DialogBuyBean.DataBean bean;

    public static BuyDialog newInstance() {
        return new BuyDialog();
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public void setBugData(DialogBuyBean dialogBuyBean, int beans) {
        mDialogBuyBean = dialogBuyBean;
        this.beans = beans;
    }

    public void setListener(BuyDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_buy, container, true);
        bind = ButterKnife.bind(this, view);
        buyDialogContentTv.setText(mContent);
        dialogBuyBeanTv.setText(getResources().getString(R.string.buy_dailog_hint) + beans);
        initView();
        return view;
    }

    private void initView() {
        dialogBuyBeans = mDialogBuyBean.getData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dialogBuyRecyclerView.setLayoutManager(linearLayoutManager);
        BuyDialogAdapter buyDialogAdapter = new BuyDialogAdapter(getActivity(), dialogBuyBeans);
        dialogBuyRecyclerView.setAdapter(buyDialogAdapter);
        buyDialogAdapter.setOnItemClickListener((adapter, view, position) -> {
            bean = buyDialogAdapter.getItem(position);
            if (bean != null) {
                for (DialogBuyBean.DataBean buyBean : dialogBuyBeans) {
                    if (buyBean.isCheck) buyBean.isCheck = false;
                }
                bean.isCheck = !bean.isCheck;
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @OnClick({R.id.dialog_buy_layout, R.id.iv_close, R.id.dialog_buy_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_buy_layout:
                closeCommonDialog();
                break;
            case R.id.iv_close:
                closeCommonDialog();
                break;
            case R.id.dialog_buy_buy:
                if (dialogBtnListener != null) {
                    if (beans < bean.num) {
                        showToast(getResources().getString(R.string.BuyDialog_buy_beans));
                    } else {
                        dialogBtnListener.buyDialogBtnOkListener(bean.num);
                        closeCommonDialog();
                    }
                }
                break;
        }
    }

    public interface BuyDialogListener {
        void buyDialogBtnOkListener(int beansMoney);
    }


    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
