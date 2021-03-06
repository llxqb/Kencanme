package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.response.OpenVipResponse;

import java.util.List;

/**
 * RechargeAdapter
 */
public class OpenVipAdapter extends BaseQuickAdapter<OpenVipResponse.VipinfoBean, BaseViewHolder> {

    private Context mContext;

    public OpenVipAdapter(Context context, @Nullable List<OpenVipResponse.VipinfoBean> data) {
        super(R.layout.item_open_vip, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, OpenVipResponse.VipinfoBean item) {
        helper.addOnClickListener(R.id.open_vip_super_vip_rl);
        if (item != null) {
            TextView originalPriceTv = helper.getView(R.id.original_price);
            helper.setText(R.id.super_vip_hint,item.getName());
            helper.setText(R.id.current_price,"/$"+item.getSpecial_price());
            helper.setText(R.id.original_price," $"+item.getOriginal_price());
            originalPriceTv.getPaint().setAntiAlias(true);//抗锯齿
            originalPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
            helper.setText(R.id.exclusive_tv,item.getPrivilege());
            if(item.isCheck){
                helper.setBackgroundRes(R.id.open_vip_super_vip_rl,R.drawable.super_vip_bg);
            }else {
                helper.setBackgroundRes(R.id.open_vip_super_vip_rl,R.drawable.bg_open_vip_selector);
            }
        }
    }
}
