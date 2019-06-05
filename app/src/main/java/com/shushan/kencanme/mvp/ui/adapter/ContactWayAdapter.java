package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.ContactWay;

import java.util.List;

/**
 * RechargeAdapter
 */
public class ContactWayAdapter extends BaseQuickAdapter<ContactWay, BaseViewHolder> {

    private Context mContext;

    public ContactWayAdapter(Context context, @Nullable List<ContactWay> data) {
        super(R.layout.item_contact_way, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactWay item) {
        if (item != null) {
            helper.setText(R.id.contact_way_tv, item.ContactName);
            helper.setText(R.id.contact_value_et, item.ContactValue);
        }
    }
}
