package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.RechargeBean;

import java.util.List;

/**
 * RechargeAdapter
 */
public class RechargeAdapter extends BaseQuickAdapter<RechargeBean, BaseViewHolder> {

    private Context mContext;

    public RechargeAdapter(Context context, @Nullable List<RechargeBean> data) {
        super(R.layout.item_recharge, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, RechargeBean item) {
        if (item != null) {
            helper.setText(R.id.hi_beans_num, String.valueOf(item.HiBeanNum));
            helper.setText(R.id.desc, item.desc);
            helper.setText(R.id.vip_desc, item.vipDesc);
            helper.setText(R.id.rp_money, item.rpMoney);
        }
    }
}
