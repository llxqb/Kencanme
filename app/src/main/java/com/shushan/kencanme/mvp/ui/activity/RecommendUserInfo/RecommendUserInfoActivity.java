package com.shushan.kencanme.mvp.ui.activity.recommendUserInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.kencanme.R;
import com.shushan.kencanme.di.components.DaggerRecommendUserInfoComponent;
import com.shushan.kencanme.di.modules.ActivityModule;
import com.shushan.kencanme.di.modules.RecommendUserInfoModule;
import com.shushan.kencanme.entity.Constants.Constant;
import com.shushan.kencanme.entity.base.BaseActivity;
import com.shushan.kencanme.entity.request.RecommendUserInfoRequest;
import com.shushan.kencanme.entity.response.ContactWay;
import com.shushan.kencanme.entity.response.HomeFragmentResponse;
import com.shushan.kencanme.entity.response.MyAlbumResponse;
import com.shushan.kencanme.entity.response.RecommendUserInfoResponse;
import com.shushan.kencanme.help.DialogFactory;
import com.shushan.kencanme.mvp.ui.activity.reportUser.ReportUserActivity;
import com.shushan.kencanme.mvp.ui.adapter.AlbumAdapter;
import com.shushan.kencanme.mvp.ui.adapter.MimeContactWayAdapter;
import com.shushan.kencanme.mvp.ui.adapter.RecommendUserLabelAdapter;
import com.shushan.kencanme.mvp.utils.LogUtils;
import com.shushan.kencanme.mvp.utils.StatusBarUtil;
import com.shushan.kencanme.mvp.views.CircleImageView;
import com.shushan.kencanme.mvp.views.CommonDialog;
import com.shushan.kencanme.mvp.views.MyJzvdStd;
import com.shushan.kencanme.mvp.views.dialog.CommonChoiceDialog;
import com.shushan.kencanme.mvp.views.dialog.UseBeansDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 推荐用户资料详情
 */
