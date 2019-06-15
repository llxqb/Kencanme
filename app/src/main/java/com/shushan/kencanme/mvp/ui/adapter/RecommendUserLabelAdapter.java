package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.request.LabelBean;
import com.shushan.kencanme.mvp.ui.activity.personInfo.EditLabelActivity;

import java.util.List;

/**
 * RecommendUserLabelAdapter
 */
public class RecommendUserLabelAdapter extends BaseQuickAdapter<LabelBean, BaseViewHolder> {
    private Context mContext;

    public RecommendUserLabelAdapter(Context context, @Nullable List<LabelBean> data) {
        super(R.layout.label_layout, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, LabelBean item) {
        helper.addOnClickListener(R.id.label_delete_iv);
        if (item!=null) {
            helper.setText(R.id.label_tv, item.name);
            if (mContext instanceof EditLabelActivity) {
                helper.setVisible(R.id.label_delete_iv, true);
            } else {
                helper.setVisible(R.id.label_delete_iv, false);
            }
        }
    }


}
