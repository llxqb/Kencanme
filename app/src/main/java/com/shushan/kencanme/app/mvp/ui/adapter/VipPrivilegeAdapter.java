package com.shushan.kencanme.app.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.app.R;
import com.shushan.kencanme.app.entity.Constants.Constant;
import com.shushan.kencanme.app.entity.VipPrivilege;
import com.shushan.kencanme.app.help.ImageLoaderHelper;

import java.util.List;

/**
 * RechargeAdapter
 */
public class VipPrivilegeAdapter extends BaseQuickAdapter<VipPrivilege, BaseViewHolder> {

    private Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public VipPrivilegeAdapter(Context context, @Nullable List<VipPrivilege> data, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_vip_privilege, data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, VipPrivilege item) {
        if (item != null) {
            ImageView imageView = helper.getView(R.id.vip_privilege_iv);
            mImageLoaderHelper.displayImage(mContext, item.pic, imageView, Constant.LOADING_SMALL);
            helper.setText(R.id.vip_privilege_name, item.name);
        }
    }
}
