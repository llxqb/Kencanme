package com.shushan.kencanme.mvp.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;

import java.util.List;

/**
 * RechargeAdapter
 */
public class ContactWayAdapter extends BaseQuickAdapter<PersonalInfoResponse.ContactBean, BaseViewHolder> {

    private Context mContext;
    public ContactWayAdapter(Context context, @Nullable List<PersonalInfoResponse.ContactBean> data) {
        super(R.layout.item_contact_way, data);
        mContext = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, PersonalInfoResponse.ContactBean item) {
        helper.addOnClickListener(R.id.contact_way_tv).addOnClickListener(R.id.delete_iv);
        if (item != null) {
            helper.setText(R.id.contact_way_tv, item.getContactName());
            helper.setText(R.id.contact_value_et, item.getContactValue());
        }
        EditText contactValueEt = helper.getView(R.id.contact_value_et);
        contactValueEt.addTextChangedListener(new CustomTextWatcher(helper.getAdapterPosition(),item));
    }


    class CustomTextWatcher implements TextWatcher {
        int index;
        PersonalInfoResponse.ContactBean mItem;

        public CustomTextWatcher(int index,PersonalInfoResponse.ContactBean item) {
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
                mItem.setContactValue(editable.toString());
            }
        }
    }
}
