package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.DialogBuyBean;

import java.util.List;

/**
 * bugDialogAdapter
 */
public class BuyDialogAdapter extends BaseQuickAdapter<DialogBuyBean.DataBean, BaseViewHolder> {

    private Context mContext;

    public BuyDialogAdapter(Context context, @Nullable List<DialogBuyBean.DataBean> data) {
        super(R.layout.dialog_bug_item, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, DialogBuyBean.DataBean item) {
        if (item != null) {
            helper.setText(R.id.exposure_time_tv, String.valueOf(item.time));
            helper.setText(R.id.money_tv, String.valueOf(item.num));
            if (item.isCheck) {
                helper.setVisible(R.id.check_iv, true);
                helper.setBackgroundRes(R.id.exposure_time_rl, R.drawable.dialog_buy_blue_bg);
                helper.setTextColor(R.id.exposure_time_tv, mContext.getResources().getColor(R.color.white));
                helper.setTextColor(R.id.time_hint, mContext.getResources().getColor(R.color.white));
            } else {
                helper.setVisible(R.id.check_iv, false);
                helper.setBackgroundRes(R.id.exposure_time_rl, R.drawable.dialog_buy_white_bg);
                helper.setTextColor(R.id.exposure_time_tv, mContext.getResources().getColor(R.color.five_text_color));
                helper.setTextColor(R.id.time_hint, mContext.getResources().getColor(R.color.five_text_color));
            }
            if (item.isHot || helper.getAdapterPosition() == 1) {//默认第二项火热
                helper.setVisible(R.id.hot_iv, true);
            } else {
                helper.setVisible(R.id.hot_iv, false);
            }

        }
    }
}
