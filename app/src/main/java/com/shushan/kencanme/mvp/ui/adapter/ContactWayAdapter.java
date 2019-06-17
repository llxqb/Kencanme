package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.ContactWay;

import java.util.List;

/**
 * RechargeAdapter
 */
public class ContactWayAdapter extends BaseQuickAdapter<ContactWay, BaseViewHolder> {

    private Context mContext;
    public ContactWayAdapter(Context context, @Nullable List<ContactWay> data) {
        super(R.layout.item_contact_way, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, ContactWay item) {
        helper.addOnClickListener(R.id.contact_way_tv).addOnClickListener(R.id.delete_iv);
        if (item != null) {
            helper.setText(R.id.contact_way_tv, item.name);
            helper.setText(R.id.contact_value_et, item.email);
        }
        EditText contactValueEt = helper.getView(R.id.contact_value_et);
        contactValueEt.addTextChangedListener(new CustomTextWatcher(helper.getAdapterPosition(),item));
    }


    class CustomTextWatcher implements TextWatcher {
        int index;
        ContactWay mItem;

        public CustomTextWatcher(int index,ContactWay item) {
            this.index = index;
            this.mItem = item;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable != null) {
                mItem.email = editable.toString();
            }
        }
    }
}
