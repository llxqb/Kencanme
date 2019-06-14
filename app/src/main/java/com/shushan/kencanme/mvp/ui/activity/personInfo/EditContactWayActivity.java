package com.shushan.kencanme.mvp.ui.activity.personInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerPersonalInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.PersonalInfoModule;
import com.shushan.kencanme.entity.Constants.ActivityConstant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.UpdatePersonalInfoRequest;
import com.shushan.kencanme.entity.response.PersonalInfoResponse;
import com.shushan.kencanme.entity.user.LoginUser;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.adapter.ContactWayAdapter;
import com.shushan.kencanme.mvp.views.dialog.PhotoDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 编辑联系方式
 */
public class EditContactWayActivity extends BaseActivity implements PhotoDialog.PhotoDialogListener, PersonalInfoControl.PersonalInfoView {

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
    List<PersonalInfoResponse.ContactBean> contactWayList = new ArrayList<>();
    ContactWayAdapter contactWayAdapter = null;
    UpdatePersonalInfoRequest updatePersonalInfoRequest;

    @Inject
    PersonalInfoControl.PresenterPersonalInfo mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact_way);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    int currentPos;

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.EditContactWayActivity_title));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mContactRecyclerView.setLayoutManager(linearLayoutManager);
        contactWayAdapter = new ContactWayAdapter(this, contactWayList);
        mContactRecyclerView.setAdapter(contactWayAdapter);

        mContactRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                PersonalInfoResponse.ContactBean contactWay = (PersonalInfoResponse.ContactBean) adapter.getItem(position);
                currentPos = position;
                switch (view.getId()) {
                    case R.id.contact_way_tv:
                        showContactDialog();
                        break;
                    case R.id.delete_iv:
                        assert contactWay != null;
                        contactWay.setContactValue("");
                        adapter.notifyItemChanged(position);
                        break;
                }
            }
        });
    }


    @Override
    public void initData() {
    }

    @OnClick({R.id.common_back, R.id.add_more_ll, R.id.save_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.add_more_ll:
                PersonalInfoResponse.ContactBean contactWay = new PersonalInfoResponse.ContactBean();
                contactWay.setContactName("google");//默认添加谷歌
                contactWay.setContactValue("");
                contactWayAdapter.addData(contactWay);
                break;
            case R.id.save_btn:
                if (contactWayList.size() == 0) {
                    showToast("please add contact");
                    return;
                }
                updatePersonalInfoRequest = new UpdatePersonalInfoRequest();
                updatePersonalInfoRequest.token = mBuProcessor.getToken();
                updatePersonalInfoRequest.contact = new Gson().toJson(contactWayList);
                mPresenter.onRequestPersonalInfo(updatePersonalInfoRequest);
                break;
        }
    }

    /**
     * 显示联系方式
     */
    private void showContactDialog() {
        //弹出框框
        PhotoDialog photoDialog = PhotoDialog.newInstance();
        photoDialog.setListener(this);
        photoDialog.setData("Select Contact way", "google", "facebook", "WhatsApp");
        DialogFactory.showDialogFragment(Objects.requireNonNull(this).getSupportFragmentManager(), photoDialog, PhotoDialog.TAG);
    }

    @Override
    public void photoDialogBtnOkListener() {
        //google
        PersonalInfoResponse.ContactBean contactWay = new PersonalInfoResponse.ContactBean();
        contactWay.setContactName("google");
        contactWay.setContactValue("");
        contactWayAdapter.setData(currentPos, contactWay);
    }

    @Override
    public void albumDialogBtnOkListener() {
        PersonalInfoResponse.ContactBean contactWay = new PersonalInfoResponse.ContactBean();
        contactWay.setContactName("facebook");
        contactWay.setContactValue("");
        contactWayAdapter.setData(currentPos, contactWay);
    }

    @Override
    public void photoDialogBtn3OkListener() {
        PersonalInfoResponse.ContactBean contactWay = new PersonalInfoResponse.ContactBean();
        contactWay.setContactName("WhatsApp");
        contactWay.setContactValue("");
        contactWayAdapter.setData(currentPos, contactWay);
    }


    @Override
    public void updateSuccess(String msg) {
        showToast(msg);
        updatLoginUser();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_USER_INFO));
        finish();
    }

    /**
     * 更新用户数据
     */
    private void updatLoginUser() {
        LoginUser loginUser = mBuProcessor.getLoginUser();
        loginUser.contact = updatePersonalInfoRequest.contact;
        mBuProcessor.setLoginUser(loginUser);
    }

    @Override
    public void uploadVideoSuccess(String videoPath) {

    }

    @Override
    public void uploadImageSuccess(String picPath) {

    }

    @Override
    public void updateMyAlbumSuccess(String msg) {

    }

    private void initializeInjector() {
        DaggerPersonalInfoComponent.builder().appComponent(getAppComponent())
                .personalInfoModule(new PersonalInfoModule(EditContactWayActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
