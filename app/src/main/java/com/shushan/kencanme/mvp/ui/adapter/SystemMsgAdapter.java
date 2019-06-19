package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.SystemMsgResponse;
import com.shushan.kencanme.help.ImageLoaderHelper;

import java.util.List;

/**
 * Mime 页面 MyAlbumAdapter
 */
public class SystemMsgAdapter extends BaseQuickAdapter<SystemMsgResponse, BaseViewHolder> {

    private ImageLoaderHelper mImageLoaderHelper;
    private Context mContext;

    public SystemMsgAdapter(Context context, @Nullable List<SystemMsgResponse> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_system_msg, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, SystemMsgResponse item) {
    }

}
