package com.shushan.kencanme.mvp.ui.adapter;

import android.graphics.BlurMaskFilter;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.MaskFilterSpan;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.ContactWay;
import com.shushan.kencanme.mvp.ui.activity.recommendUserInfo.RecommendUserInfoActivity;

import java.util.List;

/**
 * Mime 页面 MimeContactWayAdapter
 */
public class MimeContactWayAdapter extends BaseQuickAdapter<ContactWay, BaseViewHolder> {


    public MimeContactWayAdapter(@Nullable List<ContactWay> data) {
        super(R.layout.item_mime_contact_way, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactWay item) {
        TextView mContactWay = helper.getView(R.id.contact_way);
        String mContactWayValue = item.name + ":" + item.email;
        if (mContext instanceof RecommendUserInfoActivity) {
//            //设置文字模糊
//            mContactWay.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            SpannableString stringBuilder = new SpannableString(mContactWayValue);
//            stringBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)), 0, stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            mContactWay.setText(stringBuilder);
            if (item.isShow) {
                mContactWay.setText(mContactWayValue);
            } else {
                //设置文字模糊
                mContactWay.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                SpannableString stringBuilder = new SpannableString(mContactWayValue);
                stringBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)), 0, stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                mContactWay.setText(stringBuilder);
            }
        } else {
            mContactWay.setText(mContactWayValue);
        }
    }
}
