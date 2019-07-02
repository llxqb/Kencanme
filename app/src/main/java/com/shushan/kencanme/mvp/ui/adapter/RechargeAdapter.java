package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.ReChargeBeansInfoResponse;

import java.util.List;

/**
 * RechargeAdapter
 */
public class RechargeAdapter extends BaseQuickAdapter<ReChargeBeansInfoResponse.BeansinfoBean, BaseViewHolder> {

    private Context mContext;

    public RechargeAdapter(Context context, @Nullable List<ReChargeBeansInfoResponse.BeansinfoBean> data) {
        super(R.layout.item_recharge, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, ReChargeBeansInfoResponse.BeansinfoBean item) {
        helper.addOnClickListener(R.id.item_recharge_rl);
        if (item != null) {
            helper.setText(R.id.hi_beans_num, String.valueOf(item.getAmount()));
            if (!TextUtils.isEmpty(item.getDescribe())) {
                helper.setVisible(R.id.desc, true);
                helper.setText(R.id.desc, item.getDescribe());
            } else {
                helper.setVisible(R.id.desc, false);
            }
            helper.setText(R.id.give, "give" + String.valueOf(item.getVip_give()));
            helper.setText(R.id.vip_give, " / VIP give" + String.valueOf(item.getVip_give()));
            helper.setText(R.id.rp_money, "$" + String.valueOf(item.getPrice()));

            if (item.isCheck) {
                helper.setBackgroundRes(R.id.item_recharge_rl, R.drawable.item_recharge_check_bg);
                helper.setBackgroundColor(R.id.line,mContext.getResources().getColor(R.color.color_yellow_line));
                helper.setVisible(R.id.check_iv, true);
            } else {
                helper.setBackgroundRes(R.id.item_recharge_rl, R.drawable.item_recharge_bg);
                helper.setBackgroundColor(R.id.line,mContext.getResources().getColor(R.color.line_color));
                helper.setVisible(R.id.check_iv, false);
            }
        }
    }
}
