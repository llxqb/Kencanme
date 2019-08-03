package com.shushan.kencanme.app.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.response.ReportUserListResponse;

import java.util.List;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class ReportUserAdapter extends BaseQuickAdapter<ReportUserListResponse.DataBean, BaseViewHolder> {


    public ReportUserAdapter(@Nullable List<ReportUserListResponse.DataBean> data) {
        super(R.layout.report_user_list_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportUserListResponse.DataBean item) {
        if (item != null) {
            helper.addOnClickListener(R.id.report_user_ll);
            helper.setText(R.id.report_user_name_tv, item.getReason());
        }
    }
}
