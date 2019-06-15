package com.shushan.kencanme.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.ContactWay;

import java.util.List;

/**
 * Mime 页面 MimeContactWayAdapter
 */
public class MimeContactWayAdapter extends BaseQuickAdapter<ContactWay, BaseViewHolder> {


    public MimeContactWayAdapter(@Nullable List<ContactWay> data) {
        super(R.layout.item_mime_contact_way, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactWay item) {
        helper.setText(R.id.contact_way, item.contactName + ":" + item.contactValue);
    }
}
