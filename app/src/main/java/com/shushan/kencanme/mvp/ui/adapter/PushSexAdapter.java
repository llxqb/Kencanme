package com.shushan.kencanme.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;

import java.util.List;

public class PushSexAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public PushSexAdapter(@Nullable List<String> data) {
        super(R.layout.item_push_sex_select, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item != null) {
            helper.setText(R.id.sex_tv, item);
        }
    }
}
