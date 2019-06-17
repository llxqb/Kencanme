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
        if (item != null) {
            helper.setText(R.id.hi_beans_num, String.valueOf(item.getAmount()));
            String describeValue = !TextUtils.isEmpty(item.getDescribe()) ? item.getDescribe() + "/" : "";
            helper.setText(R.id.desc, describeValue);
            helper.setText(R.id.vip_desc, "VIP give" + String.valueOf(item.getVip_give()));
            helper.setText(R.id.rp_money, String.valueOf(item.getPrice()));
        }
    }
}
