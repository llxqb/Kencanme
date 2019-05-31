package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;

import java.util.List;

/**
 * RecommendUserLabelAdapter
 */
public class RecommendUserLabelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private Context mContext;

    public RecommendUserLabelAdapter(@Nullable List<String> data) {
        super(R.layout.label_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (!TextUtils.isEmpty(item)) {
            helper.setText(R.id.label_tv, item);
        }
    }


}
