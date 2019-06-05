package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.response.ContactWay;
import com.shushan.kencanme.mvp.ui.adapter.ContactWayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑联系方式
 */
public class EditContactWayActivity extends BaseActivity {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.contact_recycler_view)
    RecyclerView mContactRecyclerView;
    @BindView(R.id.add_more_ll)
    LinearLayout mAddMoreLl;
    @BindView(R.id.save_btn)
    Button mSaveBtn;
    List<ContactWay> contactWayList = new ArrayList<>();
    ContactWayAdapter contactWayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_way);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.EditContactWayActivity_title));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mContactRecyclerView.setLayoutManager(linearLayoutManager);
        contactWayAdapter = new ContactWayAdapter(this, contactWayList);
        mContactRecyclerView.setAdapter(contactWayAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                ContactWay contactWay = new ContactWay();
                contactWay.ContactName = "facebook";
                contactWay.ContactValue = "123456789@facebook.com";
                contactWayList.add(contactWay);
            } else {
                ContactWay contactWay = new ContactWay();
                contactWay.ContactName = "google";
                contactWay.ContactValue = "123456789@google.com";
                contactWayList.add(contactWay);
            }
        }


    }

    @OnClick({R.id.common_back, R.id.add_more_ll, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.add_more_ll:
                ContactWay contactWay = new ContactWay();
                contactWay.ContactName = "google";//默认添加谷歌
                contactWayAdapter.addData(contactWay);
                break;
            case R.id.save_btn:
                break;
        }
    }
}
