package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.mvp.ui.activity.personInfo.EditLabelActivity;

import java.util.List;

/**
 * RecommendUserLabelAdapter
 */
public class RecommendUserLabelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;

    public RecommendUserLabelAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.label_layout, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (!TextUtils.isEmpty(item)) {
            helper.setText(R.id.label_tv, item);
            if (mContext instanceof EditLabelActivity) {
                helper.setVisible(R.id.label_delete_iv, true);
            } else {
                helper.setVisible(R.id.label_delete_iv, false);
            }
        }
    }


}
