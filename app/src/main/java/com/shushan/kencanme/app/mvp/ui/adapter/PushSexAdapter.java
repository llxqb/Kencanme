package com.shushan.kencanme.app.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.SexBean;

import java.util.List;

public class PushSexAdapter extends BaseQuickAdapter<SexBean, BaseViewHolder> {


    public PushSexAdapter(@Nullable List<SexBean> data) {
        super(R.layout.item_push_sex_select, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, SexBean item) {
        if (item != null) {
            helper.setText(R.id.sex_tv, item.name);
            if (item.isCheck) {
                helper.setBackgroundRes(R.id.sex_tv, R.drawable.bg_color_yellow_round_30);
            } else {
                helper.setBackgroundRes(R.id.sex_tv, R.drawable.sex_selector);
            }
        }
    }
}
