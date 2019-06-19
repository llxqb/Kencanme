package com.shushan.kencanme.mvp.ui.activity.loveMe;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerLoveMePeopleComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.LoveMePeopleModule;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.MyFriendsRequest;
import com.shushan.kencanme.entity.response.MyFriendsResponse;
import com.shushan.kencanme.mvp.ui.adapter.MyFriendsAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 查看喜欢我的人
 */
public class LoveMePeopleActivity extends BaseActivity implements LoveMePeopleControl.LoveMePeopleView {

    @BindView(R.id.common_back)
    ImageView mCommonBack;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_iv_right)
    ImageView mCommonIvRight;
    @BindView(R.id.love_me_recycler_view)
    RecyclerView mLoveMeRecyclerView;

    private MyFriendsAdapter myFriendsAdapter;
    List<MyFriendsResponse.ListBean> listBeanList = new ArrayList<>();
    @Inject
    LoveMePeopleControl.PresenterLoveMePeople mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_me_people);
        ButterKnife.bind(this);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.LoveMePeopleActivity_title));
        mCommonIvRight.setVisibility(View.VISIBLE);
        mLoveMeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myFriendsAdapter = new MyFriendsAdapter(this, listBeanList, mImageLoaderHelper);
        mLoveMeRecyclerView.setAdapter(myFriendsAdapter);
    }

    @Override
    public void initData() {
        MyFriendsRequest myFriendsRequest = new MyFriendsRequest();
        myFriendsRequest.token = mBuProcessor.getToken();
        myFriendsRequest.type = "2";
        mPresenter.onRequestMyFriendList(myFriendsRequest);
    }

    @OnClick({R.id.common_back, R.id.common_iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back:
                finish();
                break;
            case R.id.common_iv_right:
//                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
//                commonChoiceDialog.setListener(this);
//                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);
                break;
        }
    }

    @Override
    public void getLoveMePeopleInfoSuccess(MyFriendsResponse myFriendsResponse) {
        myFriendsAdapter.setNewData(myFriendsResponse.getList());
    }


    private void initializeInjector() {
        DaggerLoveMePeopleComponent.builder().appComponent(getAppComponent())
                .loveMePeopleModule(new LoveMePeopleModule(LoveMePeopleActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);

    }

}
