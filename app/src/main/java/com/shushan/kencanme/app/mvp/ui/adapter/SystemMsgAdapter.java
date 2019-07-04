package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.response.SystemMsgResponse;
import com.shushan.kencanme.app.help.ImageLoaderHelper;
import com.shushan.kencanme.app.mvp.utils.DateUtil;

import java.util.List;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class SystemMsgAdapter extends BaseQuickAdapter<SystemMsgResponse.DataBean, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public SystemMsgAdapter(Context context, @Nullable List<SystemMsgResponse.DataBean> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_system_msg, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, SystemMsgResponse.DataBean item) {
        helper.setText(R.id.system_msg_title, item.getTitle())
                .setText(R.id.system_msg_content, item.getDetail());
        helper.setText(R.id.system_msg_date, DateUtil.getStrTime(item.getCreate_time(), "yyyy/MM/dd"));
    }

}
