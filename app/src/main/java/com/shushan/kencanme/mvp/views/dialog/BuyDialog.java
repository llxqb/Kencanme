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
    private CommonDialogListener dialogBtnListener;
    private String title, mContent;
    private Unbinder bind;
    private List<DialogBuyBean> dialogBuyBeans = new ArrayList<>();

    public static BuyDialog newInstance() {
        return new BuyDialog();
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
        View view = inflater.inflate(R.layout.dialog_buy, container, true);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                DialogBuyBean dialogBuyBean = new DialogBuyBean();
                dialogBuyBean.time = 25;
                dialogBuyBean.money = 100;
                dialogBuyBean.isCheck = true;
                dialogBuyBean.isHot = false;
                dialogBuyBeans.add(dialogBuyBean);
            }
            if (i == 1) {
                DialogBuyBean dialogBuyBean = new DialogBuyBean();
                dialogBuyBean.time = 5;
                dialogBuyBean.money = 25;
                dialogBuyBean.isCheck = false;
                dialogBuyBean.isHot = true;
                dialogBuyBeans.add(dialogBuyBean);
            }
            if (i == 2) {
                DialogBuyBean dialogBuyBean = new DialogBuyBean();
                dialogBuyBean.time = 1;
                dialogBuyBean.money = 6;
                dialogBuyBean.isCheck = false;
                dialogBuyBean.isHot = false;
                dialogBuyBeans.add(dialogBuyBean);
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dialogBuyRecyclerView.setLayoutManager(linearLayoutManager);
        BuyDialogAdapter buyDialogAdapter = new BuyDialogAdapter(getActivity(), R.layout.dialog_bug_item, dialogBuyBeans);
        dialogBuyRecyclerView.setAdapter(buyDialogAdapter);


        buyDialogAdapter.setOnItemClickListener((adapter, view, position) -> {
            DialogBuyBean bean = buyDialogAdapter.getItem(position);
            if (bean != null) {
                for (DialogBuyBean buyBean : dialogBuyBeans) {
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
