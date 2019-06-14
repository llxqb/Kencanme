package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;

import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.MaskFilterSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerRecommendUserInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.RecommendUserInfoModule;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.reportUser.ReportUserActivity;
import com.shushan.kencanme.mvp.ui.adapter.RecommendUserAlbumAdapter;
import com.shushan.kencanme.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推荐用户资料详情
 */
public class RecommendUserInfoActivity extends BaseActivity implements RecommendUserInfoControl.RecommendUserInfoView, CommonChoiceDialog.commonChoiceDialogListener, CommonDialog.CommonDialogListener {

    @BindView(R.id.head_icon)
    CircleImageView mHeadIcon;
    @BindView(R.id.recommend_username)
    TextView mRecommendUsername;
    @BindView(R.id.recommend_user_sex_year)
    TextView mRecommendUserSexYear;
    @BindView(R.id.recommend_active_time)
    TextView mRecommendActiveTime;
    @BindView(R.id.recommend_desc)
    TextView mRecommendDesc;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    @BindView(R.id.recommend_user_location)
    TextView mRecommendUserLocation;
    @BindView(R.id.recommend_user_height)
    TextView mRecommendUserHeight;
    @BindView(R.id.recommend_user_weight)
    TextView mRecommendUserWeight;
    @BindView(R.id.recommend_user_chest)
    TextView mRecommendUserChest;
    @BindView(R.id.recommend_user_birthday)
    TextView mRecommendUserBirthday;
    @BindView(R.id.recommend_user_professional)
    TextView mRecommendUserProfessional;
    @BindView(R.id.contact_way)
    TextView mContactWay;
    @BindView(R.id.look_over_tv)
    TextView mLookOverTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.recommend_like_iv)
    ImageView mRecommendLikeIv;
    @BindView(R.id.recommend_chat_iv)
    ImageView mRecommendChatIv;
    private List<RecommendUserInfoResponse.DataBean> recommendUserInfoLists = new ArrayList<>();
    private RecommendUserAlbumAdapter recommendUserAlbumAdapter;
    @Inject
    RecommendUserInfoControl.PresenterRecommendUserInfo mPresenter;
    private String userLabel[] = {"haokan", "sexy", "graceful beauty"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_user_info);
        ButterKnife.bind(this);
        initializeInjector();
        //设置有图片状态栏
        StatusBarUtil.setTransparentForImageView(this, null);
        initView();
        initData();
    }

    @Override
    public void initView() {
        //设置文字模糊
        mContactWay.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        SpannableString stringBuilder = new SpannableString("13262253731");
        stringBuilder.setSpan(new MaskFilterSpan(new BlurMaskFilter(10f, BlurMaskFilter.Blur.NORMAL)), 0, stringBuilder.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mContactWay.setText(stringBuilder);
        initAdapter();
        recommendUserAlbumAdapter.setOnItemClickListener((adapter, view, position) -> {
            RecommendUserInfoResponse.DataBean bean = recommendUserAlbumAdapter.getItem(position);
            if (bean != null) {
                if (!bean.isVip) {
//                    adapter.notifyDataSetChanged();
                    showToast("你不是VIP");
                }
                if (!bean.isPic) {
//                    JzvdStd.startFullscreenDirectly(this, JzvdStd.class, bean.picPath, "饺子辛苦了");
                    //todo
                    //跳转去播放页面
                }
            }
        });
    }

    private void initAdapter() {
        //图片adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mAlbumRecyclerView.setLayoutManager(gridLayoutManager);
        recommendUserAlbumAdapter = new RecommendUserAlbumAdapter(this, recommendUserInfoLists, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(recommendUserAlbumAdapter);

        //label adapter
        List<String> labelList = Arrays.asList(userLabel);
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 3);
        mLabelRecyclerView.setLayoutManager(gridLayoutManager2);
        RecommendUserLabelAdapter recommendUserLabelAdapter = new RecommendUserLabelAdapter(this,labelList);
        mLabelRecyclerView.setAdapter(recommendUserLabelAdapter);
    }

    @Override
    public void initData() {
        mPresenter.onRequestRecommendUserInfo(null);
    }

    @OnClick({R.id.back_iv, R.id.more_iv, R.id.look_over_tv, R.id.recommend_like_iv, R.id.recommend_chat_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.more_iv:
//                CommonChoiceDialog commonChoiceDialog = CommonChoiceDialog.newInstance();
//                commonChoiceDialog.setListener(this);
//                DialogFactory.showDialogFragment(this.getSupportFragmentManager(), commonChoiceDialog, CommonChoiceDialog.TAG);

                DialogFactory.showCommonDialog(this, "Determine to blacklist the us\n" +
                        "-er? After joining the blacklist, \n" +
                        "the user will no longer be pus\n" +
                        "-hed for you.?", Constant.DIALOG_THREE);
                break;
            case R.id.look_over_tv:
                mContactWay.setText("13262253731");
                break;
            case R.id.recommend_like_iv:
                showToast("已喜欢");
                mImageLoaderHelper.displayImage(this, R.mipmap.home_liked, mRecommendLikeIv,Constant.LOADING_SMALL);
                break;
            case R.id.recommend_chat_iv:
                break;
        }
    }

    private void initializeInjector() {
        DaggerRecommendUserInfoComponent.builder().appComponent(getAppComponent())
                .recommendUserInfoModule(new RecommendUserInfoModule(RecommendUserInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public void getRecommendUserInfoSuccess(List<RecommendUserInfoResponse.DataBean> response) {
        LogUtils.e("response:" + new Gson().toJson(response));
        recommendUserInfoLists = response;
        recommendUserAlbumAdapter.setNewData(response);
    }

    @Override
    public void showErrMessage(Throwable e) {

    }

    @Override
    public void deleteUserListener() {
        DialogFactory.showCommonDialog(this, "Are you sure you will delete t\n" +
                "-his friend? Please think clea\n" +
                "-rly.", Constant.DIALOG_THREE);
    }

    @Override
    public void blackUserListener() {
        DialogFactory.showCommonDialog(this, "Determine to blacklist the us\n" +
                "-er? After joining the blacklist, \n" +
                "the user will no longer be pus\n" +
                "-hed for you.?", Constant.DIALOG_THREE);
    }

    @Override
    public void commonDialogBtnOkListener() {
        //去举报用户界面
        startActivitys(ReportUserActivity.class);
    }
}