public class RecommendUserInfoActivity extends BaseActivity implements RecommendUserInfoControl.RecommendUserInfoView, CommonChoiceDialog.commonChoiceDialogListener,
        CommonDialog.CommonDialogListener ,MyJzvdStd.MyjzvdListener,UseBeansDialog.UseBeansDialogListener{
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.more_iv)
    ImageView mMoreIv;
    @BindView(R.id.head_icon)
    CircleImageView mHeadIcon;
    @BindView(R.id.recommend_user_head_bg)
    LinearLayout mRecommendUserHeadBg;
    @BindView(R.id.recommend_username)
    TextView mRecommendUsername;
    @BindView(R.id.recommend_user_sex_year)
    TextView mRecommendUserSexYear;
    @BindView(R.id.recommend_active_time)
    TextView mRecommendActiveTime;
    @BindView(R.id.recommend_desc)
    TextView mRecommendDesc;
    @BindView(R.id.album_recycler_view)
    RecyclerView mAlbumRecyclerView;
    @BindView(R.id.photo_album_tv)
    TextView mPhotoAlbumTv;
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
    @BindView(R.id.contact_way_recycler_view)
    RecyclerView mContactWayRecyclerView;
    @BindView(R.id.look_over_tv)
    TextView mLookOverTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.recommend_like_iv)
    ImageView mRecommendLikeIv;
    @BindView(R.id.recommend_chat_iv)
    ImageView mRecommendChatIv;
    private List<MyAlbumResponse.DataBean> albumInfoLists = new ArrayList<>();
    private List<ContactWay> contactWayList = new ArrayList<>();
    private AlbumAdapter albumAdapter;
    private MimeContactWayAdapter contactWayAdapter;
    private RecommendUserLabelAdapter recommendUserLabelAdapter;
    @Inject
    RecommendUserInfoControl.PresenterRecommendUserInfo mPresenter;
    List<String> labelList = new ArrayList<>();

    public static void start(Context context, HomeFragmentResponse.ListBean listBean) {
        Intent intent = new Intent(context, RecommendUserInfoActivity.class);
        intent.putExtra("listBean", listBean);
        context.startActivity(intent);
    }

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
        initAdapter();
        albumAdapter.setOnItemClickListener((adapter, view, position) -> {
            MyAlbumResponse.DataBean bean = albumAdapter.getItem(position);
        });
    }

    /**
     * 设置页面数据
     */
    private void setUserData(RecommendUserInfoResponse recommendUserInfoResponse) {
        mImageLoaderHelper.displayBackgroundImage(this, recommendUserInfoResponse.getCover(), mRecommendUserHeadBg, Constant.LOADING_MIDDLE);
        mImageLoaderHelper.displayImage(this, recommendUserInfoResponse.getTrait(), mHeadIcon, Constant.LOADING_AVATOR);
        mRecommendUsername.setText(recommendUserInfoResponse.getNickname());
        if (recommendUserInfoResponse.getSex() == 1) {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_male);
        } else {
            mRecommendUserSexYear.setBackgroundResource(R.mipmap.message_gender_female);
        }
        String mSexYearTvValue = recommendUserInfoResponse.getAge() + " years";
        String activeTimeValue = "active " + recommendUserInfoResponse.getActive_time() + " min age";
        mRecommendUserSexYear.setText(mSexYearTvValue);
        mRecommendActiveTime.setText(activeTimeValue);
        mRecommendDesc.setText(recommendUserInfoResponse.getDeclaration());
        //相册信息
        if (recommendUserInfoResponse.getAlbum().size() == 0)
            mPhotoAlbumTv.setVisibility(View.GONE);
        //个人信息
        mRecommendUserLocation.setText(recommendUserInfoResponse.getCity());
        String mUserHeightValue = recommendUserInfoResponse.getHeight() != 0 ? "Height: " + recommendUserInfoResponse.getHeight() + "cm" : "Height: ";
        String mUserWeightValue = !TextUtils.isEmpty(recommendUserInfoResponse.getWeight()) ? "Weight: " + recommendUserInfoResponse.getWeight() + "kg" : "Weight: ";
        String mUserChestValue = "Chest:" + recommendUserInfoResponse.getBust();
        String mUserBirthdayValue = "Birthday:" + recommendUserInfoResponse.getBirthday();
        String mUserProfessionalValue = "Professional:" + recommendUserInfoResponse.getOccupation();
        mRecommendUserHeight.setText(mUserHeightValue);
        mRecommendUserWeight.setText(mUserWeightValue);
        mRecommendUserChest.setText(mUserChestValue);
        mRecommendUserBirthday.setText(mUserBirthdayValue);
        mRecommendUserProfessional.setText(mUserProfessionalValue);

    }

    private void initAdapter() {
        //图片adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        mAlbumRecyclerView.setLayoutManager(gridLayoutManager);
        albumAdapter = new AlbumAdapter(this, albumInfoLists, mImageLoaderHelper);
        mAlbumRecyclerView.setAdapter(albumAdapter);

        //联系方式adapter
        mContactWayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactWayAdapter = new MimeContactWayAdapter(contactWayList);
        mContactWayRecyclerView.setAdapter(contactWayAdapter);
        //label adapter
        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(this, 3);
        mLabelRecyclerView.setLayoutManager(gridLayoutManager2);
        recommendUserLabelAdapter = new RecommendUserLabelAdapter(this, labelList);
        mLabelRecyclerView.setAdapter(recommendUserLabelAdapter);


    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            HomeFragmentResponse.ListBean listBean = getIntent().getParcelableExtra("listBean");
            if (listBean != null) {
                //查询用户详细信息
                RecommendUserInfoRequest recommendUserInfoRequest = new RecommendUserInfoRequest();
                recommendUserInfoRequest.token = mBuProcessor.getToken();
                recommendUserInfoRequest.uid = String.valueOf(listBean.getUid());
                mPresenter.onRequestRecommendUserInfo(recommendUserInfoRequest);
            }
        }
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
//                mContactWay.setText("13262253731");
                showContactWay();
                break;
            case R.id.recommend_like_iv:
                showToast("已喜欢");
                mImageLoaderHelper.displayImage(this, R.mipmap.home_liked, mRecommendLikeIv, Constant.LOADING_SMALL);
                break;
            case R.id.recommend_chat_iv:
                break;
        }
    }

    private void showContactWay() {
//        UseBeansDialog useBeansDialog = UseBeansDialog.newInstance();
//        useBeansDialog.setTitle("Look over type?");
//        useBeansDialog.setListener(this);
//        useBeansDialog.setContent("become vip","20 hi-beans");
//        DialogFactory.showDialogFragment((this).getSupportFragmentManager(), useBeansDialog, UseBeansDialog.TAG);
        for (ContactWay contactWay : contactWayList) {
            contactWay.isShow = true;
        }
        contactWayAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRecommendUserInfoSuccess(RecommendUserInfoResponse response1) {
        LogUtils.e("response:" + new Gson().toJson(response1));
        String ss = "{\"active_time\":-1,\"age\":23,\"album\":[{\"id\":6,\"album_url\":\"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190613/5d02066162657.png\",\"album_type\":1,\"cost\":0},\n" +
                "{\n" +
                "            \"id\":8,\n" +
                "            \"album_url\":\"http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4\",\n" +
                "            \"album_type\":2,\n" +
                "            \"cost\":0\n" +
                "        },\n" +
                "{\"id\":7,\"album_url\":\"https://menggoda.oss-ap-southeast-5.aliyuncs.com/cover/20190613/5d020b7c814df.png\",\"album_type\":2,\"cost\":0}],\"beans\":0,\"birthday\":\"820512000\",\"bust\":\"38B\",\"city\":\"Bandung\",\"contact\":[{\"contactName\":\"google\",\"contactValue\":\"123g\"},{\"contactName\":\"facebook\",\"contactValue\":\"qqqq\"}],\"cover\":\"https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/21077236_1847543632225383_3255788874175109773_n.jpg?_nc_cat\\u003d100\\u0026_nc_ht\\u003dscontent-hkg3-1.xx\\u0026oh\\u003df3a4c2a70deecce2692f7ca2826a69bb\\u0026oe\\u003d5D968C5C\",\"declaration\":\"\",\"exposure\":0,\"forbidden\":0,\"height\":163,\"is_friend\":0,\"is_see_contact\":0,\"label\":[{\"name\":\"yyyyy\"},{\"name\":\"uuu\"},{\"name\":\"哈哈哈哈\"}],\"last_login_time\":0,\"nickname\":\"july\",\"occupation\":\"actress\",\"pushing_age\":\"18-30\",\"pushing_gender\":2,\"pushing_large_age\":\"30\",\"pushing_small_age\":\"18\",\"relation\":0,\"rongyun_userid\":\"Kencanme770\",\"sex\":2,\"svip\":0,\"token\":\"\",\"trait\":\"https://scontent-hkg3-1.xx.fbcdn.net/v/t1.0-9/22008153_1861299764183103_3678657400557544971_n.jpg?_nc_cat\\u003d105\\u0026_nc_ht\\u003dscontent-hkg3-1.xx\\u0026oh\\u003da6136cd7368f4279828a49b5cdbba16d\\u0026oe\\u003d5D56FE47\",\"uid\":770,\"vip\":0,\"vip_time\":0,\"weight\":\"46\",\"wrong_login_num\":0,\"wrong_time\":0}\n";
        RecommendUserInfoResponse response = new Gson().fromJson(ss, RecommendUserInfoResponse.class);

        setUserData(response);
        for (RecommendUserInfoResponse.AlbumBean albumBean : response.getAlbum()) {
            MyAlbumResponse.DataBean dataBean = new MyAlbumResponse.DataBean();
            dataBean.setId(albumBean.getId());
            dataBean.setAlbum_url(albumBean.getAlbum_url());
            dataBean.setAlbum_type(albumBean.getAlbum_type());
            dataBean.setCost(albumBean.getCost());
            albumInfoLists.add(dataBean);
        }
        albumAdapter.setNewData(albumInfoLists);
        contactWayList = response.getContact();
        contactWayAdapter.setNewData(contactWayList);
        labelList = response.getLabel();
        recommendUserLabelAdapter.setNewData(labelList);
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

    private void initializeInjector() {
        DaggerRecommendUserInfoComponent.builder().appComponent(getAppComponent())
                .recommendUserInfoModule(new RecommendUserInfoModule(RecommendUserInfoActivity.this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    public void jzvdClickListener(int clickPos) {
        // VIp can view
        showToast("1111");
    }

    @Override
    public void useBeansDialogLeftListener() {

    }

    @Override
    public void useBeansDialogRightListener() {
        for (ContactWay contactWay : contactWayList) {
            contactWay.isShow = true;
        }
        contactWayAdapter.notifyDataSetChanged();
    }
}
