package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;

import java.util.Collections;

/**
 * RecommendUserLabelAdapter
 */
public class RecommendUserLabelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    private Context mContext;

    public RecommendUserLabelAdapter(Context context, @Nullable String data) {
        super(R.layout.label_layout, Collections.singletonList(data));
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
